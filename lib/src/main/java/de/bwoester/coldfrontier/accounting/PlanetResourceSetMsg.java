package de.bwoester.coldfrontier.accounting;

public record PlanetResourceSetMsg(long iron, long energy, long population) {

    static PlanetResourceSetMsg createDefault() {
        return new PlanetResourceSetMsg(0, 0, 0);
    }
    static PlanetResourceSetMsg createOne() {
        return new PlanetResourceSetMsg(1, 1, 1);
    }

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

    public PlanetResourceSetMsg multiply(double factor) {
        return new PlanetResourceSetMsg(
                (long) (iron * factor),
                (long) (energy * factor),
                (long) (population * factor)
        );
    }
}
