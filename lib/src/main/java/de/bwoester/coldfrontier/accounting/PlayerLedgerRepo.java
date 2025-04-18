package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.messaging.EventLog;
import de.bwoester.coldfrontier.messaging.EventSubject;
import de.bwoester.coldfrontier.messaging.memory.InMemoryEventLog;
import de.bwoester.coldfrontier.user.UserMsg;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PlayerLedgerRepo {

    private static final long INITIAL_PLAYER_ACCOUNT_BALANCE = 0L;
    private static final PlanetResourceSetMsg INITIAL_PLANET_ACCOUNT_BALANCE = PlanetResourceSetMsg.createDefault();

    private final InMemoryEventLog gameEventLog;

    public PlayerLedgerRepo(InMemoryEventLog gameEventLog) {
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

    EventLog<Long> createPlayerAccountEventLog(String playerId) {
        String subject = EventSubject.Accounting.playerAccount(playerId);
        EventLog<Long> eventLog = gameEventLog.viewOfType(Long.class, subject);
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

    EventLog<PlanetResourceSetMsg> createPlanetAccountEventLog(String planetId) {
        String subject = EventSubject.Accounting.planetAccount(planetId);
        EventLog<PlanetResourceSetMsg> eventLog = gameEventLog.viewOfType(PlanetResourceSetMsg.class, subject);
        if (eventLog.isEmpty()) {
            log.warn("No data for {}, initializing with {}.", subject, INITIAL_PLANET_ACCOUNT_BALANCE);
            eventLog.add(INITIAL_PLANET_ACCOUNT_BALANCE);
        }
        return eventLog;
    }
}
