package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.messaging.GameEventLog;

/**
 * Handles accounting for one player.
 */
public class AccountingService {

    private final PlayerLedger playerLedger;
    private final GameEventLog<TransactionMsg> transactions;

    public AccountingService(PlayerLedger playerLedger, GameEventLog<TransactionMsg> transactions) {
        this.playerLedger = playerLedger;
        this.transactions = transactions;
    }

    public boolean executeTransaction(String planetId, TransactionMsg transactionMsg) {
        GlobalAccount globalAccount = playerLedger.getGlobalAccount();
        PlanetAccount planetAccount = playerLedger.getPlanetAccount(planetId);
        if (globalAccount.validateTransaction(transactionMsg)
                && planetAccount.validateTransaction(transactionMsg)) {
            transactions.add(transactionMsg);
            globalAccount.executeTransaction(transactionMsg);
            planetAccount.executeTransaction(transactionMsg);
            return true;
        }
        return false;
    }

}
