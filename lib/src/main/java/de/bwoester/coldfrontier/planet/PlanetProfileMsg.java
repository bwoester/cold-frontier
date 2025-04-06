package de.bwoester.coldfrontier.planet;

// For planet traits (might be altered through in-game choices?):
// - planet type (rocky, gas giant, asteroid)
// - moons
// - rings
// - multipliers, calculated from type and traits (construction time/costs, ...)
//  -> function of PlanetMsg
// - some traits are pre-requisites for buildings on the planet (lunar defense, asteroid habitat, ...)
public record PlanetProfileMsg() {
}
