package de.bwoester.coldfrontier.input;

import de.bwoester.coldfrontier.TestInputMsg;
import de.bwoester.coldfrontier.messaging.GameEventLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InputServiceTest {

    @Mock
    private GameEventLog<InputQueueMsg> newInputsLog;

    @Mock
    private GameEventLog<InputQueueMsg> startedInputsLog;

    @Mock
    private GameEventLog<InputQueueMsg> finishedInputsLog;

    @Mock
    private GameEventLog<InputQueueMsg> failedInputsLog;

    @Captor
    private ArgumentCaptor<InputQueueMsg> inputQueueMsgCaptor;

    private InputService inputService;

    @BeforeEach
    void setUp() {
        inputService = new InputService(newInputsLog, startedInputsLog, finishedInputsLog, failedInputsLog);
    }

    @Test
    void add_ShouldAddInputToNewInputsLog() {
        // Given
        TestInputMsg inputMsg = new TestInputMsg(1L);
        Queue<InputMsg> initialQueue = new LinkedList<>();
        InputQueueMsg initialQueueMsg = new InputQueueMsg(initialQueue);

        when(newInputsLog.getLatest()).thenReturn(initialQueueMsg);

        // When
        inputService.add(inputMsg);

        // Then
        verify(newInputsLog).add(inputQueueMsgCaptor.capture());

        InputQueueMsg capturedMsg = inputQueueMsgCaptor.getValue();
        assertEquals(1, capturedMsg.queuedInput().size());
        assertEquals(inputMsg, capturedMsg.queuedInput().peek());
    }

    @Test
    void startInputHandling_ShouldMoveMatchingInputsFromNewToStarted() {
        // Given
        TestInputMsg inputMsg1 = new TestInputMsg(1L, "planet-1");
        TestInputMsg inputMsg2 = new TestInputMsg(2L, "planet-2");

        Queue<InputMsg> initialNewQueue = new LinkedList<>();
        initialNewQueue.add(inputMsg1);
        initialNewQueue.add(inputMsg2);
        InputQueueMsg initialNewQueueMsg = new InputQueueMsg(initialNewQueue);

        Queue<InputMsg> initialStartedQueue = new LinkedList<>();
        InputQueueMsg initialStartedQueueMsg = new InputQueueMsg(initialStartedQueue);

        when(newInputsLog.getLatest()).thenReturn(initialNewQueueMsg);
        when(startedInputsLog.getLatest()).thenReturn(initialStartedQueueMsg);

        // When
        Queue<TestInputMsg> result = inputService.startInputHandling(
                TestInputMsg.class,
                input -> "planet-1".equals(input.planetId())
        );

        // Then
        // Verify the input was moved to started queue
        verify(startedInputsLog).add(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedStartedMsg = inputQueueMsgCaptor.getValue();
        assertEquals(1, capturedStartedMsg.queuedInput().size());
        assertEquals(inputMsg1, capturedStartedMsg.queuedInput().peek());

        // Verify the input was removed from new queue
        verify(newInputsLog).add(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedNewMsg = inputQueueMsgCaptor.getValue();
        assertEquals(1, capturedNewMsg.queuedInput().size());
        assertEquals(inputMsg2, capturedNewMsg.queuedInput().peek());

        // Verify the returned queue contains the matched inputs
        assertEquals(1, result.size());
        assertEquals(inputMsg1, result.peek());
    }

    @Test
    void finishInputHandling_ShouldMoveInputFromStartedToFinished() {
        // Given
        TestInputMsg inputMsg = new TestInputMsg(1L);

        Queue<InputMsg> initialStartedQueue = new LinkedList<>();
        initialStartedQueue.add(inputMsg);
        InputQueueMsg initialStartedQueueMsg = new InputQueueMsg(initialStartedQueue);

        Queue<InputMsg> initialFinishedQueue = new LinkedList<>();
        InputQueueMsg initialFinishedQueueMsg = new InputQueueMsg(initialFinishedQueue);

        when(startedInputsLog.getLatest()).thenReturn(initialStartedQueueMsg);
        when(finishedInputsLog.getLatest()).thenReturn(initialFinishedQueueMsg);

        // When
        inputService.finishInputHandling(inputMsg);

        // Then
        // Verify the input was moved to finished queue
        verify(finishedInputsLog).add(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedFinishedMsg = inputQueueMsgCaptor.getValue();
        assertEquals(1, capturedFinishedMsg.queuedInput().size());
        assertEquals(inputMsg, capturedFinishedMsg.queuedInput().peek());

        // Verify the input was removed from started queue
        verify(startedInputsLog).add(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedStartedMsg = inputQueueMsgCaptor.getValue();
        assertEquals(0, capturedStartedMsg.queuedInput().size());
    }

    @Test
    void failedInputHandling_ShouldMoveInputFromStartedToFailed() {
        // Given
        TestInputMsg inputMsg = new TestInputMsg(1L);
        RuntimeException exception = new RuntimeException("Test error");

        Queue<InputMsg> initialStartedQueue = new LinkedList<>();
        initialStartedQueue.add(inputMsg);
        InputQueueMsg initialStartedQueueMsg = new InputQueueMsg(initialStartedQueue);

        Queue<InputMsg> initialFailedQueue = new LinkedList<>();
        InputQueueMsg initialFailedQueueMsg = new InputQueueMsg(initialFailedQueue);

        when(startedInputsLog.getLatest()).thenReturn(initialStartedQueueMsg);
        when(failedInputsLog.getLatest()).thenReturn(initialFailedQueueMsg);

        // When
        inputService.failedInputHandling(inputMsg, exception);

        // Then
        // Verify the input was moved to failed queue as a FailedInputMsg
        verify(failedInputsLog).add(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedFailedMsg = inputQueueMsgCaptor.getValue();
        assertEquals(1, capturedFailedMsg.queuedInput().size());
        assertInstanceOf(FailedInputMsg.class, capturedFailedMsg.queuedInput().peek());

        FailedInputMsg failedInputMsg = (FailedInputMsg) capturedFailedMsg.queuedInput().peek();
        assertEquals(inputMsg, failedInputMsg.inputMsg());
        assertEquals(exception, failedInputMsg.reason());

        // Verify the input was removed from started queue
        verify(startedInputsLog).add(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedStartedMsg = inputQueueMsgCaptor.getValue();
        assertEquals(0, capturedStartedMsg.queuedInput().size());
    }

    @Test
    void verifyState_ShouldCleanUpOldInputs() {
        // Given
        long currentTick = 5L;
        TestInputMsg oldInput = new TestInputMsg(2L); // 3 ticks ago
        TestInputMsg recentInput = new TestInputMsg(4L); // 1 tick ago

        Queue<InputMsg> newInputQueue = new LinkedList<>();
        newInputQueue.add(oldInput);
        newInputQueue.add(recentInput);
        InputQueueMsg newInputQueueMsg = new InputQueueMsg(newInputQueue);

        when(newInputsLog.isEmpty()).thenReturn(false);
        when(newInputsLog.getLatest()).thenReturn(newInputQueueMsg);

        // Empty started and failed queues
        when(startedInputsLog.isEmpty()).thenReturn(true);
        when(failedInputsLog.isEmpty()).thenReturn(true);

        // When
        inputService.verifyState(currentTick);

        // Then
        // Verify old inputs were removed
        verify(newInputsLog).add(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedNewMsg = inputQueueMsgCaptor.getValue();
        assertEquals(1, capturedNewMsg.queuedInput().size());
        assertEquals(recentInput, capturedNewMsg.queuedInput().peek());
    }

    @Test
    void verifyState_ShouldHandleStartedInputs() {
        // Given
        long currentTick = 5L;
        TestInputMsg startedInput = new TestInputMsg(4L);

        Queue<InputMsg> startedInputQueue = new LinkedList<>();
        startedInputQueue.add(startedInput);
        InputQueueMsg startedInputQueueMsg = new InputQueueMsg(startedInputQueue);

        // Empty new and failed queues
        when(newInputsLog.isEmpty()).thenReturn(true);

        when(startedInputsLog.isEmpty()).thenReturn(false);
        when(startedInputsLog.getLatest()).thenReturn(startedInputQueueMsg);

        // For failedInputHandling
        when(failedInputsLog.isEmpty()).thenReturn(true);

        // When
        inputService.verifyState(currentTick);

        // Then
        // Verify an empty input queue was set on started inputs
        verify(startedInputsLog).add(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedStartedMsg = inputQueueMsgCaptor.getValue();
        assertEquals(0, capturedStartedMsg.queuedInput().size());
    }

    @Test
    void verifyState_ShouldLogFailedInputs() {
        // Given
        long currentTick = 5L;
        TestInputMsg originalInput = new TestInputMsg(4L);
        RuntimeException exception = new RuntimeException("Test error");
        FailedInputMsg failedInputMsg = new FailedInputMsg(4L, originalInput, exception);

        Queue<InputMsg> failedInputQueue = new LinkedList<>();
        failedInputQueue.add(failedInputMsg);
        InputQueueMsg failedInputQueueMsg = new InputQueueMsg(failedInputQueue);

        // Empty new and started queues
        when(newInputsLog.isEmpty()).thenReturn(true);
        when(startedInputsLog.isEmpty()).thenReturn(true);

        when(failedInputsLog.isEmpty()).thenReturn(false);
        when(failedInputsLog.getLatest()).thenReturn(failedInputQueueMsg);

        // When
        inputService.verifyState(currentTick);

        // Then
        // Verify an empty input queue was set on failed inputs
        verify(failedInputsLog).add(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedFailedMsg = inputQueueMsgCaptor.getValue();
        assertEquals(0, capturedFailedMsg.queuedInput().size());
    }

}
