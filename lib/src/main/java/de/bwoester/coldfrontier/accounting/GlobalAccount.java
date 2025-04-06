package de.bwoester.coldfrontier.accounting;

public class GlobalAccount {

    private long creditsBalance;

    public GlobalAccount(long creditsBalance) {
        this.creditsBalance = creditsBalance;
    }

    // TODO move to two-stage commit instead
    // instead of validate, request transaction, lock credits -> already updates available credits
    // instead of execute, commit or rollback transaction -> cleanup
    public boolean validateTransaction(TransactionMsg transactionMsg) {
        return calculateNewBalance(transactionMsg) > 0;
    }

    public void executeTransaction(TransactionMsg transactionMsg) {
        creditsBalance = calculateNewBalance(transactionMsg);
    }

    private long calculateNewBalance(TransactionMsg transactionMsg) {
        long credits = transactionMsg.amount().credits();
        return switch (transactionMsg.type()) {
            case INCOME, TRANSFER -> creditsBalance + credits;
            case EXPENSE -> creditsBalance - credits;
        };
    }
}
