package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.EventLogStub;
import de.bwoester.coldfrontier.messaging.GameEventLog;
import de.bwoester.coldfrontier.messaging.GameEventSubject;
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

    EventLogStub eventLogStub;
    GameEventLog<PlanetResourceSetMsg> balance;

    @BeforeEach
    void setUp() {
        eventLogStub = new EventLogStub();
        balance = eventLogStub.inMemoryGameEventLog.viewOfType(
                PlanetResourceSetMsg.class,
                GameEventSubject.Accounting.planetAccount(PLANET_ID)
        );
    }

    @AfterEach
    void tearDown() {
        log.info("event log:\n{}", eventLogStub.inMemoryGameEventLog.prettyPrint());
    }

    @ParameterizedTest
    @MethodSource
    void validateTransaction(PlanetResourceSetMsg available, PlanetResourceSetMsg tryingToSpend, boolean expectedResult) {
        balance.add(available);
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
        balance.add(available);
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