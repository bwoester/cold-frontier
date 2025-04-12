package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.messaging.GameEventLog;
import de.bwoester.coldfrontier.messaging.GameEventSubject;
import de.bwoester.coldfrontier.messaging.InMemoryGameEventLog;
import de.bwoester.coldfrontier.user.UserMsg;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PlayerLedgerRepo {

    private static final long INITIAL_PLAYER_ACCOUNT_BALANCE = 0L;
    private static final PlanetResourceSetMsg INITIAL_PLANET_ACCOUNT_BALANCE = PlanetResourceSetMsg.createDefault();

    private final InMemoryGameEventLog gameEventLog;

    public PlayerLedgerRepo(InMemoryGameEventLog gameEventLog) {
        this.gameEventLog = gameEventLog;
    }

    public void init(UserMsg user) {
        createPlayerAccountEventLog(user.id()).add(0L);
        user.userProfile().planetIds().forEach(planetId -> createPlanetAccountEventLog(planetId)
                .add(PlanetResourceSetMsg.createDefault())
        );
    }

    public PlayerLedger get(UserMsg user) {
        Map<String, PlanetAccount> planetAccounts = new HashMap<>();
        for (String planetId : user.userProfile().planetIds()) {
            PlanetAccount planetAccount = new PlanetAccount(createPlanetAccountEventLog(planetId));
            planetAccounts.put(planetId, planetAccount);
        }
        GlobalAccount userAccount = new GlobalAccount(createPlayerAccountEventLog(user.id()));
        return new PlayerLedger(userAccount, planetAccounts);
    }

    GameEventLog<Long> createPlayerAccountEventLog(String playerId) {
        String subject = GameEventSubject.Accounting.playerAccount(playerId);
        GameEventLog<Long> eventLog = gameEventLog.viewOfType(Long.class, subject);
        if (eventLog.isEmpty()) {
            // TODO for now, it simplifies unit tests
            //  for later, player ledgers should be initialized when a user registers
            //  when getting the ledger, this should not happen
            //  maybe it should even be considered an error?
            log.warn("No data for {}, initializing with {}.", subject, INITIAL_PLAYER_ACCOUNT_BALANCE);
            eventLog.add(INITIAL_PLAYER_ACCOUNT_BALANCE);
        }
        return eventLog;
    }

    GameEventLog<PlanetResourceSetMsg> createPlanetAccountEventLog(String planetId) {
        String subject = GameEventSubject.Accounting.planetAccount(planetId);
        GameEventLog<PlanetResourceSetMsg> eventLog = gameEventLog.viewOfType(PlanetResourceSetMsg.class, subject);
        if (eventLog.isEmpty()) {
            log.warn("No data for {}, initializing with {}.", subject, INITIAL_PLANET_ACCOUNT_BALANCE);
            eventLog.add(INITIAL_PLANET_ACCOUNT_BALANCE);
        }
        return eventLog;
    }
}
