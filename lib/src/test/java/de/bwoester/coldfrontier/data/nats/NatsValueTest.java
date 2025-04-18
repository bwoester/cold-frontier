package de.bwoester.coldfrontier.data.nats;

import de.bwoester.coldfrontier.data.Event;
import de.bwoester.coldfrontier.data.EventFactory;
import de.bwoester.coldfrontier.data.ValueWatcher;
import de.bwoester.coldfrontier.data.ValueWatchSubscription;
import io.nats.client.JetStreamApiException;
import io.nats.client.KeyValue;
import io.nats.client.api.KeyValueEntry;
import io.nats.client.api.KeyValueWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NatsValueTest {

    private NatsValue<String> natsValue;

    @Mock
    private KeyValue keyValue;

    @Mock
    private EventSerializer<String> serializer;

    @Mock
    private EventFactory eventFactory;

    @Mock
    private KeyValueEntry keyValueEntry;

    @Mock
    private Event<String> event;

    private static final String TEST_KEY = "test-key";
    private static final String TEST_VALUE = "test-value";
    private static final byte[] SERIALIZED_VALUE = "serialized-value".getBytes(StandardCharsets.UTF_8);

    @BeforeEach
    void setUp() {
        natsValue = new NatsValue<>(keyValue, TEST_KEY, serializer, eventFactory);
    }

    @Test
    void isPresentReturnsTrueWhenValueExists() throws JetStreamApiException, IOException {
        when(keyValue.get(TEST_KEY)).thenReturn(keyValueEntry);

        assertTrue(natsValue.isPresent());
        verify(keyValue).get(TEST_KEY);
    }

    @Test
    void isPresentReturnsFalseWhenValueDoesNotExist() throws JetStreamApiException, IOException {
        when(keyValue.get(TEST_KEY)).thenReturn(null);

        assertFalse(natsValue.isPresent());
        verify(keyValue).get(TEST_KEY);
    }

    @Test
    void isPresentReturnsFalseWhenExceptionThrown() throws JetStreamApiException, IOException {
        when(keyValue.get(TEST_KEY)).thenThrow(new IOException("Test exception"));

        assertFalse(natsValue.isPresent());
        verify(keyValue).get(TEST_KEY);
    }

    @Test
    void getReturnsValueWhenExists() throws JetStreamApiException, IOException {
        when(keyValue.get(TEST_KEY)).thenReturn(keyValueEntry);
        when(keyValueEntry.getValue()).thenReturn(SERIALIZED_VALUE);
        when(serializer.deserialize(SERIALIZED_VALUE)).thenReturn(event);
        when(event.payload()).thenReturn(TEST_VALUE);

        String result = natsValue.get();

        assertEquals(TEST_VALUE, result);
        verify(keyValue).get(TEST_KEY);
        verify(serializer).deserialize(SERIALIZED_VALUE);
        verify(event).payload();
    }

    @Test
    void getReturnsNullWhenValueDoesNotExist() throws JetStreamApiException, IOException {
        when(keyValue.get(TEST_KEY)).thenReturn(null);

        String result = natsValue.get();

        assertNull(result);
        verify(keyValue).get(TEST_KEY);
        verifyNoInteractions(serializer);
    }

    @Test
    void getThrowsRuntimeExceptionWhenExceptionOccurs() throws JetStreamApiException, IOException {
        when(keyValue.get(TEST_KEY)).thenThrow(new IOException("Test exception"));

        assertThrows(RuntimeException.class, () -> natsValue.get());
        verify(keyValue).get(TEST_KEY);
    }

    @Test
    void setStoresValueSuccessfully() throws JetStreamApiException, IOException {
        when(eventFactory.createEvent(TEST_VALUE)).thenReturn(event);
        when(serializer.serialize(event)).thenReturn(SERIALIZED_VALUE);

        natsValue.set(TEST_VALUE);

        verify(eventFactory).createEvent(TEST_VALUE);
        verify(serializer).serialize(event);
        verify(keyValue).put(eq(TEST_KEY), eq(SERIALIZED_VALUE));
    }

    @Test
    void setThrowsRuntimeExceptionWhenExceptionOccurs() throws JetStreamApiException, IOException {
        when(eventFactory.createEvent(TEST_VALUE)).thenReturn(event);
        when(serializer.serialize(event)).thenReturn(SERIALIZED_VALUE);
        doThrow(new IOException("Test exception")).when(keyValue).put(eq(TEST_KEY), eq(SERIALIZED_VALUE));

        assertThrows(RuntimeException.class, () -> natsValue.set(TEST_VALUE));

        verify(eventFactory).createEvent(TEST_VALUE);
        verify(serializer).serialize(event);
        verify(keyValue).put(eq(TEST_KEY), eq(SERIALIZED_VALUE));
    }

    @Test
    void deleteRemovesValueSuccessfully() throws JetStreamApiException, IOException {
        natsValue.delete();

        verify(keyValue).delete(TEST_KEY);
    }

    @Test
    void deleteThrowsRuntimeExceptionWhenExceptionOccurs() throws JetStreamApiException, IOException {
        doThrow(new IOException("Test exception")).when(keyValue).delete(TEST_KEY);

        assertThrows(RuntimeException.class, () -> natsValue.delete());

        verify(keyValue).delete(TEST_KEY);
    }

//    @Test
//    void watchCreatesSubscriptionSuccessfully() throws JetStreamApiException, IOException, InterruptedException {
//        @SuppressWarnings("unchecked")
//        ValueWatcher<String> valueWatcher = mock(ValueWatcher.class);
//
//        io.nats.client.api.KeyValueWatchSubscription mockNatsSubscription = mock(io.nats.client.api.KeyValueWatchSubscription.class);
//
//        ArgumentCaptor<KeyValueWatcher> watcherCaptor = ArgumentCaptor.forClass(KeyValueWatcher.class);
//
//        when(keyValue.watch(eq(TEST_KEY), watcherCaptor.capture())).thenReturn(mockNatsSubscription);
//
//        ValueWatchSubscription subscription = natsValue.watch(valueWatcher);
//
//        assertNotNull(subscription);
//        assertTrue(subscription instanceof NatsValueWatchSubscription);
//
//        // Verify that the watcher passed to KeyValue.watch is a NatsValueWatcher
//        KeyValueWatcher capturedWatcher = watcherCaptor.getValue();
//        assertTrue(capturedWatcher instanceof NatsValueWatcher);
//
//        verify(keyValue).watch(eq(TEST_KEY), any(NatsValueWatcher.class));
//    }
}
