package de.bwoester.coldfrontier.accounting;

import java.util.Map;

/**
 * A player's overall finances, tracking credits and planetary accounts.
 */
public record PlayerLedgerMsg(GlobalAccount globalAccount, Map<String, PlanetAccount> planetAccounts) {
}
