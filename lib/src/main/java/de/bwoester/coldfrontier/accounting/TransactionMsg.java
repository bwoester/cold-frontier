package de.bwoester.coldfrontier.accounting;

public record TransactionMsg(String description, TransactionType type, ResourceSetMsg amount) {
    public enum TransactionType {
        INCOME, EXPENSE, TRANSFER
    }
}
