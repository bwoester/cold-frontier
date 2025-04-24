package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.TestValues;
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

@Slf4j
class LedgerRepoTest {

    TestValues testValues;
    UserMsg user;

    @BeforeEach
    void setUp() {
        testValues = new TestValues();
        user = new UserMsg(
                "player-1",
                Collections.emptyList(),
                new UserSettingsMsg("de"),
                new UserProfileMsg(List.of("planet-1"))
        );
    }

    @AfterEach
    void tearDown() {
        log.info("event log:\n{}", testValues.util.prettyPrint());
    }

    @Test
    void get() {
        PlayerLedgerRepo playerLedgerRepo = new PlayerLedgerRepo(testValues.util);

        Ledger ledger = playerLedgerRepo.get(user);
        Assertions.assertNotNull(ledger);

        UserAccount userAccount = ledger.getUserAccount();
        Assertions.assertNotNull(userAccount);
        Assertions.assertEquals(0L, userAccount.getBalance());

        PlanetAccount planetAccount = ledger.getPlanetAccount("planet-1");
        Assertions.assertNotNull(planetAccount);
        Assertions.assertEquals(PlanetResourceSetMsg.createDefault(), planetAccount.getBalance());
    }

}