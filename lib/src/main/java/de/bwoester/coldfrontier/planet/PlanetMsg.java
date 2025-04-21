package de.bwoester.coldfrontier.planet;

public record PlanetMsg(String id, MiningResourcesMsg initialResources, MiningResourcesMsg remainingResources, PlanetProfileMsg planetProfile, String playerId) {
}
