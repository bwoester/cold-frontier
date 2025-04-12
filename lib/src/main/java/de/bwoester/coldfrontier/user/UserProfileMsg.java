package de.bwoester.coldfrontier.user;

import java.util.Collection;

// For in-game choices/ traits:
// - Genetics
// - multipliers achieved through research (construction time/costs, construction queue, ...)
public record UserProfileMsg(Collection<String> planetIds) {
}
