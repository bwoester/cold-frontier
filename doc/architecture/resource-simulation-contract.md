---
status: proposed
type: architecture-notes
---
# Domain Contract for Resource Simulation

## Stage 1

The resource simulation advances the economy of a prepared colony with predefined facilities. It receives the economically relevant state at a known game time and a target game time. The game rules are available to the simulation. It computes production, consumption, and the resulting transitions from the evolving state.

The result contains the updated state and the game time reached, along with the domain events that occurred and their effective game times. The resulting state is the starting point for the next interval. In the absence of external interventions, advancing directly or in segments yields the same final domain state and the same events over the full interval.

**Independence from intermediate timestamps.** Given the same initial state and no external interventions, additional queries or advancement intervals must not create or destroy resources. At the same target game time, stocks, production, consumption, and domain events with their effective game times match. Dividing advancement into segments must preserve fractional quantities. Produced resources become available in whole units. Fractions remain in the updated state as production progress. Numeric representation, including a possible integer scale, remains open.

For the [water plant acceptance case](../game-design/scenarios/water-production.feature), the depletion event belongs to the interval that reaches minute 36. A subsequent interval starting at minute 36 does not report it again. Facilities advance together in the context of the colony. The caller does not specify rates or the order of individual facilities.

### Construction Completion, Fractions, and Availability

