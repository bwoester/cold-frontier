package de.bwoester.coldfrontier.accounting;

public record ResourceSetMsg(PlanetResourceSetMsg planetResources, long credits) {

    public static ResourceSetMsg createDefault() {
        return new ResourceSetMsg(PlanetResourceSetMsg.createDefault(), 0);
    }

    public ResourceSetMsg multiply(long factor) {
        return new ResourceSetMsg(planetResources.multiply(factor), credits * factor);
    }

    public ResourceSetMsg multiply(float factor) {
        return new ResourceSetMsg(planetResources.multiply(factor), (long) (credits * factor));
    }

    public ResourceSetMsg add(ResourceSetMsg allBuildingsProd) {
        return new ResourceSetMsg(
                planetResources.add(allBuildingsProd.planetResources),
                credits + allBuildingsProd.credits);
    }
}
