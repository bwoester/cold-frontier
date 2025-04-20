package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.data.Value;

public class PlanetAccount {

    private final Value<PlanetResourceSetMsg> balance;

    public PlanetAccount(Value<PlanetResourceSetMsg> balance) {
        this.balance = balance;
    }

    public PlanetResourceSetMsg getBalance() {
        return balance.get();
    }

    public boolean validateTransaction(TransactionMsg transactionMsg) {
        return calculateNewBalance(transactionMsg).allGreaterOrEqual(0);
    }

    public void executeTransaction(TransactionMsg transactionMsg) {
        PlanetResourceSetMsg newBalance = calculateNewBalance(transactionMsg);
        balance.set(newBalance);
    }

    private PlanetResourceSetMsg calculateNewBalance(TransactionMsg transactionMsg) {
        PlanetResourceSetMsg currentBalance = balance.get();
        PlanetResourceSetMsg diff = transactionMsg.amount().planetResources();
        return switch (transactionMsg.type()) {
            case INCOME, TRANSFER -> currentBalance.add(diff);
            case EXPENSE -> currentBalance.subtract(diff);
        };
    }

}
