package de.bwoester.coldfrontier.messaging;

public class GameEventSubject {

    public static class Accounting {

        /**
         * @param playerId the player id
         * @return "accounting/players/${playerId}/transactions"
         */
        public static String playerTransactions(String playerId) {
            return String.format("accounting/players/%s/transactions", playerId);
        }

        /**
         * @param playerId the player id
         * @return "accounting/players/${playerId}/account"
         */
        public static String playerAccount(String playerId) {
            return String.format("accounting/players/%s/account", playerId);
        }

        /**
         * @param planetId the planet id
         * @return "accounting/planets/${playerId}/account"
         */
        public static String planetAccount(String planetId) {
            return String.format("accounting/planets/%s/account", planetId);
        }
    }

}
