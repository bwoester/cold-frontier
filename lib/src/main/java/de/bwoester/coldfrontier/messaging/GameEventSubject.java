package de.bwoester.coldfrontier.messaging;

public class GameEventSubject {

    public static class Accounting {

        public static String playerAccount(String playerId) {
            return String.format("accounting/players/%s/account", playerId);
        }

        public static String planetAccount(String planetId) {
            return String.format("accounting/planets/%s/account", planetId);
        }
    }

}
