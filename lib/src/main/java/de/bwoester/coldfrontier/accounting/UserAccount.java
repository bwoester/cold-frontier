package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.data.Value;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAccount {

    private final Value<Long> balance;

    public long getBalance() {
        return balance.get();
    }

    // TODO move to two-stage commit instead
    // instead of validate, request transaction, lock credits -> already updates available credits
    // instead of execute, commit or rollback transaction -> cleanup
    public boolean validateTransaction(TransactionMsg transactionMsg) {
        return calculateNewBalance(transactionMsg) >= 0;
    }

    public void executeTransaction(TransactionMsg transactionMsg) {
        long newBalance = calculateNewBalance(transactionMsg);
        balance.set(newBalance);
    }

    private long calculateNewBalance(TransactionMsg transactionMsg) {
        long currentBalance = balance.get();
        long diff = transactionMsg.amount().credits();
        return switch (transactionMsg.type()) {
            case INCOME, TRANSFER -> currentBalance + diff;
            case EXPENSE -> currentBalance - diff;
        };
    }
}
