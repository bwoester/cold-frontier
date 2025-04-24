package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.data.Keys;
import de.bwoester.coldfrontier.data.Value;
import de.bwoester.coldfrontier.data.ValueRepository;
import de.bwoester.coldfrontier.user.UserMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class PlayerLedgerRepo {

    private static final long INITIAL_PLAYER_ACCOUNT_BALANCE = 0L;
    private static final PlanetResourceSetMsg INITIAL_PLANET_ACCOUNT_BALANCE = PlanetResourceSetMsg.createDefault();

    private final ValueRepository valueRepository;

    public Ledger createLedger(String playerId, String startPlanetId) {
        Value<Long> playerAccount = createPlayerAccount(playerId);
        playerAccount.set(INITIAL_PLAYER_ACCOUNT_BALANCE);
        Value<PlanetResourceSetMsg> planetAccount = createPlanetAccount(startPlanetId);
        planetAccount.set(INITIAL_PLANET_ACCOUNT_BALANCE);
        return new Ledger(
                new UserAccount(playerAccount),
                Map.of(startPlanetId, new PlanetAccount(planetAccount))
        );
    }

    public Ledger get(UserMsg user) {
        Map<String, PlanetAccount> planetAccounts = new HashMap<>();
        for (String planetId : user.userProfile().planetIds()) {
            PlanetAccount planetAccount = new PlanetAccount(createPlanetAccount(planetId));
            planetAccounts.put(planetId, planetAccount);
        }
        UserAccount userAccount = new UserAccount(createPlayerAccount(user.id()));
        return new Ledger(userAccount, planetAccounts);
    }

    Value<Long> createPlayerAccount(String playerId) {
        String key = Keys.Accounting.playerAccount(playerId);
        Value<Long> value = valueRepository.get(Long.class, key);
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
        Value<PlanetResourceSetMsg> value = valueRepository.get(PlanetResourceSetMsg.class, key);
        if (!value.isPresent()) {
            log.warn("No data for {}, initializing with {}.", key, INITIAL_PLANET_ACCOUNT_BALANCE);
            value.set(INITIAL_PLANET_ACCOUNT_BALANCE);
        }
        return value;
    }
}
