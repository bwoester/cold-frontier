package de.bwoester.coldfrontier.data.nats;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.data.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Map;

import static de.bwoester.coldfrontier.resources.ResourceType.ICE;
import static de.bwoester.coldfrontier.resources.ResourceType.WATER;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonEventSerializerTest {
    private final JsonMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    private final JsonEventSerializer<Payload> serializer =
            new JsonEventSerializer<>(mapper, Payload.class);

    record Payload(String resource, BigDecimal amount, Instant recordedAt) {}

    @Test
    @DisplayName("Previously stored event JSON remains readable after the Jackson upgrade")
    void readsExistingEventFormat() throws Exception {
        byte[] storedJson = """
                {"tick":42,"payload":{"resource":"ice","amount":0.125,
                 "recordedAt":"2026-11-17T09:30:00Z"}}
                """.getBytes(StandardCharsets.UTF_8);

        assertEquals(new Event<>(42, new Payload("ice", new BigDecimal("0.125"),
                Instant.parse("2026-11-17T09:30:00Z"))), serializer.deserialize(storedJson));
    }

    @Test
    void readsLegacyPlanetBalanceAndRoundTripsAdditionalResources() throws Exception {
        var serializer = new JsonEventSerializer<>(mapper, PlanetResourceSetMsg.class);
        byte[] storedJson = """
                {"tick":42,"payload":{"iron":3,"energy":4,"population":5}}
                """.getBytes(StandardCharsets.UTF_8);
        assertEquals(new Event<>(42, new PlanetResourceSetMsg(3, 4, 5)), serializer.deserialize(storedJson));

        var event = new Event<>(43, new PlanetResourceSetMsg(3, 4, 5, Map.of(ICE, 2L, WATER, 1L)));
        assertEquals(event, serializer.deserialize(serializer.serialize(event)));
    }

    @Test
    @DisplayName("Generic record payloads retain decimal precision and nanoseconds through JSON")
    void roundTripPreservesPayload() throws Exception {
        Event<Payload> event = new Event<>(Long.MAX_VALUE,
                new Payload("water", new BigDecimal("123456789.000000001"),
                        Instant.parse("2026-11-17T09:30:00.123456789Z")));

        assertEquals(event, serializer.deserialize(serializer.serialize(event)));
    }
}
