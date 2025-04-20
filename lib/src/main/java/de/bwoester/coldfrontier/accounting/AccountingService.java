package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.data.Value;

/**
 * Handles accounting for one player.
 */
public class AccountingService {

    private final PlayerLedger playerLedger;
    private final Value<TransactionMsg> transactions;

    public AccountingService(PlayerLedger playerLedger, Value<TransactionMsg> transactions) {
        this.playerLedger = playerLedger;
        this.transactions = transactions;
    }

    public boolean executeTransaction(String planetId, TransactionMsg transactionMsg) {
        GlobalAccount globalAccount = playerLedger.getGlobalAccount();
        PlanetAccount planetAccount = playerLedger.getPlanetAccount(planetId);
        if (globalAccount.validateTransaction(transactionMsg)
                && planetAccount.validateTransaction(transactionMsg)) {
            transactions.set(transactionMsg);
            globalAccount.executeTransaction(transactionMsg);
            planetAccount.executeTransaction(transactionMsg);
            return true;
        }
        return false;
    }

}
