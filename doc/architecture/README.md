# Cold Frontier Architecture

This is a working arc42 overview. The linked views focus on the resource simulation and the first interactions that cross its boundary.

| Status | Meaning |
| --- | --- |
| **Decided** | A confirmed gameplay rule or an accepted architecture decision. The reference identifies which one. |
| **Implemented on main** | Behavior observed in the current prototype or an enabled acceptance case. This status is independent of whether a rule is decided. |
| **In review** | Work on a pull request. The PR link identifies the evidence. |
| **Proposed** | A candidate responsibility, design, or deployment option. |
| **Open** | A question with no agreed answer. Follow the linked issue for the discussion. |

| arc42 section | View |
| --- | --- |
| 1 · Introduction and goals | [Quality priorities and project context](../README.md#quality-priority) |
| 5 · Building block view | [Ownership and consistency boundaries](building-blocks.md) |
| 6 · Runtime view | [Resource advancement and cross-boundary interactions](runtime.md) |
| 7 · Deployment view | [Placement and horizontal scaling](deployment.md) |
| 8 · Crosscutting concepts | [Effective game time and audit](runtime.md#effective-game-time-and-audit) |
| 9 · Architecture decisions | Decision discussions are linked from the corresponding views. ADR statuses are **Proposed**, **Accepted**, **Superseded**, and **Rejected**. |
| 10 · Quality requirements | [Reproducible advancement](runtime.md#reproducible-advancement) and [scaling scenarios](deployment.md#quality-scenarios) |
| 11 · Risks and technical debt | [Concurrent actions](command-ordering.md), [prototype gaps](current-state.md#known-gaps-and-discussion-points), and [deployment risks](deployment.md#quality-scenarios) |

The [economy rules](../game-design/economy.md) and [Gherkin scenarios](../game-design/scenarios/) define gameplay and expected results. The [resource simulation contract](resource-simulation-contract.md) develops the domain interface. [Current technical state](current-state.md) records prototype details.
