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

import java.util.Map;
import java.util.stream.Stream;

@Slf4j
class AccountingServiceTest {

    private static final String PLAYER_ID = "player-1";
    private static final String PLANET_ID = "planet-1";

    private static final PlanetResourceSetMsg PLANET_RESOURCES_ONE = PlanetResourceSetMsg.createOne();
    private static final PlanetResourceSetMsg PLANET_RESOURCES_TWO = PLANET_RESOURCES_ONE.multiply(2);
    private static final PlanetResourceSetMsg PLANET_RESOURCES_THREE = PLANET_RESOURCES_ONE.multiply(3);

    private static final TransactionMsg TRANSACTION_INCOME_ONE = new TransactionMsg("income",
            TransactionMsg.TransactionType.INCOME, new ResourceSetMsg(PLANET_RESOURCES_ONE, 1));
    private static final TransactionMsg TRANSACTION_EXPENSE_ONE = new TransactionMsg("expense",
            TransactionMsg.TransactionType.EXPENSE, new ResourceSetMsg(PLANET_RESOURCES_ONE, 1));

    EventLogStub eventLogStub;
    GameEventLog<TransactionMsg> transactions;
    PlayerLedger playerLedger;

    @BeforeEach
    void setUp() {
        eventLogStub = new EventLogStub();
        transactions = eventLogStub.inMemoryGameEventLog.viewOfType(TransactionMsg.class,
                GameEventSubject.Accounting.playerTransactions(PLAYER_ID));
        playerLedger = createLedger(2, PLANET_RESOURCES_TWO);
    }

    @AfterEach
    void tearDown() {
        log.info("event log:\n{}", eventLogStub.inMemoryGameEventLog.prettyPrint());
    }

    @ParameterizedTest
    @MethodSource
    void executeTransaction(String planetId, TransactionMsg transaction, long expectedPlayerBalance, PlanetResourceSetMsg expectedPlanetBalance) {
        AccountingService accountingService = new AccountingService(playerLedger, transactions);
        accountingService.executeTransaction(planetId, transaction);
        Assertions.assertEquals(expectedPlayerBalance, playerLedger.getGlobalAccount().getBalance());
        Assertions.assertEquals(expectedPlanetBalance, playerLedger.getPlanetAccount(planetId).getBalance());
    }

    private static Stream<Arguments> executeTransaction() {
        return Stream.of(
                Arguments.of(PLANET_ID, TRANSACTION_INCOME_ONE, 3, PLANET_RESOURCES_THREE),
                Arguments.of(PLANET_ID, TRANSACTION_EXPENSE_ONE, 1, PLANET_RESOURCES_ONE)
        );
    }

    private PlayerLedger createLedger(long playerBalance, PlanetResourceSetMsg planetBalance) {
        GameEventLog<Long> playerBalanceLog = eventLogStub.inMemoryGameEventLog.viewOfType(Long.class,
                GameEventSubject.Accounting.playerAccount(PLAYER_ID));
        playerBalanceLog.add(playerBalance);
        GlobalAccount globalAccount = new GlobalAccount(playerBalanceLog);
        GameEventLog<PlanetResourceSetMsg> planetBalanceLog = eventLogStub.inMemoryGameEventLog.viewOfType(PlanetResourceSetMsg.class,
                GameEventSubject.Accounting.planetAccount(PLANET_ID));
        planetBalanceLog.add(planetBalance);
        PlanetAccount planetAccount = new PlanetAccount(planetBalanceLog);
        return new PlayerLedger(globalAccount, Map.of(PLANET_ID, planetAccount));
    }
}