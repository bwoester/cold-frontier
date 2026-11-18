---
status: research
type: source-analysis
---
# IceWars – Research Mapping for Historical Units

**Status as of 2026-11-01.** Historical reconstruction. Cold Frontier can draw inspiration from the units and prerequisites. Its game rules are decided separately.

## Scope and Sources

The [unit list with XML costs](icewars-ships.md) covers all 64 entries in the available export. Each ID has a research finding or an explicit unknown status. Other IceWars units may be absent, and the findings do not establish simultaneous availability of all units.

**Series B** refers to the five IceWars-Techtree-Evo1 through Evo5 files with similar designs. Their appearance supports grouping them. It does not establish a common version. Upload dates in 2025 do not date the game rules.

| Page | Source | SHA-256 of the image reviewed |
| --- | --- | --- |
| B1 / Evo 1 | [IW-11](https://drive.google.com/file/d/1yt-JLQm8b-CSgF6wfcE693S_PHFz6i_q/view) | `147b5b4402889e385857b646f4426d909e20e620b6062366982c6177e62eaa5c` |
| B2 / Evo 2 | [IW-12](https://drive.google.com/file/d/10j0oA_e1vozl-W2JMRedfr8BU6yID_Bh/view) | `14a6b5d754a741efc274d944bacb09b771cec29768b7ed21bf8227b1cbe5ac82` |
| B3 / Evo 3 | [IW-13](https://drive.google.com/file/d/1S-C77ux_m7YuEm88GJ6I2x7VAx5zSrgV/view) | `b948eaf811d8c7d8061d090e057c4034ca6b02cdbe15d14fb481e3df75de2cf8` |
| B4 / Evo 4 | [IW-14](https://drive.google.com/file/d/1Tzu_CmrF-Bi_YUVVNtj2ZATInYKacSHr/view) | `d637b798ca571e208d6ac6eb90b4f17b18c6758b7f0f47a35a71c02109cf368d` |
| B5 / Evo 5 | [IW-15](https://drive.google.com/file/d/1tQWofJMO0Z-jJ2WgiDTjqYixuLYhlkeQ/view) | `018d80f12bbd4fd3f49af8a020a61afa4f24c2eea40ff3991842283135107da3` |

**Series A** refers to the older files evo1.png through evo5.png and evo7.png (IW-05 through IW-10). [A1 / IW-05](https://drive.google.com/file/d/0B_fvsE93Ci4dUmNOTkxwc29xSlE/view) and [A2 / IW-06](https://drive.google.com/file/d/0B_fvsE93Ci4dRzFsTm0xbWNEcDQ/view) are legible enough to read. A3–A5 and A7 have very low original resolution. Upscaling cannot recover missing text. Evo 6 is missing. The separately named Evo 0 image cannot be assigned with confidence.

Checksums for the reviewed older images: A1 `96ad4dbe3865ee26962171e27545869d182691151bf968949eeab97429a91c97`, A2 `84359733bdbec42629fc07221a275cdeced313f1c2a924f37e74fd6dfb8df649`. See the [source catalog](icewars-catalog.md) for inventory and progress.

## Conventions and Evidence Limits

- **F:** Visible incoming arrows to the research node. **R:** Additional research explicitly marked in purple in the box. Multiple entries are shown together. Their AND/OR semantics have not been independently verified.
- **FP:** Research points for the unit's research node. Material costs and cumulative costs along the research path are separate. The legend includes unverified FP, and the values have not been independently validated.
- A unit name among prerequisites denotes research. The diagrams do not establish ownership or consumption of a built ship, or an upgrade or replacement mechanism.
- The matrix lists direct predecessors and purple extra fields. Selected earlier prerequisites are shown separately in the research chains. The transitive list is incomplete.
- **Orange lines** mark required buildings according to the legend. Building pictures below a node are associated unlocks. Required shipyard levels and exact construction requirements remain unverified.
- The diagrams contain no XML IDs. Matches use names and sometimes role labels. XML categories are retained, and a shared balancing version remains unverified. Source labels for ships, research, and buildings retain their original spelling for lookup.
- **Open** indicates a lack of reliable evidence. A unit may still exist, have prerequisites, and be unlockable.
- A name alone cannot establish required shipyards, local research, construction slots, or predecessor units.

## Mapping for All 64 XML Entries

There are 37 name-based matches in B1–B5, one additional match in A2, and 26 XML entries without reliable research mappings. The Terraformer Alpha link to a unit unlock is tentative, and SilentSorrow is a spelling variant. **The [XML catalog](icewars-ships.md) contains all material costs, durations, and raw `baubar` values.**

| XML ID | XML name | Source | Unit research node (FP) | Direct research prerequisites shown | Evidence limits |
| ---: | --- | --- | ---: | --- | --- |
| 8 | Scout | B1 | 9,600 | Raumschiffchassis (F) | No research or building line in the unit box. |
| 9 | Systrans (Systemtransporter Klasse 1) | B1 | 9,600 | Raumschiffchassis (F) | Name match. A common version with the XML remains unverified. |
| 10 | Crux (Systemtransporter Kolonisten) | B1 | 16,000 | Eistransport (F) | No orange building line. A colonist transport is a separate role from a colony ship. |
| 11 | Lurch (Systemtransporter Klasse 2) | B1 | 16,000 | Eistransport (F) | Name match. A common version with the XML remains unverified. |
| 12 | KISS-01 (Systemkolonieschiff) | B1 | 40,000 | Eistransport; Kolonie auf felsigen Planeten (F) | Indirect building prerequisite Startrampe through Extraterrestrisches Siedeln. See the research chains below. |
| 13 | Sonde X11 | B1 | 1,920 | Visuelle Optik (F) | Name match. A common version with the XML remains unverified. |
| 14 | Hunter | B3 | 200,000 | energetische Panzerung; Hyperraumphotonenantrieb (F) | The Hunter node has no field or arrow for Lionheart in this series. |
| 15 | Gorgol 9 (Hyperraumtransporter Klasse 1) | B2 | 60,000 | große Schiffsrümpfe; Systrans (F+R) | Purple marks Systrans as a research prerequisite. Possession of an existing transport remains unverified. |
| 16 | Kolpor (Hyperraumtransporter Kolonisten) | B2 | 75,000 | große Schiffsrümpfe; Cryogenic; Crux (F+R) | Name match. A common version with the XML remains unverified. |
| 17 | Eisbär (Hyperraumtransporter Klasse 2) | B2 | 50,000 | große Schiffsrümpfe; Lurch (F+R) | Name match. A common version with the XML remains unverified. |
| 18 | INS-03A (interstellares Kolonieschiff) | B2 | 100,000 | große Schiffsrümpfe; Cryogenic; KISS-01 (F+R) | Name match. A common version with the XML remains unverified. |
| 20 | Pflaumenmus (Carrier) | B2 | 75,000 | große Schiffsrümpfe (F) | Diagram: small carrier. XML category: destroyers. |
| 21 | bürokratischer Formularbomber | Open | Unverified | Cannot reliably reconstruct | B2 and A2 name Atombomber. A match with bürokratischer Formularbomber remains unverified. |
| 22 | Sheep | B2 | 25,000 | kleine Lebenserhaltungssysteme; Gausskanone (F) | Name match. A common version with the XML remains unverified. |
| 23 | Shark | B3 | 110,000 | Photonenantrieb; energetische Panzerung; Sheep (Jäger) (F+R) | Name match. A common version with the XML remains unverified. |
| 24 | Terminus Sonde | B2 | 145,000 | Cryogenic (F) | Same node name. Older prerequisites are outside this match. |
| 25 | Terraformer Alpha | B3 | 500,000 | Grundlagen Terraformen (F) | The research node has the same name and no unit image. The XML match is plausible. A construction blueprint remains unverified. |
| 26 | Rosa-Plüschhasen-Spezialschiff | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. The export field `baubar=0` does not establish availability. |
| 27 | Lionheart | B1 | 15,000 | Raumschiffchassis (F) | Name match. A common version with the XML remains unverified. |
| 28 | Mule (Carrier) | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. |
| 29 | Nimbus BP-1729 | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. The export field `baubar=0` does not establish availability. |
| 30 | Sirius X300 | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. |
| 31 | Manta | B4 | 895,000 | Fast Tracking Systeme; Impuls Vektor Triebwerk; Pulslasertechnik; Shark (Jäger) (F+R) | Name match. A common version with the XML remains unverified. |
| 32 | Downbringer | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. |
| 33 | Nightcrawler | B5 | 450,000 | Tarnung ist alles (F) | Diagram: cloaked fighter. XML: fighter. |
| 34 | Stormbringer | B4 | 1,800,000 | Fusionstechnologie (F) | Name match. A common version with the XML remains unverified. |
| 35 | Nova | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. |
| 36 | Nepomuk | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. |
| 37 | Victim | B4 | 950,000 | Alpha Class Schilde; Fast Tracking Systeme; Vendeta (Zerstörer) (F+R) | Name match. A common version with the XML remains unverified. |
| 38 | Gatling | Open | Unverified | Cannot reliably reconstruct | B2 shows SDI Gatling under Gausskanone. Its match with the XML corvette Gatling is unverified. |
| 39 | Eraser | B5 | 650,000 | Tarnung ist alles (F) | Diagram: cloaked corvette. XML: corvettes. |
| 40 | Slayer | B1 | 50,000 | Raumschiffchassis (F) | Name match. A common version with the XML remains unverified. |
| 41 | Vendeta | B3 | 120,000 | energetische Panzerung; Hyperraumphotonenantrieb (F) | Vendeta is the spelling in both the XML and the diagram. |
| 42 | Crawler | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. |
| 43 | Widowmaker | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. |
| 44 | Hitman | B2 | 100,000 | große Schiffsrümpfe; chemischer Hyperraumantrieb (F) | Name match. A common version with the XML remains unverified. |
| 45 | Silent Sorrow | B5 | 2,400,000 | Tarnung von großen Sachen mittels einer Decke (F) | Diagram: SilentSorrow (cloaked cruiser). XML: Silent Sorrow. Spelling variant. The diagram has no ID. |
| 46 | Succubus | B4 | 1,700,000 | Alpha Class Schilde; Die Macht der Verführung (F) | Name match. A common version with the XML remains unverified. |
| 47 | Sirius XPi | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. |
| 48 | Big Daddy | B3 | 250,000 | sehr große Schiffsrümpfe; Hyperraumphotonenantrieb (F) | Name match. A common version with the XML remains unverified. |
| 49 | Kronk | B5 | 3,000,000 | Gravitonenstrahl (F) | Name match. A common version with the XML remains unverified. |
| 50 | Quasal | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. |
| 51 | Zeus | B5 | 4,000,000 | Größe macht doch was aus; Gravitonenstrahl (F) | Name match. A common version with the XML remains unverified. |
| 52 | Tempest | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. |
| 53 | X12 (Carrier) | B4 | 1,500,000 | Alpha Class Schilde; verbesserte Jägerstarttechnologie; Hyperraumphotonenantrieb (F+R) | Name match. A common version with the XML remains unverified. |
| 54 | Kampfbasis Alpha | B2 | 50,000 | orbitale Dockingsysteme; Ballistische Panzerung (F) | Name match. A common version with the XML remains unverified. |
| 55 | Robominer V1 | B3 | 75,000 | Asteroidenmining; Hyperraumphotonenantrieb (F) | Name match. A common version with the XML remains unverified. |
| 57 | Gargoil (Carrier) | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. |
| 58 | TAG Vario Kreuzer | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. |
| 59 | Kamel Z-98 (Hyperraumtransporter Klasse 1) | B4 | 699,800 | Fusionstechnologie; Hyperraumphotonenantrieb (F+R) | Name match. A common version with the XML remains unverified. |
| 60 | Waschbär (Hyperraumtransporter Klasse 2) | B4 | 785,000 | Fusionstechnologie; Hyperraumphotonenantrieb (F+R) | Name match. A common version with the XML remains unverified. |
| 61 | Kampfbasis Beta | B4 | 650,000 | Fusionstechnologie (F) | The name and version letter alone do not establish Kampfbasis Alpha as an extra direct prerequisite. |
| 63 | Weihnachtsmannschlitten (Transporter) | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. The export field `baubar=0` does not establish availability. |
| 64 | Sonde X13 | B5 | 1,000,000 | Bessere Informationsgewinnung (F) | The adjacent node Wir wollen aber nicht ausspioniert werden is another successor. The diagram does not show it as a prerequisite for the probe. |
| 67 | Kampfbasis Gamma | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. |
| 69 | Raketentransporter (mit Plutoniumdrachenantrieb) | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. The export field `baubar=0` does not establish availability. |
| 70 | Flughund (Hyperraumtransporter Klasse 1) | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. |
| 71 | Seepferdchen (Hyperraumtransporter Klasse 2) | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. |
| 72 | Artefaktsammelbasis Alpha | A2 / IW-06 | 150,000 | Suche nach neuen alten Sachen (F) | The name variant “Artefaktsammelbasis Alpha” appears in A2 only. |
| 73 | Artefaktsammelbasis Beta | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. |
| 76 | Settlers Delight | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. The export field `baubar=0` does not establish availability. |
| 77 | Kampfhuhn | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. The export field `baubar=0` does not establish availability. |
| 83 | Eichhörnchen | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. The export field `baubar=0` does not establish availability. |
| 84 | wilde Katze | Open | Unverified | Cannot reliably reconstruct | No reliable research match in B1–B5 or the other readable sources. The export field `baubar=0` does not establish availability. |

## Research Chains and Buildings

Selected upstream prerequisites. Building abbreviations are kept as shown in the diagrams.

| Source | Research node | Upstream research or extra field | Orange building prerequisite | Pictured unlock |
| --- | --- | --- | --- | --- |
| B1 | Chemischer Antrieb | Race Into Space; Verbrennungstechnik (R) | None marked | None |
| B1 | Raumfahrt | Chemischer Antrieb | None marked | Startrampe, Taverne, kl. Werft, Fl.scanner |
| B1 | Raumschiffchassis | Raumfahrt | None marked | None |
| B1 | Eistransport | Raumschiffchassis | None marked | None |
| B1 | Extraterrestrisches Siedeln | Several arrows, partial transcription | **Startrampe** | None |
| B1 | Kolonie auf felsigen Planeten | Extraterrestrisches Siedeln | No additional requirement marked | None |
| B2 | Große Schiffsrümpfe | Ballistische Panzerung; chemischer Hyperraumantrieb; Raumschiffchassis (R) | None marked | mit. plan. Werft |
| B2 | Kleine Lebenserhaltungssysteme | Große Schiffsrümpfe | None marked | None |
| B2 | Cryogenic | Aufnahme in die zivilisierten Welten | None marked | None |
| B2 | Aufnahme in die zivilisierten Welten | Antrag auf imperiale Besteuerung | **Imp. Steuerbehörde** | None |
| B3 | Sehr große Schiffsrümpfe | Zero G Forschung; große Rümpfe (R, abbreviated label) | None marked | gr. Werft |
| B3 | Grundlagen Terraformen | Genetik | None marked | None |
| B5 | Größe macht doch was aus | Wieso haben alle einen Partner nur ich nicht? | None marked | DN Werft, kl. Stahlkomplex |
| B5 | Verstehen der Zusammenhänge | Several arrows, partial transcription | **Quantencomputer** | None |
| A2 | Suche nach neuen alten Sachen | Eine kurze Abhandlung über den Nutzen… | **prähistorische Ausgrabung; große prähist. Ausgrabung; Artefakt-Ausgrabungszentrum** | None |

**Indirect building prerequisites.** In KISS-01 ← Kolonie auf felsigen Planeten ← Extraterrestrisches Siedeln, Startrampe is explicitly marked. Whether it remains necessary when building each ship later is unverified. Similarly, Imp. Steuerbehörde is indirectly upstream of Cryogenic and its associated units.

**Shipyard unlocks and construction prerequisites.** Zeus has Größe macht doch was aus as a predecessor. The predecessor shows a DN Werft. The exact shipyard or level for building Zeus remains unverified.

## Separate Findings from the Older Series A

Keep these findings separate from the B matrix. The selection covers only legible local relationships.

| XML name match | Source | Legible finding | Evidence limits |
| --- | --- | --- | --- |
| Sonde X11 (13) | A1 | Visuelle Optik → Sonde X11 | Same local relationship as B1. |
| Scout (8), Systrans (9), Slayer (40), Lionheart (27) | A1 | Raumschiffchassis → each unit's node | A global tier order by ID remains unverified. |
| Terraformer Alpha (25), tentative | A1 | Grundlagen Terraformen → Terraformer Alpha. Grundlagen Terraformen lists Kontrollzentrum m. Startr. in orange. | A1 gives 30,000 FP for Grundlagen Terraformen. B3 gives 500,000 FP and different predecessors. The building field applies to A1. |
| Artefaktsammelbasis Alpha (72), name variant | A2 | Artefaktsammelbasis Alpha node, 150,000 FP, with an incoming link from Suche nach neuen alten Sachen | The diagram lacks the XML ID and does not independently establish all construction prerequisites. |
| Sheep (22), Hitman (44) | A2 | Unit nodes are visible, with references to Shark (Evo 3) and Succubus (Evo 4) | Prerequisites on the blurry later pages remain unverified. |
| Atombomber, XML ID uncertain | A2 | A 40,000 FP node is visible. | A match with bürokratischer Formularbomber remains unverified. |

## Naming Conflicts and Additional Diagram Entries

| Diagram finding | XML relationship | Treatment |
| --- | --- | --- |
| B2/A2: Atombomber (40,000 FP) | No XML entry with that name. ID 21 is bürokratischer Formularbomber. | Retain the additional historical name. Identity, costs, and availability remain unverified. B2 shows at least kleine Lebenserhaltungssysteme as a predecessor. Full prerequisites are unreconstructed. |
| B2: SDI Gatling unlocked by Gausskanone | ID 38 is Gatling, in corvettes. | A match remains unverified. |
| B3: Robominer V1 | ID 55, Robominer V1 | Mining buildings under other nodes do not establish construction prerequisites for the ship. |
| B4: X12 (Carrier) | ID 53, XML category cruisers | Retain both descriptions and the source category. |
| B5: SilentSorrow (cloaked cruiser) | ID 45, Silent Sorrow | Plausible spelling or role variant. The diagram has no ID. |
| B3/A1: Terraformer Alpha | ID 25, Terraformer Alpha | The names match. The research node alone leaves the unit unlock uncertain. |

## Costs and Historical Availability

- B1 lists 1,500 steel for Systrans and IW-03 lists 1,000. B1 lists 15 steel for Sonde X11 and the XML lists 10. Many values appear to differ by a factor of about 1.5. Rounding, modifiers, or version differences remain unclear, so there is no established conversion rule.
- The XML supplies full material costs. Diagrams supply research points and requirements. A game configuration in which both applied is unverified.
- `baubar=1` is an export field. Research nodes and `baubar` do not establish universal unlocks or account requirements. Event, NPC, or bonus roles for life forms and zero-cost or `baubar=0` entries remain unverified.
- Weapon stats, speed, cargo, tank capacity, crew semantics, required shipyard levels, and upgradeability remain unverified.
- Missing matches mostly concern later, special, or differently named entries. These units may still have existed historically.

## Findings and Remaining Gaps

- All 64 XML IDs occur exactly once in the matrix. 38 have at least one name-based diagram link, and 26 lack reliable mappings. Caveats to the name matches are recorded.
- Incoming arrows and extra fields were visually traced for all 37 series B units. The community diagrams remain independently unverified against original game code.
- The transitive research list for each ship remains incomplete. Selected upstream shipyard and colonization paths and explicit building fields are documented.
- Series B ends with Evo 5. Series A lacks Evo 6 and legible details on later pages. Better originals or additional primary data could clarify remaining units and requirements.
- The original sources are still awaiting archive copies in the repository. Links and checksums are recorded.
