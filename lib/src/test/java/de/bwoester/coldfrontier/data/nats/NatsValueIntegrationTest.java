package de.bwoester.coldfrontier.data.nats;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.bwoester.coldfrontier.data.*;
import io.nats.client.*;
import io.nats.client.api.KeyValueConfiguration;
import io.nats.client.api.KeyValueStatus;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for NatsValue that connects to a real NATS server.
 * 
 * Note: This test requires a running NATS server on localhost:4222.
 * If the server is not available, the tests will be skipped.
 */
class NatsValueIntegrationTest {

    private static Connection natsConnection;
    private static JetStreamManagement jsm;
    private static KeyValueManagement kvm;
    private static long tick = 0;
    
    private KeyValue keyValue;
    private NatsValue<TestData> natsValue;
    private String bucketName;
    private String testKey;
    
    private static final String TEST_VALUE_DATA = "test-data";
    private static final TestData TEST_VALUE = new TestData(TEST_VALUE_DATA);
    
    private EventSerializer<TestData> serializer;
    private EventFactory eventFactory;

    @BeforeAll
    static void setupNats() {
        try {
            Options options = new Options.Builder()
                    .server("nats://demo.nats.io:4222")
                    .connectionTimeout(Duration.ofSeconds(5))
                    .build();
            
            natsConnection = Nats.connect(options);
            jsm = natsConnection.jetStreamManagement();
            kvm = natsConnection.keyValueManagement();
            
        } catch (Exception e) {
            // If we can't connect to NATS, mark the whole test class as disabled
            Assumptions.assumeTrue(false, "NATS server not available at localhost:4222: " + e.getMessage());
        }
    }
    
    @AfterAll
    static void tearDownNats() {
        if (natsConnection != null) {
            try {
                natsConnection.close();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    @BeforeEach
    void setUp() throws IOException, JetStreamApiException {
        // Skip tests if NATS connection failed to establish
        Assumptions.assumeTrue(natsConnection != null && natsConnection.getStatus() == Connection.Status.CONNECTED,
                "NATS connection not available");
        
        // Create a unique bucket name for this test run
        bucketName = "test-bucket-" + UUID.randomUUID();
        testKey = "test-key";
        
        // Create a KeyValue store
        KeyValueConfiguration kvc = KeyValueConfiguration.builder()
                .name(bucketName)
                .build();

        KeyValueStatus kvStatus = kvm.create(kvc);
        keyValue = natsConnection.keyValue(bucketName);
        
        // Create serializer and event factory
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        serializer = new JsonEventSerializer<>(objectMapper, TestData.class);
        eventFactory = new EventFactory(() -> tick);
        
        // Create the NatsValue instance to test
        natsValue = new NatsValue<>(keyValue, testKey, serializer, eventFactory);
    }
    
    @AfterEach
    void tearDown() {
        if (keyValue != null && natsConnection != null && natsConnection.getStatus() == Connection.Status.CONNECTED) {
            try {
                // Delete the test bucket
                kvm.delete(bucketName);
            } catch (IOException | JetStreamApiException e) {
                // Just log this, don't fail the test
                System.err.println("Error deleting KeyValue bucket: " + e.getMessage());
            }
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
