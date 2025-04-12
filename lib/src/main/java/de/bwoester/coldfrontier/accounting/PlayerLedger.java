package de.bwoester.coldfrontier.accounting;

import lombok.Getter;

import java.util.Map;

/**
 * A player's overall finances, tracking credits and planetary accounts.
 */
public class PlayerLedger {

    @Getter
    private final GlobalAccount globalAccount;
    private final Map<String, PlanetAccount> planetAccounts;

    public PlayerLedger(GlobalAccount globalAccount, Map<String, PlanetAccount> planetAccounts) {
        this.globalAccount = globalAccount;
        this.planetAccounts = planetAccounts;
    }

    public PlanetAccount getPlanetAccount(String planetId) {
        return planetAccounts.get(planetId);
    }
}
