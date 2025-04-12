package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.EventLogStub;
import de.bwoester.coldfrontier.messaging.GameEventLog;
import de.bwoester.coldfrontier.messaging.GameEventSubject;
import de.bwoester.coldfrontier.user.UserMsg;
import de.bwoester.coldfrontier.user.UserProfileMsg;
import de.bwoester.coldfrontier.user.UserSettingsMsg;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class GlobalAccountTest {

    EventLogStub eventLogStub;
    UserMsg user;
    GameEventLog<Long> balance;

    @BeforeEach
    void setUp() {
        eventLogStub = new EventLogStub();
        user = new UserMsg(
                "player-1",
                Collections.emptyList(),
                new UserSettingsMsg("de"),
                new UserProfileMsg(List.of("planet-1"))
        );
        balance = eventLogStub.inMemoryGameEventLog.viewOfType(Long.class, GameEventSubject.Accounting.playerAccount(user.id()));
    }

    @ParameterizedTest
    @MethodSource
    void validateTransaction(long available, long tryingToSpend, boolean expectedResult) {
        balance.add(available);
        GlobalAccount globalAccount = new GlobalAccount(balance);
        ResourceSetMsg amount = new ResourceSetMsg(PlanetResourceSetMsg.createDefault(), tryingToSpend);
        TransactionMsg transactionMsg = new TransactionMsg("", TransactionMsg.TransactionType.EXPENSE, amount);
        Assertions.assertEquals(expectedResult, globalAccount.validateTransaction(transactionMsg));
    }

    private static Stream<Arguments> validateTransaction() {
        return Stream.of(
                Arguments.of(2L, 1L, true),
                Arguments.of(2L, 2L, true),
                Arguments.of(2L, 3L, false)
        );
    }

    @ParameterizedTest
    @MethodSource
    void executeTransaction(long available, long tryingToSpend, long expectedBalance) {
        balance.add(available);
        GlobalAccount globalAccount = new GlobalAccount(balance);
        ResourceSetMsg amount = new ResourceSetMsg(PlanetResourceSetMsg.createDefault(), tryingToSpend);
        TransactionMsg transactionMsg = new TransactionMsg("", TransactionMsg.TransactionType.EXPENSE, amount);
        globalAccount.executeTransaction(transactionMsg);
        Assertions.assertEquals(expectedBalance, globalAccount.getBalance());
    }

    private static Stream<Arguments> executeTransaction() {
        return Stream.of(
                Arguments.of(2L, 1L, 1L)
        );
    }

}