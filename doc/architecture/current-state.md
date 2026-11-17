---
status: observed-prototype
type: architecture-notes
---
<a name="technischer-ist-zustand-keine-soll-architektur"></a>

# Current Technical State of the Prototype

This page summarizes **observed prototype code**. The target architecture and technology choices require their own decisions.

- `lib/`: Java 21 library with `TickCoordinator`, `TickComponent`, `Planet`, and services for production, buildings, construction progress, accounting, and inputs.
- `TickCoordinator`: A global tick runs phases across all registered components (input, resources, buildings, movement, conflicts, visibility, persistence, and cleanup).
- `Planet`: Orchestrates specialized services. Production currently sums the rates of the buildings.
- `Value<T>`: State-oriented access with in-memory and NATS KV implementations. A value with history does not automatically constitute a domain event.
- `AccountingService`: Checks and updates player and planet accounts. Multiple writes in the observed flow are not protected by a shared transaction.
- `backend/`: Quarkus prototype with REST and OIDC dependencies.
- `frontend/`: Vue SPA with public and protected pages and game navigation.

<a name="bekannte-lücken--diskussionspunkte"></a>

## Known Gaps and Discussion Points

- The rules for bottlenecks and resource depletion are incomplete.
- Verify the interaction between construction progress and ticks, as well as concurrent commands, end to end.
- Event sourcing, the time model, database, messaging, RPC, backend framework, and CSS approach have no accepted decisions.
- A framework-independent domain engine is an **architecture goal**. The current `lib` also has NATS-specific dependencies.

<a name="verweise"></a>

## References

[Planetary Economy](../game-design/economy.md) · [Earlier Technical Notes](../notes.md)

Sources: `lib/src/main/java/de/bwoester/coldfrontier/`, `backend/build.gradle.kts`, `frontend/package.json` (repository state when this page was written).
