package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.TestValues;
import de.bwoester.coldfrontier.data.Value;
import de.bwoester.coldfrontier.data.Keys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@Slf4j
class GlobalAccountTest {

    private static final String PLAYER_ID = "player-1";

    TestValues testValues;
    Value<Long> balance;

    @BeforeEach
    void setUp() {
        testValues = new TestValues();
        balance = testValues.util.create(Long.class, Keys.Accounting.playerAccount(PLAYER_ID));
    }

    @AfterEach
    void tearDown() {
        log.info("event log:\n{}", testValues.util.prettyPrint());
    }

    @ParameterizedTest
    @MethodSource
    void validateTransaction(long available, long tryingToSpend, boolean expectedResult) {
        balance.set(available);
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
        balance.set(available);
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