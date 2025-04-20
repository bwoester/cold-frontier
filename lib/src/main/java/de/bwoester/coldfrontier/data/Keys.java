package de.bwoester.coldfrontier.data;

public class Keys {

    // ------------------------------------------------------------------------

    public static class Accounting {
        /**
         * @param playerId the player id
         * @return "accounting.players.${playerId}.transactions"
         */
        public static String playerTransactions(String playerId) {
            return String.format("accounting.players.%s.transactions", playerId);
        }

        /**
         * @param playerId the player id
         * @return "accounting.players.${playerId}.account"
         */
        public static String playerAccount(String playerId) {
            return String.format("accounting.players.%s.account", playerId);
        }

        /**
         * @param planetId the planet id
         * @return "accounting.planets.${playerId}.account"
         */
        public static String planetAccount(String planetId) {
            return String.format("accounting.planets.%s.account", planetId);
        }
    }

    // ------------------------------------------------------------------------

    public static class Building {
        /**
         * @param planetId the planet id
         * @return "buildings.planets.${planetId}.counters"
         */
        public static String counters(String planetId) {
            return String.format("buildings.planets.%s.counters", planetId);
        }

        /**
         * @param planetId the planet id
         * @return "buildings.planets.${planetId}.queue"
         */
        public static String queue(String planetId) {
            return String.format("buildings.planets.%s.queue", planetId);
        }
    }

    // ------------------------------------------------------------------------

    public static class Progress {
        /**
         * @param planetId the planet id
         * @return "progress.planets.${planetId}.building"
         */
        public static String building(String planetId) {
            return String.format("progress.planets.%s.building", planetId);
        }
    }

    // ------------------------------------------------------------------------

    public static class Input {
        /**
         * @param playerId the player id
         * @return "input.players.${playerId}.new"
         */
        public static String newInput(String playerId) {
            return String.format("input.players.%s.new", playerId);
        }

        /**
         * @param playerId the player id
         * @return "input.players.${playerId}.started"
         */
        public static String startedInput(String playerId) {
            return String.format("input.players.%s.started", playerId);
        }

        /**
         * @param playerId the player id
         * @return "input.players.${playerId}.finished"
         */
        public static String finishedInput(String playerId) {
            return String.format("input.players.%s.finished", playerId);
        }

        /**
         * @param playerId the player id
         * @return "input.players.${playerId}.failed"
         */
        public static String failedInput(String playerId) {
            return String.format("input.players.%s.failed", playerId);
        }
    }

    // ------------------------------------------------------------------------

}
