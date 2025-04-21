package de.bwoester.coldfrontier.planet;

public record MiningResourcesMsg(
        long iron,
        long deepIron,
        long chemicals,
        long ice,
        long deepIce
) {

    public static MiningResourcesMsg createOne() {
        return new MiningResourcesMsg(1, 1, 1, 1, 1);
    }

    public MiningResourcesMsg multiply(long multiplier) {
        return new MiningResourcesMsg(multiplier * iron, multiplier * deepIron, multiplier * chemicals, multiplier * ice, multiplier * deepIce);
    }

}
