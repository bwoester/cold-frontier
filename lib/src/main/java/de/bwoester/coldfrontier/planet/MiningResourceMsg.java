package de.bwoester.coldfrontier.planet;

public record MiningResourceMsg(
        long iron,
        long deepIron,
        long chemicals,
        long ice,
        long deepIce
) {
}
