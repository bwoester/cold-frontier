---
status: observed-prototype
type: architecture-notes
---
# Current Technical State of the Prototype

This page summarizes **observed prototype code**. The target architecture and technology choices require their own decisions.

- `lib/`: Java 21 library with `TickCoordinator`, `TickComponent`, `Planet`, and services for production, buildings, construction progress, accounting, and inputs.
- `TickCoordinator`: A global tick runs phases across all registered components (input, resources, buildings, movement, conflicts, visibility, persistence, and cleanup).
- `Planet`: Orchestrates specialized services. Production currently sums the rates of the buildings.
- `Value<T>`: State-oriented access with in-memory and NATS KV implementations. A value with history does not automatically constitute a domain event.
- `AccountingService`: Checks and updates player and planet accounts. Multiple writes in the observed flow are not protected by a shared transaction.
- `backend/`: Quarkus prototype with REST and OIDC dependencies.
- `frontend/`: Vue SPA with public and protected pages and game navigation.

## Prototype Game Time

`ColdFrontier.create(Duration)` configures the duration of one tick in game time. The standalone launcher defaults to one second and accepts `-Dcold-frontier.tick-duration=PT0.25S` for a 250 ms tick. A saved simulation keeps its original tick duration.

`advanceTo(Duration)` accepts elapsed game time since tick zero and rejects targets between tick boundaries. Construction uses game seconds and preserves the prototype's existing whole-second rounding of the queue multiplier. A building becomes operational at the first tick boundary at or after its construction duration. Its production starts with the next tick. Production and consumption rates use game hours. Each resource update calculates exactly one configured tick. Accounting posts whole units, and the planet stores the signed fractional remainder for later ticks.

## Known Gaps and Discussion Points

- The rules for bottlenecks and resource depletion are incomplete.
- Verify concurrent commands and the remaining game-design scenarios for construction and production end to end.
- Event sourcing, the time model, database, messaging, RPC, backend framework, and CSS approach have no accepted decisions.
- A framework-independent domain engine is an **architecture goal**. The current `lib` also has NATS-specific dependencies.

## References

[Planetary Economy](../game-design/economy.md) · [Earlier Technical Notes](../notes.md)

Sources: `lib/src/main/java/de/bwoester/coldfrontier/`, `backend/build.gradle.kts`, `frontend/package.json` (repository state when this page was written).
