package de.bwoester.coldfrontier.user;

import de.bwoester.coldfrontier.accounting.PlayerLedger;
import de.bwoester.coldfrontier.accounting.PlayerLedgerRepo;
import de.bwoester.coldfrontier.data.Keys;
import de.bwoester.coldfrontier.data.Value;
import de.bwoester.coldfrontier.data.ValueFactory;
import de.bwoester.coldfrontier.planet.PlanetMsg;
import de.bwoester.coldfrontier.planet.PlanetService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class UserService {

    private final ValueFactory valueFactory;
    private final PlanetService planetService;
    private final PlayerLedgerRepo playerLedgerRepo;

    public Value<UserMsg> registerNewUser(String externalId) {
        String userId = UUID.randomUUID().toString();

        // each new player needs a start planet
        Value<PlanetMsg> startPlanet = planetService.chooseStartPlanet(userId);
        String startPlanetId = startPlanet.get().id();

        Value<UserMsg> user = valueFactory.create(UserMsg.class, Keys.User.user(userId));
        user.set(new UserMsg(
                userId,
                List.of(externalId),
                new UserSettingsMsg("de"),
                new UserProfileMsg(List.of(startPlanetId))
        ));

        // each new player requires an accounting ledger
        PlayerLedger ledger = playerLedgerRepo.createLedger(userId, startPlanetId);

        return user;
    }

}
