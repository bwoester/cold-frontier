---
status: under-discussion
type: game-design
---
<a name="evolution-gebäudeeffizienz-und-spielrhythmus"></a>

# Evolution, Building Efficiency, and Play Rhythm

**Status as of 2026-11-08.** Based on the project owner's ideas from that day's project discussion.

<a name="herkunft-und-verbindlichkeit"></a>

## Origin and Decision Status

- **Project goal.** Keep the game interesting through all evolution stages. New stages should introduce mechanics and decisions.
- **Project goal.** Later building generations should cost more and take longer to build, while operating more efficiently once required infrastructure is included. They can take longer to pay back.
- **Project goal.** Longer build times should reduce the demands on players as the number of planets and fleets grows.
- **Project owner's proposal.** The Evo 1–5 progression below and the gradual expansion of the counter system.
- **Analysis and proposals.** The metrics, calculations, scenarios, and risks on this page. Specific building parameters, unlock rules, and timing targets remain open.

Evolution here refers to player progression. [Project stages](project-stages.md) describe development of the game.

<a name="1-evolution-als-einführung-neuer-entscheidungen"></a>

## 1. Evolution Introduces New Decisions

Stages should build on what players have learned and open new strategic questions. New resources, additional colonies, intergalactic travel, genetics, and terraforming are possible milestones. Adding a resource should create a new trade-off. Repeating the same production chain would fall short of that goal.

| Stage | Project owner's proposal | Intended new decisions | Open questions |
| --- | --- | --- | --- |
| Evo 1 | Starting planet and its local economy | Understand production chains, spot bottlenecks, and balance expansion against supply and declining yield | Initial scope. First fleets and combat are unassigned. |
| Evo 2 | Expand beyond the first colony and build a second in the same galaxy | Choose destinations, plan starting cargo and resupply, and coordinate two economies | Whether the starting planet can be abandoned remains open. |
| Evo 3 | Trade and global finance, satisfaction and taxes | Weigh local production against trade, supply against tax revenue, and choose economic roles | Meaning of global finance and whether credits or trade are available earlier. |
| Evo 4 | Access to other galaxies | Weigh range, new destinations, and longer supply routes | Unlocks, suitable ships, and relation to existing colony limits. |
| Evo 5 | Genetics and terraforming | Specialize or modify populations and planets | Effects, costs, limits, and ability to change choices. |

**Proposed direction for satisfaction.** A more satisfied population should be more tolerant of higher taxes, yielding credits. The tax base, feedback from tax levels to satisfaction, delays, and economic consequences remain open. No formula or automatic monotonic increase in net revenue has been decided.

**Analysis.** Trade, finance, satisfaction, and taxation together may create a steep learning curve. Evo 3 could spread them across several unlocks. Evo 5 also needs decisions that remain relevant after unlocking so the final stage offers more than an endpoint.

**Interaction with depletion.** If colonization unlocks in Evo 2, the starting economy must support the intended path despite declining yields. Different paces of play should not accidentally leave players unable to progress. [Planetary survival protection](economy.md) alone cannot guarantee economic progress.

<a name="ressourcenwechsel-und-schutz-von-neueinsteigern"></a>

### Resource Progression and Protection for New Players

**Project owner's idea.** Demand gradually shifts toward new resources at higher Evo stages. Early stocks should become less attractive as raid loot for far more advanced players. Existing colonies, buildings, and production chains should retain their value. Which IceWars resources Cold Frontier will use remains open. The project owner recalls VV4A becoming available later in IceWars. The [source catalog](../sources/icewars-catalog.md) confirms that the resource was in the export. Its exact unlock stage remains unverified.

**IceWars player recollection.** Iron and energy were processed into steel, and steel and energy into VV4A. The project owner recalls iron being widely available and less economically relevant late in the game. Limited processing throughput is one possible explanation. If existing iron stocks already keep steel production at capacity, additional iron speeds up neither steel nor VV4A production. Which capacities or other factors mattered in IceWars remains open.

**Proposal to test.** New resources become an additional bottleneck for advanced projects. Existing basic materials remain relevant for purposes such as supply, repairs, or as a limited input to new production chains. An advanced colony can obtain them efficiently from its own production. Whether old facilities keep running, can be upgraded, or can be converted gradually is a balancing question. Conversion rules and any automatic resource transition remain open.

**Limits of the approach.** Lower loot value can make economically motivated raids less frequent. Early resources can remain attractive as loot if extra units increase throughput, cover a foreseeable shortage, or sell for a high price. Large overall demand alone is insufficient if local extraction already covers the processing facilities' needs. Players may still attack for other reasons. Whether additional protection rules are needed remains open.

