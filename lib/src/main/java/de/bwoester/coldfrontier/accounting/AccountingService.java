package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.data.Value;

/**
 * Handles accounting for one player.
 */
public class AccountingService {

    private final Ledger ledger;
    private final Value<TransactionMsg> transactions;

    public AccountingService(Ledger ledger, Value<TransactionMsg> transactions) {
        this.ledger = ledger;
        this.transactions = transactions;
    }

    public boolean executeTransaction(String planetId, TransactionMsg transactionMsg) {
        UserAccount userAccount = ledger.getUserAccount();
        PlanetAccount planetAccount = ledger.getPlanetAccount(planetId);
        if (userAccount.validateTransaction(transactionMsg)
                && planetAccount.validateTransaction(transactionMsg)) {
            transactions.set(transactionMsg);
            userAccount.executeTransaction(transactionMsg);
            planetAccount.executeTransaction(transactionMsg);
            return true;
        }
        return false;
    }
}