The [confirmed rules for construction completion and production progress](../game-design/economy.md#construction-completion-and-production-progress) apply regardless of the engine. When advancement crosses construction completion, it accounts for the existing facilities up to completion and the new building after that, subject to its operating conditions. Completion itself creates no resource unit. Processing in phases must introduce neither retroactive production nor an additional delay.

In addition to available whole units, the result must preserve unfinished production progress or allow it to be reconstructed without loss. Two intervals that each produce half a unit of progress yield one available unit together. Intermediate evaluations must not shift that time. This leaves numeric format and storage layout open. Showing rates per hour leaves internal time resolution and advancement intervals open.

### Pooled Extraction

Extraction follows the [pooling rules per colony and resource](../game-design/economy.md#pooled-extraction-per-colony-and-resource). Derive available whole units from pooled extraction quantities before rounding any building's output. The shared remainder persists through construction completion. The internal aggregation and storage structures remain open. Processing also uses shared capacity per colony and recipe, as described below.

### Inputs for a Unit in Progress

Water processing follows the [pooling rules per colony and recipe](../game-design/economy.md#pooled-processing-per-colony-and-recipe). It starts a unit only when effective processing capacity and at least 1 ice and 2 energy in the resource pool are available. On start, both inputs move from the pool into the shared processing operation exactly once. The effective capacities of contributing water plants are pooled. A single set of ingredients serves the pooled operation. The unit remains in state as production in progress until completion. On completion, 1 whole unit of water moves into the pool.

If any input is missing, the next unit does not start and all inputs remain in the pool. As soon as pooled processing has all required quantities and effective capacity, it withdraws the ingredients together, assigns them to the unit in progress, and starts automatically. The unit becomes available once the effective pooled capacity has done all the work required to produce it. The [restart test case](../game-design/scenarios/water-production-restart.feature) demonstrates this with an energy shortage.

- **Minute 0:** Before production first starts, the prepared pool contains 6 ice and 0 water. On initialization, the plant withdraws 1 ice and 2 energy and starts the first unit. The initialized state at minute 0 contains 5 ice and 0 water in the pool and the first unit in progress. Advancing to the game time already reached returns that state without new events. The first unit becomes available at minute 6.
- **Minute 30:** The fifth unit of water becomes available. The plant withdraws the last unit of ice and 2 energy for the sixth unit. The pool has no ice left.
- **Minute 33:** The pool contains 5 water and 0 ice. The sixth unit is half finished.
- **Minute 36:** The sixth unit of water becomes available. The plant cannot start another unit because there is no ice. The depletion event occurs at this time.

In the acceptance case, the depletion event marks the transition from active production to insufficient ice for the next unit. The pool reaches zero when the last ice is withdrawn at minute 30. What happens to inputs already withdrawn if production is canceled remains open.

## Responsibilities When Adding Other Game Mechanics

| Role | Responsibility | Boundary |
| --- | --- | --- |
| Resource simulation | Compute stocks, production, consumption, and the resulting economic transitions. | Has no knowledge of transport plans, combat rules, or player commands. Advances only the state it owns. |
| Game mechanic that owns the action | Plan or evaluate its own action and determine its effect. Examples include transport, combat, construction, and research. | A mechanic does not directly change stocks or other state it does not own. For example, completing unloading hands off cargo for acceptance according to the rules, and the outcome depends on available storage capacity. |
| Game flow coordination | Account for the next relevant change, advance affected states to its effective game time, apply actions and consequences in the order specified by the rules, then continue. | Production and combat rules belong to their respective mechanics. The existing Tick Coordinator could be a starting point for this role. Its phases do not yet establish priority rules for every case. |

A time can be known in advance or follow from the progress of a mechanic. For example, the expected completion time of a construction order can shift because of an economic bottleneck. A spontaneous player command receives an effective game time under the rules for game flow coordination. Handling late and simultaneous actions remains part of the [open question on command ordering](command-ordering.md).

## Phase Order of the Tick Prototype

The [`TickCoordinator`](../../lib/src/main/java/de/bwoester/coldfrontier/TickCoordinator.java) first increments the tick counter and then runs each phase for all registered components. The table shows the existing sequence and the proposed interpretation of each phase in terms of game rules.

| No. | Prototype phase | Domain rationale and open questions |
| ---: | --- | --- |
| 1 | Inputs (`handleInput`) | A defined time for player commands determines the stocks they see and the consequences the economy takes into account afterward. Running inputs before the resource phase is a proposal. |
| 2 | Resources (`handleResourceUpdates`) | Production and consumption need an order relative to commands and other stock changes. Completing one water unit and attempting to start the next can remain part of the same domain operation. |
| 3 | Completions (`handleFinishedUnits`) | The prototype takes completed buildings into account after the resource phase, so they contribute to production in the following tick at the earliest. The rule is defined by the [timing boundary at construction completion](../game-design/economy.md#construction-completion-and-production-progress). Whether the implementation satisfies it also depends on the interval assigned to the resource phase and the time assigned to completion. |
| 4 | Queues (`handleQueuedUnits`) | After completion, the freed construction slot can go directly to the next order. That local order suffices within the same construction queue as long as no other mechanic needs to intervene between completion and the start of the next order. |
| 5 | Movement (`handleMovement`) | A separate phase makes sense if conflicts should use positions reached after movement. The prototype's phase order alone leaves arrival and interception rules open. |
| 6 | Conflicts (`handleConflicts`) | Resolution can build on movement results and change stocks or facilities. Priority relative to other changes at the same game time needs concrete test cases. |
| 7 | Visibility (`handleVisibilityUpdates`) | A domain phase makes sense if visibility determines which actions are allowed at the same game time. A display-only view can be derived from game state. |
| 8 | Persistence (`handlePersistence`) | Technical step. Saving at a given point does not establish a gameplay priority rule by itself. |
| 9 | Cleanup (`handleCleanup`) | Technical cleanup needs no domain phase. An expiry with a gameplay effect, such as the end of a bonus, needs a rule defining when it takes effect. |

### When a Separate Phase Makes Sense

A domain phase defines **what state subsequent game rules see at the same game time**. A separate phase makes sense when ordering groups of actions changes an observable result and can be explained as a shared rule. A suitable test compares orderings that produce different stocks, permitted actions, or domain events.

Service responsibilities alone do not establish this order. A resource pool can receive cargo, pay construction costs, and supply inputs for production at the same game time. The same colony remains responsible for its stock rules. Processing all planets before all fleets would implicitly let service order decide whether newly unloaded ice is available for construction and production at that time. A local sequence within a construction order or water plant needs its own phase only if another mechanic must see a relevant state in between. Independent actions at the same time need no global ordering solely because they are simultaneous.

### Unloading Completion and Crediting Cargo

Unloading starts on arrival. Approach, docking, and unloading share one time window. The [initial rule](../game-design/economy.md#s13--unloading-with-limited-storage-capacity) uses 15 minutes. Five minutes was a value discussed earlier. The sequence applies regardless of duration.

Game flow coordination processes the **due command to complete unloading** at the start of its effective game time as system input before player commands and resource updates. If unloading is still in progress, it checks the recipient, authorization to unload, and free storage capacity. On successful completion, cargo leaves the ship. The accepted quantity is available immediately in the destination pool, and any overflow is lost. The **Unloading Completed** event records the outcome. Crediting requires no additional input.

A cancellation effective before completion stops unloading. All cargo stays with the fleet. The originally scheduled completion does not credit any cargo. The effects of combat, ship destruction, and possible cargo losses during unloading remain open. Once completion has been processed successfully, a subsequent action against the transport cannot prevent the transfer. This includes an attack in a later phase at the same game time.

If ice is credited at minute 36 and at least 1 ice and 2 energy remain after player commands, the water plant starts the seventh unit as soon as the sixth finishes. No ice depletion event occurs. The [unloading completion Gherkin scenarios](../game-design/scenarios/delivery-completion.feature) test the actions taking effect together. Ordering within a group remains part of the [open question on command ordering](command-ordering.md).

## Events from the Economy

The result can report a transition handled within the economy. In the water plant scenario, the economy stops the plant when ice runs out. The audit event records the effective game time.

If a transition can affect another mechanic at an earlier time, game flow coordination must account for it at that point before finalizing dependent states at later times. This also applies to feedback into the economy. A log evaluated only at the end is insufficient for this purpose.

**Example for discussion.** An economic bottleneck takes effect at minute 36 and, under the assumed game rule, slows down construction in progress. Construction advances at its previous rate up to that point. From minute 36 onward, the bottleneck affects the remaining work. A completion previously expected at minute 45 can move, which can in turn change when the new building starts producing. The existing water plant acceptance case has no construction of this kind.

**Proposal for a later extension.** A run can stop at a transition relevant to coordination and return the state at the game time reached. Game flow coordination processes the consequence at that time and resumes the run. The meaning of the reached game time and the events must remain unambiguous. The first concrete case involving feedback will help determine whether an early return or another coordination mechanism fits.

Ordering simultaneous effects, handling chains of consequences at the same game time, and deciding which transitions require coordination all remain open. Stage 1 requires neither a general event interface nor a decision on the technical time model.

## Mathematics of Ice Extraction

[Continuous ice extraction](../game-design/continuous-ice-extraction.md) shows how to calculate a proportional extraction model directly within a valid interval and why direct and segmented advancement are mathematically equivalent. It starts with the deposit and extracted quantity. Stocks, available whole units, bottlenecks, and feedback into other mechanics still need to be evaluated together under this contract. Advancement to the next external event must still account for any intervening internal transitions.
