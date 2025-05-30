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
class UserAccountTest {

    private static final String PLAYER_ID = "player-1";

    TestValues testValues;
    Value<Long> balance;

    @BeforeEach
    void setUp() {
        testValues = new TestValues();
        balance = testValues.util.get(Long.class, Keys.Accounting.playerAccount(PLAYER_ID));
    }

    @AfterEach
    void tearDown() {
        log.info("event log:\n{}", testValues.util.prettyPrint());
    }

    @ParameterizedTest
    @MethodSource
    void validateTransaction(long available, long tryingToSpend, boolean expectedResult) {
        balance.set(available);
        UserAccount userAccount = new UserAccount(balance);
        ResourceSetMsg amount = new ResourceSetMsg(PlanetResourceSetMsg.createDefault(), tryingToSpend);
        TransactionMsg transactionMsg = new TransactionMsg("", TransactionMsg.TransactionType.EXPENSE, amount);
        Assertions.assertEquals(expectedResult, userAccount.validateTransaction(transactionMsg));
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
        UserAccount userAccount = new UserAccount(balance);
        ResourceSetMsg amount = new ResourceSetMsg(PlanetResourceSetMsg.createDefault(), tryingToSpend);
        TransactionMsg transactionMsg = new TransactionMsg("", TransactionMsg.TransactionType.EXPENSE, amount);
        userAccount.executeTransaction(transactionMsg);
        Assertions.assertEquals(expectedBalance, userAccount.getBalance());
    }

    private static Stream<Arguments> executeTransaction() {
        return Stream.of(
                Arguments.of(2L, 1L, 1L)
        );
    }

}