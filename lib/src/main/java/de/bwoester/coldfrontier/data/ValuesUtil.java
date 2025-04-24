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
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
public class ValuesUtil implements ValueRepository, DataAccess {

    @Getter
    private final DataAccess dataAccess;

    @Getter
    private final ValueRepository valueRepository;

    public static ValuesUtil create() {
        String natsEndpoint = getNatsEndpoint();
        EventFactory eventFactory = new EventFactory(() -> 0L);
        if (natsEndpoint == null || natsEndpoint.isEmpty()) {
            log.warn("No NATS endpoint configured, using in-memory value store");
            InMemoryDataAccess dataAccess = new InMemoryDataAccess();
            InMemoryValueRepository valueRepository = new InMemoryValueRepository(dataAccess.getData(), eventFactory);
            return new ValuesUtil(dataAccess, valueRepository);
        } else {
            log.info("Using NATS endpoint: {}", natsEndpoint);
            NatsDataAccess dataAccess = new NatsDataAccess(natsEndpoint);
            dataAccess.init();
            NatsValueRepository valueRepository = new NatsValueRepository(dataAccess.getKv(), eventFactory, objectMapper());
            return new ValuesUtil(dataAccess, valueRepository);
        }
    }

    public static ValuesUtil create(Supplier<Long> tickSupplier) {
        String natsEndpoint = getNatsEndpoint();
        EventFactory eventFactory = new EventFactory(tickSupplier);
        if (natsEndpoint == null || natsEndpoint.isEmpty()) {
            log.warn("No NATS endpoint configured, using in-memory value store");
            InMemoryDataAccess dataAccess = new InMemoryDataAccess();
            InMemoryValueRepository valueRepository = new InMemoryValueRepository(dataAccess.getData(), eventFactory);
            return new ValuesUtil(dataAccess, valueRepository);
        } else {
            log.info("Using NATS endpoint: {}", natsEndpoint);
            NatsDataAccess dataAccess = new NatsDataAccess(natsEndpoint);
            dataAccess.init();
            NatsValueRepository valueRepository = new NatsValueRepository(dataAccess.getKv(), eventFactory, objectMapper());
            return new ValuesUtil(dataAccess, valueRepository);
        }
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
