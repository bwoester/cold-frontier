package de.bwoester.coldfrontier.planet;

public record PlanetMsg(String id, MiningResourceMsg initialResources, MiningResourceMsg remainingResources, PlanetProfileMsg planetProfile) {
}
