---
status: under-discussion
type: game-design
---
# Game Mechanics Overview

> [!important] Evidence
> This map collects **topics and ideas**. Each topic records its status and origin. See [Current Technical State](../architecture/current-state.md) for the prototype.

## Mechanics

| Area | Current status | Further reading |
| --- | --- | --- |
| Evolution / balancing / play rhythm | Project goals and an Evo 1–5 proposal. Overall building efficiency, interaction load, and comparison with IceWars are under discussion. | [Evolution and Balancing](evolution-and-balancing.md) |
| Planets / colonization | Project goal. `Planet` exists in the prototype. | [Planetary Economy](economy.md) |
| Resources / production / bottlenecks | Project goal with parts in the code. **First area explored in depth.** | [Planetary Economy](economy.md) |
| Buildings / construction orders | Building types, the queue, and progress exist in code. Rules remain open. | [[economy#Buildings and Construction]] |
| Research / technologies | Project goal with historical IceWars inspiration. | [IceWars References](icewars-references.md) |
| Trade / transport / market | Project goal. The mechanic is still unspecified. | [[economy#Trade and Transport]] |
| Fleets / ships / movement | Project goal. Tick phases appear in prototype code and notes. | [Current Technical State](../architecture/current-state.md) |
| Battles / wars | Project goal. The mechanic is still unspecified. | [IceWars References](icewars-references.md) |
| Visibility / sensors | Phases and TODOs in code and notes. | [Current Technical State](../architecture/current-state.md) |
| Game time / events | Global phase-based tick coordinator in the prototype. The time model remains open. Configurable round speed is a future idea. | [Round Speed](#round-speed), [Current Technical State](../architecture/current-state.md) |
| Advisors / narrative / humor | Existing character and tone documents. Interactive advisors are a low-priority idea. | [Interactive Advisors](#interactive-advisors), [Narrative Tone](../personas/overall-tone.md), [Advisor Relationships](../personas/interpersonal-dynamics.md) |
| Players / alliances / social interaction | Players are implemented and alliances are historically documented. Rules remain open. | [IceWars References](icewars-references.md) |

## Round Speed

**Future idea.** A round could define how much game time elapses per unit of real time. At 4× speed, 15 real minutes would correspond to 60 game minutes. This could support speed rounds and clock-driven tests that use a controllable clock.

Construction durations and production rates would still be expressed in game time. The speed factor would determine how quickly the round reaches a given point in game time. Each simulation engine would remain free to choose its own time resolution and advancement model.

## Interactive Advisors

**Future idea · Low priority.** Let the advisors react to the player's colony, talk among themselves, and answer follow-up questions in character. Situational humor could help give Cold Frontier a distinctive voice. The same conversations might help new players understand cause and effect in their first production chains.

For example, when a waterworks runs out of ice, Fiduciary could celebrate the drop in ice consumption while Elara explains why water production stopped. The player could then ask what to check next. The game supplies the facts and visible options from the player's own view of the colony. Essential warnings and figures are available to everyone through the game UI.

One option is to let players opt in by connecting a provider account through its official authorization flow. [OpenRouter](https://openrouter.ai/docs/guides/overview/auth/oauth) is one candidate. Its [free models](https://openrouter.ai/collections/free-models) could cover occasional conversations without inference charges for the player or the game. The game would only request free models and pause generated dialogue when a quota is exhausted or the provider is unavailable.

An initial experiment could cover a single water shortage and a follow-up question. Open questions are whether new players can explain the shortage and identify their next step more easily than with the standard game hint, whether players enjoy the characters enough to return, and whether they would connect an external account after seeing a sample scene.

## Open Decisions

- Global or local ticks, discrete-event simulation, or a combination?
- Where are the consistency boundaries between a planet, player, fleet, and market?
- How deep should production chains and resource depletion go?
- Which IceWars mechanics should we adopt, and which should we deliberately change?

## First Story to Explore

A player builds a mine on their planet. The mine consumes energy and extracts iron. What happens if energy becomes scarce or the iron deposit runs low? See [[economy#Initial Scenarios (Expected Values Where Defined)|Initial Scenarios]].
