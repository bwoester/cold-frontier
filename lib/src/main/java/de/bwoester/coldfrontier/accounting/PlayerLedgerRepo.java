package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.data.Value;
import de.bwoester.coldfrontier.data.ValueFactory;
import de.bwoester.coldfrontier.data.Keys;
import de.bwoester.coldfrontier.user.UserMsg;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PlayerLedgerRepo {

    private static final long INITIAL_PLAYER_ACCOUNT_BALANCE = 0L;
    private static final PlanetResourceSetMsg INITIAL_PLANET_ACCOUNT_BALANCE = PlanetResourceSetMsg.createDefault();

    private final ValueFactory valueFactory;

    public PlayerLedgerRepo(ValueFactory valueFactory) {
        this.valueFactory = valueFactory;
    }

    public void init(UserMsg user) {
        createPlayerAccount(user.id()).set(0L);
        user.userProfile().planetIds().forEach(planetId -> createPlanetAccount(planetId)
                .set(PlanetResourceSetMsg.createDefault())
        );
    }

    public PlayerLedger get(UserMsg user) {
        Map<String, PlanetAccount> planetAccounts = new HashMap<>();
        for (String planetId : user.userProfile().planetIds()) {
            PlanetAccount planetAccount = new PlanetAccount(createPlanetAccount(planetId));
            planetAccounts.put(planetId, planetAccount);
        }
        GlobalAccount userAccount = new GlobalAccount(createPlayerAccount(user.id()));
        return new PlayerLedger(userAccount, planetAccounts);
    }

    Value<Long> createPlayerAccount(String playerId) {
        String key = Keys.Accounting.playerAccount(playerId);
        Value<Long> value = valueFactory.create(Long.class, key);
        if (!value.isPresent()) {
            // TODO for now, it simplifies unit tests
            //  for later, player ledgers should be initialized when a user registers
            //  when getting the ledger, this should not happen
            //  maybe it should even be considered an error?
            log.warn("No data for {}, initializing with {}.", key, INITIAL_PLAYER_ACCOUNT_BALANCE);
            value.set(INITIAL_PLAYER_ACCOUNT_BALANCE);
        }
        return value;
    }

    Value<PlanetResourceSetMsg> createPlanetAccount(String planetId) {
        String key = Keys.Accounting.planetAccount(planetId);
        Value<PlanetResourceSetMsg> value = valueFactory.create(PlanetResourceSetMsg.class, key);
        if (!value.isPresent()) {
            log.warn("No data for {}, initializing with {}.", key, INITIAL_PLANET_ACCOUNT_BALANCE);
            value.set(INITIAL_PLANET_ACCOUNT_BALANCE);
        }
        return value;
    }
}
