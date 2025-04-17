package de.bwoester.coldfrontier.planet;

public record MiningResourcesMsg(
        long iron,
        long deepIron,
        long chemicals,
        long ice,
        long deepIce
) {
}
