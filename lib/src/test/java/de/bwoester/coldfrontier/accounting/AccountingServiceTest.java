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

    TestValues testValues;
    Value<TransactionMsg> transactions;
    Ledger ledger;

    @BeforeEach
    void setUp() {
        testValues = new TestValues();
        transactions = testValues.util.get(TransactionMsg.class,
                Keys.Accounting.playerTransactions(PLAYER_ID));
        ledger = createLedger(2, PLANET_RESOURCES_TWO);
    }

    @AfterEach
    void tearDown() {
        log.info("event log:\n{}", testValues.util.prettyPrint());
    }

    @ParameterizedTest
    @MethodSource
    void executeTransaction(String planetId, TransactionMsg transaction, long expectedPlayerBalance, PlanetResourceSetMsg expectedPlanetBalance) {
        AccountingService accountingService = new AccountingService(ledger, transactions);
        accountingService.executeTransaction(planetId, transaction);
        Assertions.assertEquals(expectedPlayerBalance, ledger.getUserAccount().getBalance());
        Assertions.assertEquals(expectedPlanetBalance, ledger.getPlanetAccount(planetId).getBalance());
    }

    private static Stream<Arguments> executeTransaction() {
        return Stream.of(
                Arguments.of(PLANET_ID, TRANSACTION_INCOME_ONE, 3, PLANET_RESOURCES_THREE),
                Arguments.of(PLANET_ID, TRANSACTION_EXPENSE_ONE, 1, PLANET_RESOURCES_ONE)
        );
    }

    private Ledger createLedger(long playerBalance, PlanetResourceSetMsg planetBalance) {
        Value<Long> playerBalanceLog = testValues.util.get(Long.class,
                Keys.Accounting.playerAccount(PLAYER_ID));
        playerBalanceLog.set(playerBalance);
        UserAccount userAccount = new UserAccount(playerBalanceLog);
        Value<PlanetResourceSetMsg> planetBalanceLog = testValues.util.get(PlanetResourceSetMsg.class,
                Keys.Accounting.planetAccount(PLANET_ID));
        planetBalanceLog.set(planetBalance);
        PlanetAccount planetAccount = new PlanetAccount(planetBalanceLog);
        return new Ledger(userAccount, Map.of(PLANET_ID, planetAccount));
    }
}