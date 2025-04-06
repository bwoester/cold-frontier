package de.bwoester.coldfrontier.accounting;

public record PlanetResourceSetMsg(long iron, long energy, long population) {

    public boolean allGreaterOrEqual(int value) {
        return iron >= value
                && energy >= value
                && population >= value;
    }

    public PlanetResourceSetMsg add(PlanetResourceSetMsg other) {
        return new PlanetResourceSetMsg(
                iron + other.iron,
                energy + other.energy,
                population + other.population
        );
    }

    public PlanetResourceSetMsg subtract(PlanetResourceSetMsg other) {
        return new PlanetResourceSetMsg(
                iron - other.iron,
                energy - other.energy,
                population - other.population
        );
    }

    public PlanetResourceSetMsg multiply(long factor) {
        return new PlanetResourceSetMsg(
                iron * factor,
                energy * factor,
                population * factor
        );
    }

    public PlanetResourceSetMsg multiply(float factor) {
        return new PlanetResourceSetMsg(
                (long) (iron * factor),
                (long) (energy * factor),
                (long) (population * factor)
        );
    }
}