**Test scenario.** Under the same conditions, an advanced player compares raiding an early colony with producing the resources locally, trading, and attacking a militarily relevant target at their own stage. The comparison includes loot, flight time, and risk of losses. Also check whether a player's existing facilities still make a meaningful contribution after that player advances to a new Evo stage. Earlier resources can remain economically useful while the additional gain from raiding for them falls for advanced players.

<a name="2-gebäudegenerationen-als-versorgungssystem-vergleichen"></a>

## 2. Compare Building Generations as Supply Systems

<a name="gewünschte-wirkung"></a>

### Intended Effect

A new building type should be an attractive long-term investment even if it costs more and takes longer to build. Include the following in the comparison:

1. The production building itself.
2. Housing for additional workers.
3. Ice extraction, water production, and energy supply for these people and facilities.
4. The infrastructure for these suppliers, including their own staffing and energy needs.
5. Research, research facilities, and their supply until unlock.
6. Other prerequisites specific to the scenario, such as storage upgrades.

Apply the actual rules in [Planetary Economy](economy.md). For example, adding housing does not immediately create workers. Account for population growth and availability over time once their rules are defined.

<a name="effizienz-und-amortisation-getrennt-messen"></a>

### Measure Efficiency and Payback Separately

**Analysis.** Efficiency needs a basis for comparison. More output per building does not establish greater efficiency for the whole supply system. At minimum, compare:

| Dimension | Comparison at equal usable net output |
| --- | --- |
| Operation | External resource consumption per hour, including water and energy throughout the supply chain |
| Workers and space | Total population, required housing, and occupied building space where applicable. Space limits remain open. |
| Investment | Construction costs of the entire added supply system, per resource |
| Unlock | One-time research and infrastructure costs and time until first use |
| Time | Construction effort, earliest production start, and payback relative to the alternative |
| Attention | Required interventions and consequences of late action |

**Proposal.** State the efficiency gain at each intended use explicitly, such as lower supply cost for the same net output. Whether each generation must improve every operating dimension or can make deliberate trade-offs remains open.

Higher initial investment can take longer to recover even with lower operating costs. When a deposit is near depletion or little game time remains, an older, cheaper generation may still be the better short-term choice. Evaluate whether the new generation is attractive over its intended horizon.

<a name="freischaltung-und-bestehende-infrastruktur-fair-zurechnen"></a>

### Account for Unlocks and Existing Infrastructure

Show two perspectives:

- **Full path from a common starting point.** Both strategies include all additional research, construction, and supply costs. Identical existing infrastructure belongs to the common initial state.
- **Next investment after unlocking.** Research already paid for is a sunk cost. Compare the costs and benefits that differ from that point onward.

For an illustrative per-building comparison, allocate research costs across a stated number of new buildings. That quantity is an analysis assumption. Count the same research or shared infrastructure once on the full path. Existing spare capacity and the next required expansion have different marginal costs.

<a name="3-schlankes-fachliches-balancing-modell"></a>

## 3. Lightweight Balancing Model

<a name="datenmodell"></a>

### Data Model

**Proposal.** Start with a small scenario table of building types and directed relationships:

- Construction cost vector and work required or reference build time under named conditions.
- Research and building prerequisites as unlock relationships.
- Hourly production and consumption vectors at named operating conditions.
- Staffing needs, housing capacity, and other capacities that have been decided.
- Starting stocks, existing buildings, spare capacity, and exhaustible deposits.

Unlocks and ongoing supply are separate relationships. Whether a research building must keep operating for an unlocked mine to work would be a separate rule.

<a name="stationäre-kapazitätsrechnung"></a>

### Steady-State Capacity Calculation

For an initial comparison under constant conditions:

```text
x[j]       = number of buildings of type j
S[r,j]     = production minus consumption of resource r per building j per hour
net[r]     = sum over j of S[r,j] * x[j] - other demand[r]
```

Include all required suppliers in x. Nonnegative net balances for required inputs are an initial feasibility condition. Add staffing and housing as separate capacity constraints. For a fair comparison, each alternative meets the same target for net output of the product of interest.

The model exposes feedback, such as energy used by water plants supplying additional power plant workers. Count resources and workers only once. A resource flow cannot be both an additional external purchase and internal production.

**Limits.** Integer building counts, construction queues, starting stocks, yield changes, and economic startup need more detail. An economy that balances on paper can still fail to start without reserves. Optimization is optional. A few hand-selected building combinations suffice for an initial comparison.

