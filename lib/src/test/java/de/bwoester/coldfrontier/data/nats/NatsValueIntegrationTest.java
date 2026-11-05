package de.bwoester.coldfrontier.data.nats;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.bwoester.coldfrontier.data.*;
import io.nats.client.*;
import io.nats.client.api.KeyValueConfiguration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
@Testcontainers
class NatsValueIntegrationTest {

    private static final int NATS_PORT = 4222;

    @Container
    private static final GenericContainer<?> nats = new GenericContainer<>(
            DockerImageName.parse("nats:2.14.9-alpine"))
            .withCommand("-js")
            .withExposedPorts(NATS_PORT);

    private static Connection natsConnection;
    private static KeyValueManagement kvm;

    private KeyValue keyValue;
    private NatsValue<TestData> natsValue;
    private String bucketName;
    private String testKey;
    
    private static final String TEST_VALUE_DATA = "test-data";
    private static final TestData TEST_VALUE = new TestData(TEST_VALUE_DATA);
    
    private EventSerializer<TestData> serializer;
    private EventFactory eventFactory;

    @BeforeAll
    static void setupNats() throws IOException, InterruptedException {
        Options options = new Options.Builder()
                .server("nats://%s:%d".formatted(nats.getHost(), nats.getMappedPort(NATS_PORT)))
                .connectionTimeout(Duration.ofSeconds(5))
                .build();
        natsConnection = Nats.connect(options);
        kvm = natsConnection.keyValueManagement();
    }

    @AfterAll
    static void closeNatsConnection() throws InterruptedException {
        if (natsConnection != null) {
            natsConnection.close();
        }
    }

    @BeforeEach
    void setUp() throws IOException, JetStreamApiException {
        // Create a unique bucket name for this test run
        bucketName = "test-bucket-" + UUID.randomUUID();
        testKey = "test-key";
        
        // Create a KeyValue store
        KeyValueConfiguration kvc = KeyValueConfiguration.builder()
                .name(bucketName)
                .build();

        kvm.create(kvc);
        keyValue = natsConnection.keyValue(bucketName);
        
        // Create serializer and event factory
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        serializer = new JsonEventSerializer<>(objectMapper, TestData.class);
        eventFactory = new EventFactory(() -> 0L);
        
        // Create the NatsValue instance to test
        natsValue = new NatsValue<>(keyValue, testKey, serializer, eventFactory);
    }
    
    @AfterEach
    void deleteTestBucket() throws IOException, JetStreamApiException {
        if (keyValue != null) {
            kvm.delete(bucketName);
        }
    }

    @Test
    void isPresentReturnsFalseWhenValueDoesNotExist() {
        assertFalse(natsValue.isPresent());
    }

    @Test
    void getReturnsNullWhenValueDoesNotExist() {
        assertNull(natsValue.get());
    }

    @Test
    void setAndGetValueSuccessfully() {
        // Set the value
        natsValue.set(TEST_VALUE);
        
        // Verify it exists and can be retrieved
        assertTrue(natsValue.isPresent());
        TestData retrieved = natsValue.get();
        assertNotNull(retrieved);
        assertEquals(TEST_VALUE.data(), retrieved.data());
    }

    @Test
    void deleteRemovesValue() {
        // Set then delete the value
        natsValue.set(TEST_VALUE);
        assertTrue(natsValue.isPresent());
        
        natsValue.delete();
        
        // Verify it's gone
        assertFalse(natsValue.isPresent());
        assertNull(natsValue.get());
    }

    @Test
    void watchReceivesValueChanges() throws JetStreamApiException, IOException, InterruptedException {
        // Set up a watcher with a countdown latch to wait for notifications
        CountDownLatch valueLatch = new CountDownLatch(1);
        AtomicReference<ValueEntry<TestData>> receivedEntry = new AtomicReference<>();
        
        ValueWatcher<TestData> watcher = new ValueWatcher<>() {
            @Override
            public void watch(ValueEntry<TestData> entry) {
                receivedEntry.set(entry);
                valueLatch.countDown();
            }

            @Override
            public void endOfData() {
                // Not used in this test
            }
        };
        
        // Start watching
        ValueWatchSubscription subscription = natsValue.watch(watcher);
        try {
            // Set a value to trigger the watcher
            natsValue.set(TEST_VALUE);
            
            // Wait for the notification
            boolean received = valueLatch.await(5, TimeUnit.SECONDS);
            assertTrue(received, "Did not receive watch notification in time");
            
            // Verify the received data
            ValueEntry<TestData> entry = receivedEntry.get();
            assertNotNull(entry);
            assertNotNull(entry.event());
            assertEquals(TEST_VALUE.data(), entry.event().payload().data());
            
        } finally {
            // Clean up
            subscription.unsubscribe();
        }
    }
    
    // Helper record for test data
    record TestData(String data) {}
    
}
