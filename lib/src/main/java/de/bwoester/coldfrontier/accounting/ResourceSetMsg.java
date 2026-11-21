package de.bwoester.coldfrontier.accounting;

/**
 * Amounts attributed to one planet for accounting or production.
 * The planet account receives the local resource changes and the owning player account
 * receives the credit change. Keeping both in the transaction lets accounting validate
 * both balances before booking either change.
 */
public record ResourceSetMsg(PlanetResourceSetMsg planetResources, long credits) {

    public static ResourceSetMsg createDefault() {
        return new ResourceSetMsg(PlanetResourceSetMsg.createDefault(), 0);
    }

    public ResourceSetMsg multiply(long factor) {
        return new ResourceSetMsg(planetResources.multiply(factor), credits * factor);
    }

    public ResourceSetMsg multiply(double factor) {
        return new ResourceSetMsg(planetResources.multiply(factor), (long) (credits * factor));
    }

    public ResourceSetMsg add(ResourceSetMsg allBuildingsProd) {
        return new ResourceSetMsg(
                planetResources.add(allBuildingsProd.planetResources),
                credits + allBuildingsProd.credits);
    }
}
