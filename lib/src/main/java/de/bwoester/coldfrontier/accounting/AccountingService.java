package de.bwoester.coldfrontier.accounting;

/**
 * Handles accounting for one player.
 */
public class AccountingService {

    private final PlayerLedgerMsg playerLedger;

    public AccountingService(PlayerLedgerMsg playerLedger) {
        this.playerLedger = playerLedger;
    }

    public boolean executeTransaction(String planetId, TransactionMsg transactionMsg) {
        GlobalAccount globalAccount = playerLedger.globalAccount();
        PlanetAccount planetAccount = playerLedger.planetAccounts().get(planetId);
        if (globalAccount.validateTransaction(transactionMsg)
                && planetAccount.validateTransaction(transactionMsg)) {
            globalAccount.executeTransaction(transactionMsg);
            planetAccount.executeTransaction(transactionMsg);
            return true;
        }
        return false;
    }

}
