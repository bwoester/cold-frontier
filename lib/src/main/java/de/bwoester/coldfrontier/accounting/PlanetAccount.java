package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.messaging.EventLog;

public class PlanetAccount {

    private final EventLog<PlanetResourceSetMsg> balance;

    public PlanetAccount(EventLog<PlanetResourceSetMsg> balance) {
        this.balance = balance;
    }

    public PlanetResourceSetMsg getBalance() {
        return balance.getLatest();
    }

    public boolean validateTransaction(TransactionMsg transactionMsg) {
        return calculateNewBalance(transactionMsg).allGreaterOrEqual(0);
    }

    public void executeTransaction(TransactionMsg transactionMsg) {
        PlanetResourceSetMsg newBalance = calculateNewBalance(transactionMsg);
        balance.add(newBalance);
    }

    private PlanetResourceSetMsg calculateNewBalance(TransactionMsg transactionMsg) {
        PlanetResourceSetMsg currentBalance = balance.getLatest();
        PlanetResourceSetMsg diff = transactionMsg.amount().planetResources();
        return switch (transactionMsg.type()) {
            case INCOME, TRANSFER -> currentBalance.add(diff);
            case EXPENSE -> currentBalance.subtract(diff);
        };
    }

}
