---
status: proposed
type: architecture-notes
---
# Simulation Acceptance Test Boundary

## Scope and decision status

The agreed direction is to keep Gherkin steps independent of a particular engine
through a small test-side interface and one adapter per implementation. Steps and
adapters must not implement missing game behavior. This note proposes the concrete
shape using two existing acceptance cases; it does not select a production
architecture or introduce a test framework.

Follow the [Gherkin conventions](../gherkin-conventions.md) and the
[resource simulation contract](resource-simulation-contract.md). The feature files
remain the source of expected results. The examples below explain their mapping,
not a second executable specification.

## Minimal boundary for the first two cases

| Operation | Input | Result and responsibility |
| --- | --- | --- |
| Prepare and initialize | Explicit scenario configuration and initial game time | A fresh engine session. Preparation supplies pre-start stocks and facility parameters; engine initialization performs any automatic production start and input withdrawal. |
| Advance to | Absolute target game time | The engine advances the same session and reports the reached time and domain events emitted by this operation. A checkpoint list means successive calls, not repeated initialization. |
| Observe | Current session | A read-only domain view of stocks, shared production progress, production status and cumulative input consumption needed by the selected assertions. Reading must not advance time or emit events. |

These are semantic operations, not a prescribed Java API or serialization schema.
The first implementation can use ordinary test-side Java interfaces and records.
No network service, adapter discovery mechanism, capability framework or
cross-language protocol is needed. Gherkin remains reusable across languages;
bindings and adapters do not.

An observation describes available whole resources separately from unfinished
production, identified by colony and resource for extraction, or colony and recipe
for processing. It must be projected from actual engine state or actual engine
history. Progress need not be stored as a dedicated field, but the adapter must not
recompute expected production from elapsed time and the scenario rate.

The adapter may translate identifiers, units and existing event representations,
wire dependencies and populate the declared fixture. It may not invent a time-to-tick
mapping, supply a new production algorithm, round away discrepancies, synthesize
audit events or remove duplicates. A time mapping must come from the engine's
explicit time semantics. An unrepresentable time is an unsupported case, not a
request to silently round.

Initialization and advancement return their own event batches. The test can retain
these batches to assert a total count or inspect only the latest batch. Collecting
actual emissions is test plumbing; inferring an event from a balance is not.

Preparation and initialization are deliberately distinguished: inserting the
pre-start ice stock is fixture work; withdrawing the first recipe is engine work.
Use a fresh session for every scenario and every Examples row, and release any
subscriptions when it ends.

## Case 1: Fractional extraction across checkpoints

Use the existing Production progress outline in
[production-progress.feature](../game-design/scenarios/production-progress.feature),
starting with the rows "Half a unit remains unavailable but is retained" and
"Splitting a half-unit interval preserves partial progress".

The Background supplies an operational mine producing 3 iron per hour without
inputs, zero initial iron and progress, capacity 100, satisfied operating conditions
and no other stock-changing activity.

| Feature instruction | Boundary use | Assertion |
| --- | --- | --- |
| Start at minute 0 with the completed mine | Prepare and initialize a fresh session | Initialization does not substitute a different rate or require energy. |
| Advance through "10" | Advance to minute 10 | Reached time is minute 10; observe 0 available iron and 0.5 shared iron progress. |
| Advance through "5, 10" in the other row | Advance to minute 5, then minute 10 in a fresh session | At the stated final checkpoint observe the same 0 available iron and 0.5 progress. |

The second row does not silently add an assertion at minute 5. Any additional
checkpoint assertion must appear in the feature. Compare the stated exact decimal
quantities without a hidden tolerance. Numeric storage remains an engine choice.

The existing sequential repeated-time scenario is a subsequent check of state
stability and empty event batches. Do not claim that implementing these first two
rows covers construction completion, pooled mines or the whole feature.

## Case 2: Water production and the effective depletion time

Use the existing outline "Water production stops when the next unit cannot start
for lack of ice" in
[water-production.feature](../game-design/scenarios/water-production.feature).

Its initial pre-start stock is 6 ice and 0 water, rate 10 water per hour, and recipe
1 ice plus 2 energy per water. The Background explicitly supplies 12 energy and water capacity 100.
These are test parameters, not balancing decisions; they are written in the
feature rather than hidden in the adapter.

| Feature instruction | Boundary use | Assertion |
| --- | --- | --- |
| Initialize at minute 0 | Engine initializes the prepared session | Engine owns the first recipe withdrawal; setup must not debit it in advance. |
| Advance through "60" | Advance once to minute 60 | At minute 60: 6 water, 0 ice, 12 energy consumed and production inactive. |
| Advance through "30, 33, 36, 60" in the other row | Four successive advances in a fresh session | The same stated final quantities and production status. |
| Exactly one ice depletion event at minute 36 | Inspect actual domain events accumulated for this session | Exactly one matching event with effective game time 36, even when observed during the advance to 60. |