<a name="zeitlicher-strategie-vergleich"></a>

### Compare Strategies Over Time

Later, compare full expansion sequences from the same initial state. Existing rules for cost deduction at queuing, queue surcharges, supply, and construction modifiers also apply in the balancing model.

```text
B_s(t) = usable resource stock for strategy s at time t
D(t)   = B_new(t) - B_old(t)
```

B includes all inflows and outflows, including investment, internal use, deliveries, and overflow losses. Common starting stocks cancel in D. Keep the resource vector for spending and time series so resources such as iron and water stay separate.

If a common measure of value is useful, use explicit, constant assumed weights w:

```text
V_s(t) = sum over r of w[r] * B_s[r](t)
```

The weights are assumptions for analysis. Market prices remain open. Relative payback is the point from which the new strategy matches or exceeds the comparison strategy under the chosen measure for the rest of the horizon. Further investments or depletion can cause multiple crossings.

The simplified calculation of added investment divided by added hourly net benefit works only with a positive, constant difference after startup, comparable valuation, and lead time accounted for. The alternative might be producing during research and construction. Include that head start.

**Track separately.** Cumulative gross extraction, cumulative usable net output, and available stocks. Internal consumption, construction spending, or overflow can leave little economic gain from high gross extraction. Add remaining deposits and installed capacity to the stock curves so their value is visible.

<a name="4-bauzeit-als-entlastung-und-spielrhythmus"></a>

## 4. Construction Time and a Less Demanding Play Rhythm

**Project goal.** As players acquire more planets and fleets, longer builds should leave room for other decisions. Duration helps determine how often players need to check in.

**Analysis.** Parallel colonies, short infrastructure orders, transport completions, and queues can still produce frequent interventions even when individual builds take longer. Long build times can also create idle waiting or slow feedback.

**Proposed metrics:**

- Completions per real day across the account.
- Required player decisions and manual interventions, measured separately.
- Time until the next intervention is needed to avoid a stated drawback.
- Idle time, overflow, and delayed progress with regular and infrequent check-ins.
- Time to the first noticeable effect of a decision.

A rough estimate for permanently occupied, independent construction slots is the sum across planets of 1 / average build time for completions per hour. Actual interaction load also depends on queues, which can batch decisions, and deadlines from other mechanics.

**Project owner's clarification, 2026-11-08.** [Queue surcharges](economy.md#queue-surcharges-and-collaboration) support the goal of easier scheduling. Paying extra buys a predictable break. For example, a player might come back to place the next construction order after roughly six hours instead of three. The example illustrates the idea without guaranteeing a time window, and the existing time surcharges still apply. Timely manual orders reward active players economically. The queue lets players take longer breaks when needed without committing to a fixed play style. This replaces the earlier blanket characterization as a conflict between goals.

**Balancing question.** Set an appropriate cost for the flexibility, including the existing extension of build time. Both choices should remain practical during intended play. Longer individual builds extend the natural planning horizon. Queues let players stretch it further when their schedules require it.

Targets for daily effort, check-in frequency, and build times by Evo remain open. For comparisons, use the same explicit activity windows and avoid assuming constant availability.

<a name="5-einheiten-und-wachsende-konterkomplexität"></a>

## 5. Units and Increasing Counter Complexity

**Project owner's proposal.** Expand an initial rock-paper-scissors system with fourth and fifth components. Unit roles and their Evo assignments remain open.

**Analysis.** Additional components create depth when they produce different decisions and responses. A new component should leave existing roles useful. With four roles and only pairwise wins or losses, each has three opponents, so each cannot have equal numbers of wins and losses. Asymmetry or neutral matchups can be intentional.

**Proposed test.** Create a counter matrix for each unlock state and compare matchups with similar resource budgets. Add mixed fleets, availability, resupply, and deployment conditions as those rules become clear. An even matrix alone does not establish balanced fleet play.

<a name="späte-evolution-und-attraktive-konflikte"></a>

### Late Evolution and Appealing Conflicts

**Project owner's assessment, 2026-11-08.** Larger and more powerful ships dominate the historical Evo 4–5 steps discussed so far. Planetary defense is another possible mechanic, with historical evidence still to confirm here. The earlier blanket description of these stages as qualitative leaps went too far.

**Project owner's hypotheses.** Energy-intensive shields could require restructuring the economy. Cloaked fleets could offer military advantages and make conflict more appealing. Planetary shields, their energy needs, and cloaking effects remain open for Cold Frontier and have yet to be confirmed historically here.

