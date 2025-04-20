package de.bwoester.coldfrontier.input;

import de.bwoester.coldfrontier.TestInputMsg;
import de.bwoester.coldfrontier.data.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InputServiceTest {

    @Mock
    private Value<InputQueueMsg> newInputsLog;
    @Mock
    private Value<InputQueueMsg> startedInputsLog;
    @Mock
    private Value<InputQueueMsg> finishedInputsLog;
    @Mock
    private Value<InputQueueMsg> failedInputsLog;

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

        when(newInputsLog.get()).thenReturn(initialQueueMsg);

        // When
        inputService.add(inputMsg);

        // Then
        verify(newInputsLog).set(inputQueueMsgCaptor.capture());

        InputQueueMsg capturedMsg = inputQueueMsgCaptor.getValue();
        assertEquals(1, capturedMsg.msgQueue().size());
        assertEquals(inputMsg, capturedMsg.msgQueue().peek());
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

        when(newInputsLog.get()).thenReturn(initialNewQueueMsg);
        when(startedInputsLog.get()).thenReturn(initialStartedQueueMsg);

        // When
        Queue<TestInputMsg> result = inputService.startInputHandling(
                TestInputMsg.class,
                input -> "planet-1".equals(input.planetId())
        );

        // Then
        // Verify the input was moved to started queue
        verify(startedInputsLog).set(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedStartedMsg = inputQueueMsgCaptor.getValue();
        assertEquals(1, capturedStartedMsg.msgQueue().size());
        assertEquals(inputMsg1, capturedStartedMsg.msgQueue().peek());

        // Verify the input was removed from new queue
        verify(newInputsLog).set(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedNewMsg = inputQueueMsgCaptor.getValue();
        assertEquals(1, capturedNewMsg.msgQueue().size());
        assertEquals(inputMsg2, capturedNewMsg.msgQueue().peek());

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

        when(startedInputsLog.get()).thenReturn(initialStartedQueueMsg);
        when(finishedInputsLog.get()).thenReturn(initialFinishedQueueMsg);

        // When
        inputService.finishInputHandling(inputMsg);

        // Then
        // Verify the input was moved to finished queue
        verify(finishedInputsLog).set(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedFinishedMsg = inputQueueMsgCaptor.getValue();
        assertEquals(1, capturedFinishedMsg.msgQueue().size());
        assertEquals(inputMsg, capturedFinishedMsg.msgQueue().peek());

        // Verify the input was removed from started queue
        verify(startedInputsLog).set(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedStartedMsg = inputQueueMsgCaptor.getValue();
        assertEquals(0, capturedStartedMsg.msgQueue().size());
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

        when(startedInputsLog.get()).thenReturn(initialStartedQueueMsg);
        when(failedInputsLog.get()).thenReturn(initialFailedQueueMsg);

        // When
        inputService.failedInputHandling(inputMsg, exception);

        // Then
        // Verify the input was moved to failed queue as a FailedInputMsg
        verify(failedInputsLog).set(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedFailedMsg = inputQueueMsgCaptor.getValue();
        assertEquals(1, capturedFailedMsg.msgQueue().size());
        assertInstanceOf(FailedInputMsg.class, capturedFailedMsg.msgQueue().peek());

        FailedInputMsg failedInputMsg = (FailedInputMsg) capturedFailedMsg.msgQueue().peek();
        assertEquals(inputMsg, failedInputMsg.inputMsg());
        assertEquals(exception, failedInputMsg.reason());

        // Verify the input was removed from started queue
        verify(startedInputsLog).set(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedStartedMsg = inputQueueMsgCaptor.getValue();
        assertEquals(0, capturedStartedMsg.msgQueue().size());
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

        when(newInputsLog.isPresent()).thenReturn(true);
        when(newInputsLog.get()).thenReturn(newInputQueueMsg);

        // Empty started and failed queues
        when(startedInputsLog.isPresent()).thenReturn(false);
        when(failedInputsLog.isPresent()).thenReturn(false);

        // When
        inputService.verifyState(currentTick);

        // Then
        // Verify old inputs were removed
        verify(newInputsLog).set(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedNewMsg = inputQueueMsgCaptor.getValue();
        assertEquals(1, capturedNewMsg.msgQueue().size());
        assertEquals(recentInput, capturedNewMsg.msgQueue().peek());
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
        when(newInputsLog.isPresent()).thenReturn(false);

        when(startedInputsLog.isPresent()).thenReturn(true);
        when(startedInputsLog.get()).thenReturn(startedInputQueueMsg);

        // For failedInputHandling
        when(failedInputsLog.isPresent()).thenReturn(false);

        // When
        inputService.verifyState(currentTick);

        // Then
        // Verify an empty input queue was set on started inputs
        verify(startedInputsLog).set(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedStartedMsg = inputQueueMsgCaptor.getValue();
        assertEquals(0, capturedStartedMsg.msgQueue().size());
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
        when(newInputsLog.isPresent()).thenReturn(false);
        when(startedInputsLog.isPresent()).thenReturn(false);

        when(failedInputsLog.isPresent()).thenReturn(true);
        when(failedInputsLog.get()).thenReturn(failedInputQueueMsg);

        // When
        inputService.verifyState(currentTick);

        // Then
        // Verify an empty input queue was set on failed inputs
        verify(failedInputsLog).set(inputQueueMsgCaptor.capture());
        InputQueueMsg capturedFailedMsg = inputQueueMsgCaptor.getValue();
        assertEquals(0, capturedFailedMsg.msgQueue().size());
    }

}
