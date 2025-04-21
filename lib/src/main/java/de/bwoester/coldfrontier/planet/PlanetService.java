package de.bwoester.coldfrontier.planet;

import de.bwoester.coldfrontier.data.Keys;
import de.bwoester.coldfrontier.data.Value;
import de.bwoester.coldfrontier.data.ValueFactory;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Singleton
@RequiredArgsConstructor
public class PlanetService {

    private static final MiningResourcesMsg START_PLANET_INITIAL_RESOURCES = MiningResourcesMsg.createOne().multiply(10000);
    private static final MiningResourcesMsg START_PLANET_REMAINING_RESOURCES = START_PLANET_INITIAL_RESOURCES;
    private static final PlanetProfileMsg START_PLANET_PROFILE = new PlanetProfileMsg();

    private final ValueFactory valueFactory;

    public Value<PlanetMsg> chooseStartPlanet(String playerId) {
        // TODO: select an uninhabited planet, reset it to the startPlanet profile
        String planetId = UUID.randomUUID().toString();
        Value<PlanetMsg> startPlanet = valueFactory.create(PlanetMsg.class, Keys.Planet.planet(planetId));
        startPlanet.set(new PlanetMsg(planetId, START_PLANET_INITIAL_RESOURCES, START_PLANET_REMAINING_RESOURCES, START_PLANET_PROFILE, playerId));
        return startPlanet;
    }

}
