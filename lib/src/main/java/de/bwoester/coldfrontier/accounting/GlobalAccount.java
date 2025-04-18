package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.messaging.EventLog;

public class GlobalAccount {

    private final EventLog<Long> balance;

    public GlobalAccount(EventLog<Long> balance) {
        this.balance = balance;
    }

    public long getBalance() {
        return balance.getLatest();
    }

    // TODO move to two-stage commit instead
    // instead of validate, request transaction, lock credits -> already updates available credits
    // instead of execute, commit or rollback transaction -> cleanup
    public boolean validateTransaction(TransactionMsg transactionMsg) {
        return calculateNewBalance(transactionMsg) >= 0;
    }

    public void executeTransaction(TransactionMsg transactionMsg) {
        long newBalance = calculateNewBalance(transactionMsg);
        balance.add(newBalance);
    }

    private long calculateNewBalance(TransactionMsg transactionMsg) {
        long currentBalance = balance.getLatest();
        long diff = transactionMsg.amount().credits();
        return switch (transactionMsg.type()) {
            case INCOME, TRANSFER -> currentBalance + diff;
            case EXPENSE -> currentBalance - diff;
        };
    }
}
