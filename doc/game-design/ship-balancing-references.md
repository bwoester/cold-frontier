---
status: under-discussion
type: game-design-research
---
# Ship Balancing: Open References and Escorts

**Status as of 2026-11-08.** Research proposal. Adoption of a combat system or external data remains open.

## Starting Idea and Desired Properties

**Project owner's idea.** Use existing, well-balanced units from other games as a starting point if licensing and gameplay transfer permit it. Desired stats include speed, initiative, attack, and defense, potentially by ship or weapon class. Large ships could benefit from having enough small escorts.

**Analysis.** An open dataset saves initial parameter work. Balance also depends on costs, availability, targeting, order, hit rules, movement, and consequences of losses. The candidates below are relevant references. Superiority or full balance has not been independently established.

## Research Findings

| Candidate | Relevant content | License findings | Limits on transfer |
| --- | --- | --- | --- |
| FreeOrion | Ships and components, structure, shields, weapons and shot counts, speed, stealth, and detection. Documented changes describe fighters and specialized flak. | default/COPYING lists CC BY-SA 3.0 for game content including functional scripts. Scripts are also GPL v2. | Component-based design goes beyond fixed ship classes. Read a specific version together with technology effects and combat resolution. No generally transferable numerical initiative stat was identified. |
| Battle for Wesnoth | Explicit unit costs, hit points, movement, weapons, damage and strike counts, damage types, and resistance. Firststrike is a weapon special. | The official copyright page lists GPL v2 or later for code, including WML, and data, subject to individual notices. Wiki text is separately licensed under the GFDL. | Terrain-based avoidance, time of day, turn order, and retaliatory strikes influence strength. Firststrike is specific to an attack. |
| Zero-K | Raider, riot, skirmisher, assault, and artillery roles, with combinations that cover one another. | Official project information describes Lua code as generally GPL with exceptions. A developer statement says mostly GPL 2.0. Content licensing varies, and individual files have yet to be checked. | Real-time movement, range, projectile behavior, and control underpin its counter system. Abstract fleet resolution would change what its stats mean. |

**Research priority.** Start with FreeOrion as the closest ship reference. Wesnoth offers discrete combat profiles, and Zero-K is especially relevant for roles and interactions. Compare values from different sources against a shared combat contract before combining them.

For each candidate, a complete unit dataset pinned to a commit and tested under Cold Frontier rules is still pending. The quality of any transferred balancing remains hypothetical. Older design pages and changelog entries may describe rules that have since changed.

## License and Provenance for Future Reuse

The licenses specify conditions for reuse. FreeOrion's content license permits commercial adaptation under its terms, including attribution, linking to the license, identifying changes, and ShareAlike when distributing adaptations. Which parts of an actual integration constitute an adaptation would depend on the details.

If data are reused, document source files and versions, attribution, license, changes, and dependencies. Macros and research modifiers can affect effective stats. Check GPL and content-license obligations against the actual material. The treatment of individual numbers and whole tables is a separate question, as is reuse of graphics, names, and descriptions.

## Questions Before Comparing Stats

- **Speed.** Travel speed and tactical movement may use separate values.
- **Initiative.** Does it govern order, first shot, or frequency of action? For ordering, can destroyed enemies return fire?
- **Attack.** Distinguish damage per hit, shots fired, and hit chance.
- **Defense.** Structure, evasion, armor, and shields have different effects. Use separate values only where needed.
- **Costs.** Account for build resources and time, and eventually operation and upkeep. Comparing equal ship counts across classes can be misleading.
- **Targeting and counters.** Which weapons can engage which opponents, and how are targets assigned within fleets?

These questions guide selection of the stats that support the intended decisions. The full list remains open.

## Escort Protection Proposal

**Gameplay goal.** Small ships should remain useful alongside large ships. Mixed fleets can protect large units.

Two open alternatives:

1. **Protection through a specialized combat effect.** Escorts engage bombers, fighters, or other concrete threats. Protection follows from combat if targeting and order permit it.
2. **Abstract escort bonus.** Eligible operational escorts provide protection capacity. Large ships create protection demand. Coverage and the bonus have caps.

Illustrative formula for option 2:

```text
coverage = min(1, effective protection capacity / protection demand)
bonus    = maximum bonus * coverage
```

Apply no escort bonus when protection demand is zero. Within one area of effect, the same capacity cannot be counted in full for multiple ships. Small units count according to actual protective ability, preventing unlimited stacking based on unit count alone. Whether coverage applies fleet-wide or per protected ship remains open.

**Open questions.** Which weapons or roles are covered? Which escorts qualify as operational? When is coverage recalculated after losses? What limits and counters apply? A universal bonus could make escorts mandatory and crowd out specialization. A bonus in addition to effective interception can reward the same protection twice.

**Proposed scenarios.** Compare large units alone, with insufficient, suitable, or excess escorts, with escorts lost during battle, with many cheap unsuitable ships, and with several large ships sharing escorts. Use equal overall budgets against different enemy compositions. Escorts should help against their intended threats without becoming the best investment against every fleet.

## Next Step

Once the general combat flow is clearer, choose one candidate and pin its version. Reconstruct three to five roles with full effective profiles and costs. Check them first under their original rules, then assess adaptations to Cold Frontier and escorts separately. Implementation remains an open decision.

## Sources Reviewed

Accessed 2026-11-08. Links to moving project branches do not pin a data version.

- [FreeOrion README and content structure](https://github.com/freeorion/freeorion)
- [FreeOrion content license, default/COPYING](https://github.com/freeorion/freeorion/blob/master/default/COPYING)
- [FreeOrion FOCS data model](https://www.freeorion.org/index.php/FOCS_Scripting_Details)
- [FreeOrion changelog, including historical fighter, flak, and shield changes](https://github.com/freeorion/freeorion/blob/master/ChangeLog.md)
- [Wesnoth copyrights for code, WML, data, and wiki](https://wiki.wesnoth.org/Wesnoth:Copyrights)
- [Wesnoth UnitTypeWML](https://wiki.wesnoth.org/UnitTypeWML)
- [Wesnoth defense and resistance](https://wiki.wesnoth.org/Defense_and_resistance)
- [Wesnoth attack properties including Firststrike](https://wiki.wesnoth.org/AbilitiesWML)
- [Zero-K classes and roles](https://zero-k.info/mediawiki/Unit_classes)
- [Zero-K project introduction and Lua licensing](https://zero-k.info/Forum/Thread/20428)
- [Zero-K developer statement about file-level exceptions](https://zero-k.info/PostHistory/ViewEntry/93546)
- [CC BY-SA 3.0 terms](https://creativecommons.org/licenses/by-sa/3.0/)
