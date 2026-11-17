---
status: research
type: source-notes
---
<a name="icewars--historische-inspiration-keine-soll-spezifikation"></a>

# IceWars – Historical Inspiration

The following information comes from historical web sources, supplied materials, and an explicitly identified player's recollections of the original IceWars. Its numbers and mechanics may have changed between IceWars rounds. Cold Frontier's rules are decided separately.

The [source catalog](../sources/icewars-catalog.md) lists the supplied materials and tracks analysis, documentation, and archiving.

<a name="belastbare-orientierung"></a>

## Documented Background

- The game described itself in terms of colonizing planets, competing for resources, ships, research, and increasingly complex construction and technology options. Humor and self-mockery were explicitly part of its identity. [GamesSphere – Game Description](https://www.gamessphere.de/games/icewars)
- The game ran in real time, with rounds of about 9–12 months according to the overview. Cold Frontier's tick model remains open. [GamesSphere – Overview](https://www.gamessphere.de/games/icewars)
- Changes to research, aliens and pirates as NPCs, and quests were among the features announced in 2009. [GamesSphere – Round Restart](https://gamessphere.de/games/icewars/news/2480_rundenneustart_mit_neuen_features)
- In 2008, new players had their own galaxies with protection mechanisms. [GamesSphere – New Players](https://gamessphere.de/games/icewars/news/1577_neueinsteiger_in_zukunft_mit_mehr_schutz)
- In 2010, an upgrade system for existing ships was described with research prerequisites, construction costs, and time-dependent modifiers. [GamesSphere – Ship Upgrades](https://gamessphere.de/games/icewars/news/3565_pimp_my_shuttle_-_aus_alt_mach_neu)
- For the ninth round in 2009, the IceWars team reported **several thousand participating players**, exceeding the initial estimate of fewer than 1,000. [GamesSphere – Round Start](https://gamessphere.de/games/icewars/news/2510_erfolgreicher_start_in_die_neunte_runde)

<a name="erfahrungsbericht--eisförderung-und-ausbaukonkurrenz"></a>

## Player Recollection – Ice Extraction and Competing Construction Priorities

**Source.** Benjamin Wöster, project discussion on 2026-11-01. Player recollection without independent confirmation in the supplied XML files. The round or version remains unknown.

- According to the recollection, declining yields affected only ice. Extraction was proportional to ice density. At 15% density the same mines yielded half as much as at 30%.
- Density stayed at or above 5% on ordinary planets and 15% on special ice planets. Extraction remained possible at those floors.
- The starting planet may have started at 30% density. The recollection is uncertain.
- The effect was barely noticeable for months. Players already had hundreds of ice mines by the time shortages became pronounced. Typical responses included building more mines, raiding, and choosing an ice planet for the next colony.
- Multiplying the number of mines would have taken months and displaced construction for research, the military, and the economy. Build time was therefore a major bottleneck.
- In the player's experience, even well-organized players rarely colonized more than six planets. Seven or eight were reported, and some beginners reached four. A hard game limit is unproven.

**Separate supporting sources:**

- The [Evo 1 Tech Tree](https://drive.google.com/file/d/1yt-JLQm8b-CSgF6wfcE693S_PHFz6i_q/view) is a community diagram showing a large ice mine under “Robotik” (robotics), without extraction values.
- [Resourcenabbau](https://docs.google.com/spreadsheets/d/1Wng7Wi-kAfF1JS_VECUQVXRTWIYlHXlCo7YKTTP_vnA/edit) is an earlier redesign. The inspected values make extraction proportional to the relative remaining deposit. An initial density of 1 does not directly imply a planet with 100% absolute ice density. The spreadsheet does not establish the historical formula.

The resulting Cold Frontier design direction and absence protection goal are in [[economy#Declining Yield and Time as a Resource|Declining Yield and Time as a Resource]]. The historical numbers remain reference material.

<a name="rekonstruierte-historische-einheiten"></a>

## Reconstructed Historical Units

The [XML unit catalog](../sources/icewars-ships.md) preserves all 64 export entries with material costs, duration, and `baubar`. The [research mapping](../sources/icewars-ship-research.md) records 37 name-based matches in series B1–B5, one in A2, and 26 units without reliable research evidence. It distinguishes direct research prerequisites, explicit extra fields, selected indirect building requirements, and unknown construction prerequisites. Version and naming conflicts remain visible.

**Historical reference.** Cold Frontier can draw inspiration from these units. The list, costs, and prerequisites have no confirmed status as game rules.

<a name="erfahrungsbericht--bau-frei-minuten"></a>

## Player Recollection – Free Construction Minutes

**Source.** Benjamin Wöster, project discussion on 2026-11-02. Recollection of BFM without independent confirmation in the files reviewed. Adoption for Cold Frontier remains open.

- Free construction minutes (Bau-Frei-Minuten, BFM) mainly helped late entrants catch up in an advanced round.
- Players could use a BFM balance to speed up building construction. The difference from normal build time was deducted from that balance.
- The player tentatively recalls that builds could be shortened to a remaining or minimum time of **5 or 15 minutes**. The exact floor and usage are uncertain.
- Awarding, expiry, time of use, refunds on cancellation, interactions with shortages, and use for other order types remain open.
- BFM shows a tracked balance for acceleration. How to model ordinary elapsed time remains a separate question.

Current construction cost and cancellation rules are in [[economy#Costs, Cancellation, and Progress|Costs, Cancellation, and Progress]].

<a name="erfahrungsbericht--planetare-bauwarteschlangen"></a>

## Player Recollection – Planetary Construction Queues

**Source.** Benjamin Wöster, project discussion on 2026-11-02. The available files have not independently confirmed the recollection. Adoption for Cold Frontier remains open.

- Each planet had its own construction queue.
- The player recalls having resources deducted when the order was queued, before work started.
- The first building used normal cost and time. The second queued building cost **10% more resources and time**.
- Submitting manually after the queue emptied avoided the surcharge. Planning ahead cost more and allowed longer absences without frequent logins.
- Later **genetics options** could add a waiting slot. Base capacity, the exact option, and the number of upgrades remain uncertain.
- Surcharges for later positions (equal, linear, or multiplicative), which resources they affected, and rounding remain open.
- Did an order keep its surcharge when it moved up? How did cancellation or reordering affect other costs and times? Did unstarted orders also receive partial refunds? These details remain open.

**Analysis.** The surcharge gives absence and convenience an economic value. Frequent players can act sooner and save resources and time. On 2026-11-02 Cold Frontier confirmed retaining a surcharge as a design direction. Parameters remain open. See [[economy#Queue Surcharges and Collaboration|Queue Surcharges and Collaboration]].

<a name="erfahrungsbericht--sitter-login"></a>

## Player Recollection – Sitter Login

**Source.** Benjamin Wöster, project discussion on 2026-11-02. Historical recollection without independent confirmation from the supplied files.

- A dedicated sitter login allowed other players to take certain actions in the account they looked after.
- According to the recollection, permissions covered actions inside that account and prevented interactions with other players.
- This made it easier to cover absences and rewarded collaboration and coordination, including through construction queues.
- **Project owner's interpretation.** The restriction aimed to prevent one person from using multiple accounts to benefit one another.
- **Evidence and analysis.** The exact permissions and enforcement are undocumented. Blocking direct interactions alone cannot establish that the system prevented every kind of multi-account use or indirect benefit.
- Permitted construction and research, visibility of account information, permission management, and restrictions per account remain open.

Possible relevance for Cold Frontier is described in [[economy#Queue Surcharges and Collaboration|Queue Surcharges and Collaboration]]. A sitter system remains open.

<a name="erfahrungsbericht--spezialisierungen-über-genetik"></a>

## Player Recollection – Specialization Through Genetics

**Source.** Benjamin Wöster, project discussion on 2026-11-02. Historical recollection without independent confirmation in the files reviewed.

The project owner recalls that genetics supported specializations, encouraging distinct roles and collaboration between players. Specific options, effects, costs, and switching rules remain unreconstructed. Extraction, shipbuilding, and trade profiles are current Cold Frontier ideas. The historical options are unverified.

<a name="erfahrungsbericht--galaxien-und-kolonisationsreichweite"></a>

## Player Recollection – Galaxies and Colonization Range

**Source.** Benjamin Wöster, project discussion on 2026-11-03.

- Solar systems reportedly had about 1 to 15 planets. In principle every ship type could travel between systems within a galaxy. The project owner does not recall ships confined to a single solar system.
- Crossing galaxies required specialized research and suitable ships. The INS-03A is remembered as part of this advanced stage. The KISS-01 name “Systemkolonieschiff” alone does not establish a single-system limit.
- The player recalls every fourth galaxy being a chaos galaxy. They had extreme planets with large deposits often concentrated in one resource, plus anomalies such as time distortions and antimatter vortices. The player does not recall whether anomalies affected gameplay.
- **Inspiration for Cold Frontier.** Chaos galaxies with extreme deposits and anomalies as narrative elements. Placement and deposit rules remain open. Anomalies currently have no planned simulation effect.

<a name="erfahrungsbericht--pinguine-am-rundenende"></a>

## Player Recollection – End-of-Round Penguins

In the 2026-11-03 project discussion, Benjamin Wöster recalled penguins appearing shortly before the round ended. They spread and multiplied uncontrollably. As harbingers of the apocalypse they were part of IceWars humor. The resource XML lists penguins under ID 8. Timing and effects come from recollection. [Resource Mapping](../sources/icewars-ships.md#resource-mapping)

<a name="erfahrungsbericht--raids-und-große-kriege"></a>

## Player Recollection – Raids and Large Wars

**Source.** Benjamin Wöster, project discussion on 2026-11-08. Round and version are unknown, and the available files have not independently verified this recollection. Adoption for Cold Frontier remains open.

| Conflict | Recalled conduct and effects | Recalled role in the game |
| --- | --- | --- |
| Raids | Limited losses of defensive installations and defending and attacking ships. A victorious attacking fleet could steal already extracted planetary resources that bunkers did not protect. The account does not mention destruction of civilian buildings during raids. | Occurred more often, though they were not an everyday event. |
| Large wars | Large fleets were destroyed and colonies bombarded, destroying buildings. Rebuilding consumed significant resources and game time. | The project owner recalls this as the largest balancing problem in IceWars because it substantially set back long-term development. |

The earlier blanket statement that costs prevented conflicts applies more specifically to large destructive wars. Raids were an established part of play. Loss limits, loot formulas, cargo limits, and bombardment rules remain unreconstructed.

**Open design question for Cold Frontier.** How can far-reaching wars coexist with the value of long-term development? The project owner has yet to find a satisfactory way to compensate for those losses. See [Late Evolution and Conflicts](evolution-and-balancing.md#late-evolution-and-appealing-conflicts).

<a name="mögliche-inspirationsfelder--ausdrücklich-offen"></a>

## Potential Areas of Inspiration

- A deep technology tree and unusual buildings
- Economic side effects of population and social mechanics
- Diplomacy, communities, and alliances
- Asymmetric ships, pirates, quests, and events
- Humor through advisors, event text, and mechanics

<a name="noch-zu-recherchieren"></a>

## Research Still Open

- The supplied resource XML includes VV4A. See [[sources/icewars-catalog#Official XMLs According to the Project Owner (IW-01 to IW-03)|Official XMLs According to the Project Owner]]. Confirm the original endpoint and game version. The XML does not establish production chains.
- Further building and research dependencies. Some unit mappings are reconstructed. Complete transitive chains, shipyards required for construction, and missing or unreadable later Tech Tree pages remain open.
- Construction, transport, and combat rules by game version.

**Source criteria.** Similar games such as XWars and GalaxyWars are separate sources and cannot establish IceWars rules.
