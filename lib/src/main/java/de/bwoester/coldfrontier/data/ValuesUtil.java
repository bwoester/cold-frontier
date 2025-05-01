package de.bwoester.coldfrontier.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import de.bwoester.coldfrontier.data.memory.InMemoryDataAccess;
import de.bwoester.coldfrontier.data.memory.InMemoryValueRepository;
import de.bwoester.coldfrontier.data.nats.NatsDataAccess;
import de.bwoester.coldfrontier.data.nats.NatsValueRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ValuesUtil implements ValueRepository, DataAccess {

    @Getter
    private final DataAccess dataAccess;

    @Getter
    private final ValueRepository valueRepository;

    public static ValuesUtil create() {
        String natsEndpoint = getNatsEndpoint();
        if (natsEndpoint == null || natsEndpoint.isEmpty()) {
            log.warn("No NATS endpoint configured, using in-memory value store");
            return createInMemoryImpl();
        } else {
            log.info("Using NATS endpoint: {}", natsEndpoint);
            return createNatsImpl(natsEndpoint);
        }
    }

    private static ValuesUtil createInMemoryImpl() {
        InMemoryDataAccess dataAccess = new InMemoryDataAccess();

        // Bootstrap instance
        // We kick off with an EventFactory that always returns 0 for the current tick value.
        // This means all Value instances we get from the intermediary bootstrap ValueRepository would always
        // wrap payloads with an Event envelop that indicates the event would have been generated at tick 0, which is
        // obviously wrong.
        // But it allows us to access the tick value, which we only have to read, not write.
        Value<Long> bootstrapTick = new InMemoryValueRepository(dataAccess.getData(), new EventFactory(() -> 0L))
                .get(Long.class, "tick", 0L);

        // Actually used instance
        // Once we got the bootstrap tick value, we can create the actual EventFactory, which will always use the
        // value of the referenced tick as the current tick value. This means all Value instances we get from the
        // actual ValueRepository will always wrap payloads with an Event envelop that indicates the event has been
        // generated at the correct tick.
        // When the tick Value instance is obtained from the actual ValueRepository, it can be read and written like
        // all other Value instances. And because Value instances are references to data stored in the backend, both
        // the actual tick Value instance and the bootstrap tick Value instance, which is used for the actual
        // EventFactory, will see the updated value when they get the data via Value.get().
        ValueRepository valueRepository = new InMemoryValueRepository(dataAccess.getData(), new EventFactory(bootstrapTick::get));

        return new ValuesUtil(dataAccess, valueRepository);
    }

    private static ValuesUtil createNatsImpl(String natsEndpoint) {
        NatsDataAccess dataAccess = new NatsDataAccess(natsEndpoint);
        dataAccess.init();

        ObjectMapper objectMapper = objectMapper();

        // Bootstrap instance
        // We kick off with an EventFactory that always returns 0 for the current tick value.
        // This means all Value instances we get from the intermediary bootstrap ValueRepository would always
        // wrap payloads with an Event envelop that indicates the event would have been generated at tick 0, which is
        // obviously wrong.
        // But it allows us to access the tick value, which we only have to read, not write.
        Value<Long> bootstrapTick = new NatsValueRepository(dataAccess.getKv(), new EventFactory(() -> 0L), objectMapper)
                .get(Long.class, "tick", 0L);

        // Actually used instance
        // Once we got the bootstrap tick value, we can create the actual EventFactory, which will always use the
        // value of the referenced tick as the current tick value. This means all Value instances we get from the
        // actual ValueRepository will always wrap payloads with an Event envelop that indicates the event has been
        // generated at the correct tick.
        // When the tick Value instance is obtained from the actual ValueRepository, it can be read and written like
        // all other Value instances. And because Value instances are references to data stored in the backend, both
        // the actual tick Value instance and the bootstrap tick Value instance, which is used for the actual
        // EventFactory, will see the updated value when they get the data via Value.get().
        ValueRepository valueRepository = new NatsValueRepository(dataAccess.getKv(), new EventFactory(bootstrapTick::get), objectMapper);

        return new ValuesUtil(dataAccess, valueRepository);
    }

    private static String getNatsEndpoint() {
        return System.getenv("NATS_ENDPOINT");
    }

    private static ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Register JavaTimeModule with a custom serializer for ZonedDateTime
        JavaTimeModule timeModule = new JavaTimeModule();
        // Use ISO-8601 with UTC ("Z") time zone
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        // Serialize all ZonedDateTime as UTC
        timeModule.addSerializer(ZonedDateTime.class,
                new ZonedDateTimeSerializer(isoFormatter.withZone(ZoneOffset.UTC)));
        mapper.registerModule(new JavaTimeModule());

        // Required for Java record support (uses constructor parameter names)
        mapper.registerModule(new ParameterNamesModule());

        return mapper;
    }

    @Override
    public <T> Value<T> get(Class<T> clazz, String key) {
        return valueRepository.get(clazz, key);
    }

    @Override
    public <T> Value<T> get(Class<T> clazz, String key, T initValue) {
        return valueRepository.get(clazz, key, initValue);
    }

    @Override
    public List<ValueEntry<?>> getMostRecentEntries() {
        return dataAccess.getMostRecentEntries();
    }

    @Override
    public List<ValueEntry<?>> getMostRecentEntries(int maxEntries) {
        return dataAccess.getMostRecentEntries(maxEntries);
    }

    public String prettyPrint() {
        return prettyPrint(getMostRecentEntries());
    }

    public String prettyPrint(List<ValueEntry<?>> valueEntries) {
        return ValueEntryPrinter.prettyPrint(valueEntries);
    }
}
