package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.user.UserMsg;

import java.util.Map;
import java.util.function.Supplier;

public class AccountingService {

    private final Supplier<Long> tickSupplier;
    private final UserMsg user;

    public AccountingService(Supplier<Long> tickSupplier, UserMsg user) {
        this.tickSupplier = tickSupplier;
        this.user = user;
    }

    public boolean executeTransaction(String planetId, TransactionMsg transactionMsg) {
        PlayerLedgerMsg playerLedger = getPlayerLedger();
        GlobalAccount globalAccount = playerLedger.globalAccount();
        PlanetAccount planetAccount = playerLedger.planetAccounts().get(planetId);
        if (globalAccount.validateTransaction(transactionMsg)
                && planetAccount.validateTransaction(transactionMsg)) {
            globalAccount.executeTransaction(transactionMsg);
            planetAccount.executeTransaction(transactionMsg);
            planetAccount.recordTransaction(tickSupplier.get(), transactionMsg);
            return true;
        }
        return false;
    }

    // TODO get persisted ledger or create
    private PlayerLedgerMsg getPlayerLedger() {
        String key = String.format("accounting/%s", user.id());

        return new PlayerLedgerMsg(
                new GlobalAccount(0),
                Map.of(
                        "planet-1", new PlanetAccount(new PlanetResourceSetMsg(0, 0, 0))
                )
        );
    }
}