**Historical recollection clarified on 2026-11-08.** [Raids and Large Wars](icewars-references.md#player-recollection--raids-and-large-wars) had distinct consequences. Raids involving limited military losses and loot from unprotected, already extracted stock happened more often, though they were not everyday events. The cost problem the project owner remembered as the biggest balancing issue concerned large wars. Destroyed fleets and bombed buildings set long-term development back significantly, and rebuilding also consumed game time. This player recollection replaces the earlier blanket statement about avoiding conflict. General causes have not been established from game data.

**Open design problem.** Extensive destruction can undercut the game's promise of building up an economy. Replacing materials alone does not restore lost construction time, foregone production, or ground lost to other players. This is a design observation. How to make severe war consequences appealing or compensate for them remains unresolved. Cold Frontier has made no decision to require wars of this scale or adopt a compensation mechanic. Further design is deferred.

**Game design question.** What rewarding, bounded, and understandable conflicts should players choose to engage in? Consider both sides, including losses, rebuilding, and defender absence. The combat system remains open.

**Analysis.** Larger ships can simply scale existing decisions. Shields or cloaking add distinct choices when deployment, costs, limits, and counters introduce new trade-offs. High shield energy demand might put military protection in competition with civilian production. Building more power plants alone might settle the choice permanently.

Cloaking can reduce the attacker's risk while increasing uncertainty and losses for the defender. Both sides' experiences matter when judging whether conflict is appealing overall.

**Proposals to explore:**

- Identify conflict goals and expected benefits, such as temporary advantages, access to contested deposits, or limited loot.
- Examine costs on each side, including expected losses, logistics, foregone production, and rebuilding time. Track different resources separately at first.
- Evaluate limited engagements and large wars separately. Possible options include capped losses, retreat, and partial recovery.
- Check whether defeat allows interesting follow-up decisions and credible recovery. Assess whether lower costs or higher loot enable repeated mutual gains or encourage attacks on weaker players.
- Describe specific shield and cloaking situations, counters, and response opportunities before assigning values and Evo stages.

<a name="sekundäre-basen-als-ziele-für-bombardierung"></a>

### Secondary Bases as Bombardment Targets

**Project owner's idea.** Less permanent secondary bases or outposts could be preferred targets for attacks that damage buildings. Building and rebuilding them should take a manageable amount of game time. Destroying them should still have a noticeable economic impact so bombers have a worthwhile mission. This could make long-established colonies less attractive targets for bombardment.

**Proposal to test.** An outpost secures access to a local deposit. Losing it interrupts output until the player recaptures the site, builds a replacement, or develops another source. The economic impact can therefore be significant even when the base itself can be replaced in a manageable amount of time. The target should also offer the attacker a concrete benefit, such as access to the site. Inflicting damage alone provides an incentive only in some situations.

**Open questions.** Do players choose whether to establish outposts in contested areas? How do capture and replacement work, and which targets can be bombarded? Test repeated attacks and dependence on individual outposts separately from the effort needed to build one. Even a quickly replaceable outpost can cause a lasting setback if its output is indispensable or reconstruction remains blocked.

<a name="offene-referenzen-für-einheitenwerte-und-geleitschutz"></a>

### Open References for Unit Stats and Escorts

**Project owner's idea, 2026-11-08.** Investigate suitably licensed stats from existing games for a balancing starting point, including speed, initiative, attack, defense, and protection for large ships from small escorts. [Research and open modeling questions](ship-balancing-references.md) compares FreeOrion, Wesnoth, and Zero-K. Data reuse and combat rules remain open.

<a name="6-abgleich-mit-den-vorhandenen-icewars-tech-trees"></a>

## 6. Comparison with the Available IceWars Tech Trees

**Baseline for this page.** Repository state on main on 2026-11-08, starting commit 0cfbae3b091feb1f01a6622b949343f9968d83d4. Findings come from the [research mapping](../sources/icewars-ship-research.md), [source catalog](../sources/icewars-catalog.md), and [IceWars references](icewars-references.md) already documented there. The original images were not fully re-reviewed for this page. The selection is incomplete.

| Historical stage | Documented findings | Relevance to the current design |
| --- | --- | --- |
| Series B, Evo 1 | Spaceflight, launch ramp, and small shipyard. System transports. KISS-01 via ice transport and colonization of rocky planets. Large ice mine under robotics. | Progression from local economy to spaceflight and colonization is visible. Colonization appears earlier than in our proposal. |
| Series B, Evo 2 | Chemical hyperspace drive, large ship hulls, Cryogenic. Hyperspace transports and INS-03A. Admission to the civilized worlds follows an imperial taxation application with the imperial tax authority. | Range, logistics, and administration progress. Specific trade and satisfaction rules and exact travel semantics are unproven. |
| Series B, Evo 3 | Genetics before terraforming basics. Terraformer Alpha. Asteroid mining and Robominer V1. Very large ship hulls. | New ways to change planets and resource supply. Genetics and terraforming appear earlier than in our proposal. |
| Series B, Evo 4 | Fusion technology, Alpha Class shields, improved fighter launch technology, and new transport and carrier nodes. | Further energy, combat, and logistics technologies. The initial unlock for crossing galaxies is unclear. |
| Series B, Evo 5 | Cloaked fighter, corvette, and cruiser. Graviton beam and dreadnought research. | Cloaking could introduce distinct tactical decisions. Its actual effects remain unreconstructed. |

**Source limits.** Series A and B remain separate. Terraforming basics appears in A1 and B3 with different research points and prerequisites. A common version has not been established. Names such as hyperspace transport or interstellar colony ship do not establish the ability to cross galaxies. The project owner recalls INS-03A as part of advanced intergalactic progression. The matrix alone cannot establish its exact unlock.

**Findings.** Earlier stages suggest qualitative milestones. The selected Evo 4–5 evidence mainly shows advanced technology and units. It cannot establish a distinct player experience, validate our Evo sequence, or confirm efficiency gains or rock-paper-scissors combat. Production, consumption, and combat values are insufficient for such conclusions. Trade, satisfaction, and other nonmilitary branches need more systematic review.

<a name="7-nächste-prüfbare-balancing-szenarien"></a>

## 7. Next Balancing Scenarios to Test

The expectations are proposed checks. Define actual numbers and thresholds before automated acceptance testing.

| Scenario | Common starting point or variation | Outcome to investigate |
| --- | --- | --- |
| Old versus new building generation | Same target net output, full supply chain included | The new type meets an efficiency target still to be defined, accounting for indirect consumption. |
| Research investment | Full path to one, several, and many new buildings | The unlock cost is counted once, and benefit and payback visibly depend on utilization. |
| Spare infrastructure | Spare capacity versus a required supplier upgrade | Step changes in marginal costs remain visible. Avoid a blanket infrastructure percentage. |
| Short and long horizons | Same deposit, varying time remaining and yield | The new generation is attractive in its intended use and poor investment windows are identifiable. |
| Transition from Evo 1 to Evo 2 | Different expansion sequences and activity windows | Depletion does not inadvertently block access to a second colony. |
| Growing account | One colony versus several, with fleet tasks | Longer builds actually reduce or limit actions needed. Targets remain open. |
| Satisfaction and taxes | Same population and supply, different policies | Revenue and feedback are visible, without making maximum taxation always optimal by accident. |
| Conflict costs and rebuilding | Limited conflict and war, both sides, different power balances and activity windows | Benefits, losses, and recovery time are visible. Evaluate attacker incentives and defender experience together. |
| Shield and cloaking hypotheses | Specific deployments with and without the ability, along with possible counters | New trade-offs emerge. Do not assume unverified historical effects. |
| New counter role | Matchups before and after unlocking at equal budgets | No unintentional universal advantage. Existing roles retain their intended uses. |

<a name="8-geeignete-visualisierungen-und-erster-arbeitsschritt"></a>

## 8. Visualizations and First Step

**Balancing proposals awaiting calculation:**

| Visualization | Question |
| --- | --- |
| Dependency graph with separate unlock and supply relationships | What has to be built, researched, and supplied for the new building? |
| Stacked costs per strategy and resource | How much goes to production, upkeep, and unlocks? |
| Cumulative net output and stock curves with completion times | When does the new strategy catch up? |
| Efficiency at equal net output | Where are the operating gains and trade-offs? |
| Account timeline with completions and required actions | Does the late-game rhythm ease demands on players? |
| Counter matrix by Evo | Which matchups and roles change when new units arrive? |

**Recommended first step.** Model two building generations with the same production purpose and full supply in a small scenario. First compare infrastructure costs at equal net output, then add research and construction timing. A simple table model and exportable SVG curves suffice at this stage.

**Decisions needed before choosing numbers.** Efficiency metric, comparison horizon, common starting state, research scope, supply recipes, and assumed activity windows. Leave missing values open or mark them as illustrative assumptions.
