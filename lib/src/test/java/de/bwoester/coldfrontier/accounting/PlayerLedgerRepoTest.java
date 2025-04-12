package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.EventLogStub;
import de.bwoester.coldfrontier.user.UserMsg;
import de.bwoester.coldfrontier.user.UserProfileMsg;
import de.bwoester.coldfrontier.user.UserSettingsMsg;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
class PlayerLedgerRepoTest {

    EventLogStub eventLogStub;
    UserMsg user;

    @BeforeEach
    void setUp() {
        eventLogStub = new EventLogStub();
        user = new UserMsg(
                "player-1",
                Collections.emptyList(),
                new UserSettingsMsg("de"),
                new UserProfileMsg(List.of("planet-1"))
        );
    }

    @AfterEach
    void tearDown() {
        log.info("event log:\n{}", eventLogStub.inMemoryGameEventLog.prettyPrint());
    }

    @Test
    void get() {
        PlayerLedgerRepo playerLedgerRepo = new PlayerLedgerRepo(eventLogStub.inMemoryGameEventLog);

        PlayerLedgerMsg playerLedgerMsg = playerLedgerRepo.get(user);
        Assertions.assertNotNull(playerLedgerMsg);

        GlobalAccount globalAccount = playerLedgerMsg.globalAccount();
        Assertions.assertNotNull(globalAccount);
        Assertions.assertEquals(0L, globalAccount.getBalance());

        Map<String, PlanetAccount> planetAccountMap = playerLedgerMsg.planetAccounts();
        Assertions.assertNotNull(planetAccountMap);
        Assertions.assertEquals(1, planetAccountMap.size());
        Assertions.assertTrue(planetAccountMap.containsKey("planet-1"));

        PlanetAccount planetAccount = planetAccountMap.get("planet-1");
        Assertions.assertNotNull(planetAccount);
        Assertions.assertEquals(PlanetResourceSetMsg.createDefault(), planetAccount.getBalance());
    }

}