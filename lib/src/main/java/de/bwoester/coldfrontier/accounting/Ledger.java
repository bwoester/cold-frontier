package de.bwoester.coldfrontier.accounting;

import lombok.Getter;

import java.util.Map;

/**
 * A player's overall finances, tracking credits and planetary accounts.
 */
public class Ledger {

    @Getter
    private final UserAccount userAccount;
    private final Map<String, PlanetAccount> planetAccounts;

    public Ledger(UserAccount userAccount, Map<String, PlanetAccount> planetAccounts) {
        this.userAccount = userAccount;
        this.planetAccounts = planetAccounts;
    }

    public PlanetAccount getPlanetAccount(String planetId) {
        return planetAccounts.get(planetId);
    }
}
