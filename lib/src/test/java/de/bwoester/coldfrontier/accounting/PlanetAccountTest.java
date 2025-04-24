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
class PlanetAccountTest {

    private static final String PLANET_ID = "planet-1";
    private static final PlanetResourceSetMsg ONE = new PlanetResourceSetMsg(1, 1, 1);
    private static final PlanetResourceSetMsg TWO = ONE.multiply(2);

    TestValues testValues;
    Value<PlanetResourceSetMsg> balance;

    @BeforeEach
    void setUp() {
        testValues = new TestValues();
        balance = testValues.util.get(
                PlanetResourceSetMsg.class,
                Keys.Accounting.planetAccount(PLANET_ID)
        );
    }

    @AfterEach
    void tearDown() {
        log.info("event log:\n{}", testValues.util.prettyPrint());
    }

    @ParameterizedTest
    @MethodSource
    void validateTransaction(PlanetResourceSetMsg available, PlanetResourceSetMsg tryingToSpend, boolean expectedResult) {
        balance.set(available);
        PlanetAccount account = new PlanetAccount(balance);
        ResourceSetMsg amount = new ResourceSetMsg(tryingToSpend, 0);
        TransactionMsg transactionMsg = new TransactionMsg("", TransactionMsg.TransactionType.EXPENSE, amount);
        Assertions.assertEquals(expectedResult, account.validateTransaction(transactionMsg));
    }

    private static Stream<Arguments> validateTransaction() {
        return Stream.of(
                Arguments.of(TWO, ONE, true),
                Arguments.of(TWO, TWO, true),
                Arguments.of(ONE, TWO, false)
        );
    }

    @ParameterizedTest
    @MethodSource
    void executeTransaction(PlanetResourceSetMsg available, PlanetResourceSetMsg tryingToSpend, PlanetResourceSetMsg expectedBalance) {
        balance.set(available);
        PlanetAccount account = new PlanetAccount(balance);
        ResourceSetMsg amount = new ResourceSetMsg(tryingToSpend, 0);
        TransactionMsg transactionMsg = new TransactionMsg("", TransactionMsg.TransactionType.EXPENSE, amount);
        account.executeTransaction(transactionMsg);
        Assertions.assertEquals(expectedBalance, account.getBalance());
    }

    private static Stream<Arguments> executeTransaction() {
        return Stream.of(
                Arguments.of(TWO, ONE, ONE)
        );
    }

}