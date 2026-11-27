---
status: proposed
type: architecture-notes
---
# Strategy Pattern for Simulation Decisions

Use the Strategy pattern where several valid ways of making the same gameplay decision already exist. This gives each use case room to start with a simple policy and improve it once scenarios show what players need. For example, the first upkeep policy could use a stable order among equally prioritized buildings. Later, it could prefer buildings where shutdown and recovery would cost the player more.

## Where the strategy fits

The domain rules define when a decision is due, which constraints every outcome must satisfy, and what happens after the decision. The strategy receives the relevant state and constraints and selects a valid outcome. A strategy can rank individual candidates when they are independent or choose a feasible allocation when the candidates share resources. The domain validates and applies the outcome at the effective game time and records the decision and its reason where players or tests need to see it.

For competing upkeep, every strategy must respect drinking-water priority and all-or-nothing payment per building. Once allocation is settled, the common shutdown rule applies to buildings that could not pay. The [building-specific effects](resource-simulation-contract.md#building-specific-upkeep-effects) govern what happens to each affected building after that decision.

Give each use case an extension point shaped by its own inputs and result. A staffing policy and an upkeep policy can follow the same pattern with separate contracts. Introduce the extension point when there is a concrete choice to vary.

## Starting with a simple strategy

A stable ordering by persistent IDs or a reproducible draw can serve as a first strategy where the game rules leave candidates equally eligible. ID ordering can repeatedly favor the same buildings. A draw can distribute that advantage across decisions, but the same decision must produce the same outcome on replay. Derive the draw from stable simulation inputs, such as the identity of the decision and the effective game time, with candidates in a stable order.

Given the same initial state, rules, selected strategy, and inputs, evaluate the same decision the same way regardless of candidate iteration order or whether the simulation advances directly or in segments. Compare later strategies on the same scenarios. Keep legality and timing expectations shared. Assert the chosen outcome and explanation for each strategy separately where they differ.

## Candidate decisions

| Decision | Strategies to compare | Tracking |
| --- | --- | --- |
| Assign available workers to buildings | Longest-waiting vacancy or preserving the colony's fully staffed production mix | [#47](https://github.com/bwoester/cold-frontier/issues/47) and [#48](https://github.com/bwoester/cold-frontier/issues/48) |
| Choose which competing upkeep demands to fund | Stable ordering, reproducible draw among equally prioritized demands, or preference based on shutdown and recovery costs | [#47](https://github.com/bwoester/cold-frontier/issues/47) |
| Allocate or reserve inputs for a production restart | Accumulate unused proportional shares, reserve inputs for the next feasible batch, or plan against future stocks and completion times | [#44](https://github.com/bwoester/cold-frontier/issues/44) |

The [allocation experiment](https://github.com/bwoester/cold-frontier/issues/43) tracks the comparison of feasible allocation approaches.
