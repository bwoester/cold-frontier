package de.bwoester.coldfrontier.accounting;

import java.util.NavigableMap;
import java.util.TreeMap;

public class PlanetAccount {

    private final NavigableMap<Long, TransactionMsg> history = new TreeMap<>();

    private PlanetResourceSetMsg resourcesBalance;

    public PlanetAccount(PlanetResourceSetMsg resourcesBalance) {
        this.resourcesBalance = resourcesBalance;
    }

    public boolean validateTransaction(TransactionMsg transactionMsg) {
        return calculateNewBalance(transactionMsg).allGreaterOrEqual(0);
    }

    public void executeTransaction(TransactionMsg transactionMsg) {
        resourcesBalance = calculateNewBalance(transactionMsg);
    }

    public void recordTransaction(long tick, TransactionMsg transactionMsg) {
        history.put(tick, transactionMsg);
    }

    private PlanetResourceSetMsg calculateNewBalance(TransactionMsg transactionMsg) {
        PlanetResourceSetMsg resources = transactionMsg.amount().planetResources();
        return switch (transactionMsg.type()) {
            case INCOME, TRANSFER -> resourcesBalance.add(resources);
            case EXPENSE -> resourcesBalance.subtract(resources);
        };
    }

}
