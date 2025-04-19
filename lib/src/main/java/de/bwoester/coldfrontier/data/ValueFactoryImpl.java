package de.bwoester.coldfrontier.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import de.bwoester.coldfrontier.data.memory.InMemoryValueFactory;
import de.bwoester.coldfrontier.data.nats.NatsValueFactory;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

@Slf4j
public class ValueFactoryImpl implements ValueFactory {

    private final ValueFactory valueFactory;

    public ValueFactoryImpl(Supplier<Long> tickSupplier) {
        this(tickSupplier, getNatsEndpoint());
    }

    private static String getNatsEndpoint() {
        return System.getenv("NATS_ENDPOINT");
    }

    public ValueFactoryImpl(Supplier<Long> tickSupplier, String natsEndpoint) {
        EventFactory eventFactory = new EventFactory(tickSupplier);
        this.valueFactory = natsEndpoint.isEmpty()
                ? createInMemoryValueFactory(eventFactory)
                : createNatsValueFactory(eventFactory, natsEndpoint);
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

    private static ValueFactory createNatsValueFactory(EventFactory eventFactory, String natsEndpoint) {
        log.info("Using NATS endpoint: {}", natsEndpoint);
        NatsValueFactory natsValueFactory = new NatsValueFactory(natsEndpoint, eventFactory, objectMapper());
        natsValueFactory.init();
        return natsValueFactory;
    }

    private static ValueFactory createInMemoryValueFactory(EventFactory eventFactory) {
        log.warn("No NATS endpoint configured, using in-memory value store");
        return new InMemoryValueFactory(eventFactory);
    }

    @Override
    public <T> Value<T> create(Class<T> clazz, String key) {
        return valueFactory.create(clazz, key);
    }
}