The feature's sequential scenario additionally specifies which advance emits the
event: none to minute 30 or 33, one to minute 36, none to minute 60. It also checks
the half-finished sixth unit at minute 33. Bind these assertions explicitly when
extending the initial scope.

The depletion event marks inability to start the next unit at minute 36, not the
pool reaching zero at minute 30. Follow the existing contract; do not infer this
event from an empty pool.

## Audit observation

An adapter maps an actually emitted domain event to its semantic type, affected
colony/production and effective game time. A test may identify the only colony in
the fixture without prescribing a production UUID. Technical timestamps and IDs
are outside these assertions. Event ordering is asserted only where the scenario
requires it; unrelated events must not be mistaken for depletion events.

"Exactly one ice depletion event" filters by semantic type and subject, then checks
count and time. "The advance emits no events" checks the entire domain-event batch
for that operation. Neither assertion refers to storage writes or transport
messages. Preserve duplicate emissions so a duplicate fails the count assertion.

A synchronous result or a domain-event collector can supply these observations.
The adapter must await completion of the engine operation before inspecting its
events. Persistent audit storage and message delivery reliability need separate
integration tests; these cases only establish domain emission behavior.

## Current prototype: checked gaps

Inspected main commit
[3a70eb7](https://github.com/bwoester/cold-frontier/commit/3a70eb7224f56551c36626c9e1e56146e36478f4).
This is source inspection, not a run of these acceptance cases.

| Area | Observed code | Consequence |
| --- | --- | --- |
| Fixture configuration | [ProductionService](../../lib/src/main/java/de/bwoester/coldfrontier/production/ProductionService.java) receives a BuildingDataProvider. [Existing steps](../../lib/src/test/java/de/bwoester/coldfrontier/production/ProductionTickSteps.java) wire real services with in-memory values. | Reuse that wiring approach and supply declared test building data. Do not use the static mine's energy cost for the no-input extraction fixture. |
| Game time | [TickCoordinator](../../lib/src/main/java/de/bwoester/coldfrontier/TickCoordinator.java) advances integer ticks. Planet resource updates do not receive an elapsed duration. | No inspected API defines the required minutes-to-ticks semantics. This needs an engine decision/change, not an arbitrary adapter conversion. |
| Fractional progress | [PlanetResourceSetMsg](../../lib/src/main/java/de/bwoester/coldfrontier/accounting/PlanetResourceSetMsg.java) holds long quantities and casts double products to long. ProductionService sums fixed building amounts. | This path has no retained fractional extraction state. A getter alone cannot make the half-unit cases pass. |
| Processing | PlanetResourceSetMsg contains iron, energy and population, but no ice or water. ProductionService leaves source-material shortages as a TODO. | Water resources, recipe withdrawal, shared processing progress and shortage transitions require domain implementation. |
| Events | [EventFactory](../../lib/src/main/java/de/bwoester/coldfrontier/data/EventFactory.java) wraps payloads with the current tick. [InMemoryValue](../../lib/src/main/java/de/bwoester/coldfrontier/data/memory/InMemoryValue.java) records writes this way; its watch method is unimplemented. | These storage events do not establish a depletion event or effective game time. Add actual domain emission before connecting a collector; do not treat all writes as audit events. |
| State observation | Existing steps read PlanetAccount balances. | Whole iron is observable already. Progress, processing status and recipe consumption need actual engine state/history plus a read-only view. |

## Implementation sequence and honest reporting

1. Use this mapping to make the smallest time/progress changes needed for extraction,
   or explicitly choose a replacement for that production path before coding it.
2. Add the narrow test interface, prototype adapter and bindings for the selected
   extraction rows. Keep existing tick characterization tests separate.
3. Add processing and domain-event emission in the engine before binding the water
   audit case.
4. Expand to the remaining existing cases without changing their expected outcomes
   to accommodate the engine.

Do not create placeholder adapters that appear to support water processing.
Distinguish failed assertions, missing bindings and unsupported engine behavior in
the reported results. Excluded or pending scenarios are not accepted behavior.
When a selected acceptance case is enabled in CI, its failure must fail that
acceptance run; do not catch failures or convert them into successful tests.

This note supplies a concrete boundary and gap assessment. It does not add
executable bindings, demonstrate passing acceptance tests or select an engine
replacement. Construction, player commands and restart/persistence operations can
extend the interface only when their scenarios are taken up.
