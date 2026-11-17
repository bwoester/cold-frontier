<a name="cold-frontier--wissensbasis"></a>

# Cold Frontier Knowledge Base

This documentation is a **working model that evolves with the project**. It does not constitute a finished requirements specification or an agreed target architecture.

<a name="einstieg-in-obsidian"></a>

## Getting Started in Obsidian

Open the repository (or the `doc/` directory) as an Obsidian vault. Start with [Game Mechanics Overview](game-design/overview.md) or the visual map [Game Design Canvas](game-design/game-design.canvas). Internal links refer to files in `doc/`.

<a name="bereiche"></a>

## Sections

- [Game Mechanics](game-design/overview.md) – map of topics and current understanding.
- [Planetary Economy](game-design/economy.md) – the first topic explored in depth, with questions and examples.
- [Continuous Ice Extraction](game-design/continuous-ice-extraction.md) – formulas, parameters, examples, and charts for advancing the simulation between events.
- [Extraction Over a Round](game-design/extraction-balancing-scenarios.md) – long-term scenarios, extreme cases, and open balancing assumptions.
- [Historical IceWars Materials – Progress](sources/icewars-catalog.md) – source inventory, analysis, documentation, and archiving.
- [IceWars Research](game-design/icewars-references.md) – historical inspiration rather than Cold Frontier requirements.
- [Current Technical State](architecture/current-state.md) – observations of the prototype, with no architectural decision implied.
- [Command Ordering and Concurrent Actions](architecture/command-ordering.md) – an open architecture question for the entire game.
- [Earlier Technical Notes](notes.md) – the original collection of ideas.
- [Narrative Tone](personas/overall-tone.md) and [Advisor Relationships](personas/interpersonal-dynamics.md) – existing character concepts.

<a name="qualitätspriorität"></a>

## Quality Priority

**Clarified by the project owner on 2026-11-01.** The simulation should be easy to understand, trace, and manage. Technical sophistication or speculative resource savings do not justify hard-to-follow processing. Evaluate the time model and execution strategy against gameplay scenarios and realistic operational goals. Global ticks and lazy advancement both remain open options. The [[sources/icewars-catalog#RD-01 Context – Earlier Lazy Approach and Current Priority|source catalog]] records lessons from the earlier lazy design.

<a name="konventionen"></a>

## Conventions

**Evidence labels.** `Project goal` (stated by the project), `Current code` (observed in source code), `IceWars source` (historical inspiration), `Proposal`, `Open question`. Observations of current code and historical sources do not establish binding game rules.

**Maturity.** `Idea` → `Under discussion` → `Decided`. A rule remains open until explicitly decided. Introduce ADRs and formal requirement IDs when needed.

**Workflow.** Navigate from the overview to a mechanic, then refine its gameplay goals, rules, examples, and questions in Markdown. The canvas provides a visual view of the Markdown documentation. Record new findings in the relevant Markdown pages and version changes with Git.

**Working with sources.** After reviewing historical materials, update the [source catalog](sources/icewars-catalog.md) with links to the results. Review, adoption into the game design, and archiving are separate milestones.

**Git collaboration.** Small, straightforward changes can go directly to `main`. Discuss larger changes together first and choose a branch and pull request according to their scope. Account for concurrent changes when updating existing files.

**Next focus.** Planetary economy, including dependencies, stocks, energy, and resource depletion. Timing and concurrency follow.
