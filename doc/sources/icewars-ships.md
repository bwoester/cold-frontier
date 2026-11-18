---
status: research
type: source-analysis
---
# IceWars – Reconstructed Unit Catalog

**Status as of 2026-11-01.** Historical XML data for inspiration. Cold Frontier rules are decided separately.

**Research and buildings.** The [research mapping](icewars-ship-research.md) adds documented diagram matches or an explicit unknown status for all 64 IDs. XML costs and diagram prerequisites are from separate sources.

## Sources and Evidence Limits

- **IW-01:** [Resource Definitions XML](https://drive.google.com/file/d/0B_fvsE93Ci4daTh6cGsxanI1WkE/view) maps resource IDs to names.
- **IW-03:** [Ship Costs XML](https://drive.google.com/file/d/0B_fvsE93Ci4dNXJwbzZhTzRZTkk/view) contains 64 entries in eleven categories, with an internal update timestamp of 2014-05-03 22:00:01 UTC.
- The project owner identifies these as official sources. The original export endpoint, round, and any account context remain unknown.
- The list covers the available export. Units from other IceWars versions may be missing. Life forms retain their own category.
- Names and categories are preserved verbatim, including “Vendeta”, carrier assignments, and names that differ from the Tech Trees.
- Duration and `baubar` are raw fields. The duration unit and whether `baubar` is global or account-specific remain unknown. A value of 0 does not establish an event ship, an NPC ship, or an unresearched ship.
- The XML lacks combat stats, transport capacity, research dependencies, and roles beyond names. The [separate research reconstruction](icewars-ship-research.md) identifies selected prerequisites from diagrams. Costs alone cannot establish tiers.
- See the [source catalog and progress](icewars-catalog.md). The XML originals still need archiving in the repository.

### Identification of Reviewed Files

| Source | Bytes | SHA-256 of retrieved original bytes |
| --- | ---: | --- |
| IW-01 | 819 | `e9716e8f4bc9f2d40ff64ac69d70c945f3177cff151ff047fd143f8006a9d448` |
| IW-03 | 61004 | `9b6c6bfc309c6a5a44bb61c553c231267bd9f98ae9cf31b141e0e432a10bdbe7` |

## Resource Mapping

| ID | XML name | English name |
| ---: | --- | --- |
| 1 | Eisen | Iron |
| 2 | Stahl | Steel |
| 3 | VV4A | VV4A |
| 4 | Eis | Ice |
| 5 | chem. Elemente | Chemical elements |
| 6 | Wasser | Water |
| 7 | Energie | Energy |
| 8 | Pinguine | Penguins |
| 10 | Credits | Credits |
| 11 | Bevölkerung | Population |

## Units by XML Category

Each category is sorted by numeric ID. Tiers remain unverified. Costs list all nonzero values, and unlisted resources are zero. Population and credits retain their raw costs. Consumption or reservation semantics remain unknown. Penguins cost zero in all 64 entries. Zero costs with `baubar=0` do not establish free ordinary construction.

### Life Forms (2)

XML category: `Lebensformen`.

| ID | XML name | Duration (raw) | `baubar` | Nonzero costs |
| ---: | --- | ---: | ---: | --- |
| 83 | Eichhörnchen | 360 | 0 | Iron: 10; Steel: 10; VV4A: 5; Chemical elements: 5; Water: 2; Energy: 5 |
| 84 | wilde Katze | 360 | 0 | Iron: 10; Steel: 10; VV4A: 5; Chemical elements: 5; Water: 2; Energy: 5 |

### Probes (3)

XML category: `Sonden`.

| ID | XML name | Duration (raw) | `baubar` | Nonzero costs |
| ---: | --- | ---: | ---: | --- |
| 13 | Sonde X11 | 360 | 1 | Steel: 10; Chemical elements: 25; Energy: 40 |
| 24 | Terminus Sonde | 720 | 1 | Steel: 16; Ice: 10; Chemical elements: 50; Energy: 40; Population: 1 |
| 64 | Sonde X13 | 900 | 1 | Steel: 25; VV4A: 5; Chemical elements: 100; Energy: 100 |

### Civilian Ships (22)

XML category: `Zivile Schiffe`.

| ID | XML name | Duration (raw) | `baubar` | Nonzero costs |
| ---: | --- | ---: | ---: | --- |
| 9 | Systrans (Systemtransporter Klasse 1) | 10800 | 1 | Steel: 1,000; Chemical elements: 500; Energy: 500; Population: 15 |
| 10 | Crux (Systemtransporter Kolonisten) | 21600 | 1 | Steel: 1,200; Chemical elements: 500; Water: 150; Energy: 750; Credits: 50; Population: 15 |
| 11 | Lurch (Systemtransporter Klasse 2) | 21600 | 1 | Steel: 1,250; Ice: 50; Chemical elements: 300; Water: 50; Energy: 800; Credits: 75; Population: 15 |
| 12 | KISS-01 (Systemkolonieschiff) | 172800 | 1 | Iron: 30,000; Steel: 15,000; Ice: 1,000; Chemical elements: 15,000; Water: 1,000; Energy: 25,000; Credits: 5,000; Population: 50 |
| 15 | Gorgol 9 (Hyperraumtransporter Klasse 1) | 36000 | 1 | Steel: 2,300; Chemical elements: 750; Energy: 2,000; Population: 5 |
| 16 | Kolpor (Hyperraumtransporter Kolonisten) | 28800 | 1 | Steel: 2,000; Chemical elements: 500; Energy: 2,000; Population: 5 |
| 17 | Eisbär (Hyperraumtransporter Klasse 2) | 28800 | 1 | Steel: 2,500; Ice: 100; Chemical elements: 1,250; Water: 100; Energy: 1,250; Population: 5 |
| 18 | INS-03A (interstellares Kolonieschiff) | 172800 | 1 | Iron: 100,000; Steel: 25,000; VV4A: 10,000; Ice: 5,000; Chemical elements: 25,000; Water: 5,000; Energy: 30,000; Credits: 10,000; Population: 50 |
| 25 | Terraformer Alpha | 18000 | 1 | Steel: 1,500; Ice: 100; Chemical elements: 1,500; Water: 100; Energy: 1,500; Credits: 1,500 |
| 54 | Kampfbasis Alpha | 28800 | 1 | Iron: 10,000; Steel: 7,500; VV4A: 750; Chemical elements: 5,900; Energy: 5,000; Population: 150 |
| 55 | Robominer V1 | 36000 | 1 | Iron: 10,000; Steel: 7,500; VV4A: 750; Chemical elements: 5,900; Energy: 10,000; Population: 150 |
| 59 | Kamel Z-98 (Hyperraumtransporter Klasse 1) | 43200 | 1 | Steel: 6,500; VV4A: 1,200; Chemical elements: 3,550; Energy: 5,000; Population: 25 |
| 60 | Waschbär (Hyperraumtransporter Klasse 2) | 43200 | 1 | Steel: 6,000; VV4A: 1,000; Ice: 100; Chemical elements: 6,000; Water: 100; Energy: 3,000; Population: 25 |
| 61 | Kampfbasis Beta | 43200 | 1 | Iron: 50,000; Steel: 45,500; VV4A: 5,750; Chemical elements: 30,000; Energy: 25,000; Population: 300 |
| 63 | Weihnachtsmannschlitten (Transporter) | 0 | 0 | All resources: 0 |
| 67 | Kampfbasis Gamma | 57600 | 1 | Iron: 190,000; Steel: 176,000; VV4A: 65,000; Chemical elements: 210,000; Energy: 200,000; Population: 900 |
| 69 | Raketentransporter (mit Plutoniumdrachenantrieb) | 0 | 0 | All resources: 0 |
| 70 | Flughund (Hyperraumtransporter Klasse 1) | 86400 | 1 | Iron: 85,000; Steel: 50,000; VV4A: 15,000; Chemical elements: 30,000; Energy: 50,000; Population: 120 |
| 71 | Seepferdchen (Hyperraumtransporter Klasse 2) | 86400 | 1 | Iron: 75,000; Steel: 50,000; VV4A: 7,500; Ice: 1,500; Chemical elements: 50,000; Water: 1,500; Energy: 75,000; Population: 50 |
| 72 | Artefaktsammelbasis Alpha | 86400 | 1 | Iron: 25,000; Steel: 20,500; VV4A: 1,000; Chemical elements: 15,000; Energy: 25,000; Population: 500 |
| 73 | Artefaktsammelbasis Beta | 172800 | 1 | Iron: 150,000; Steel: 175,000; VV4A: 28,000; Chemical elements: 90,000; Energy: 100,000; Population: 500 |
| 76 | Settlers Delight | 0 | 0 | All resources: 0 |

### Fighters (6)

XML category: `Jäger`.

| ID | XML name | Duration (raw) | `baubar` | Nonzero costs |
| ---: | --- | ---: | ---: | --- |
| 22 | Sheep | 2880 | 1 | Iron: 100; Steel: 400; VV4A: 50; Chemical elements: 450; Energy: 600; Population: 1 |
| 23 | Shark | 3600 | 1 | Iron: 150; Steel: 500; VV4A: 150; Chemical elements: 550; Energy: 800; Population: 1 |
| 30 | Sirius X300 | 4680 | 1 | Iron: 500; Steel: 1,500; VV4A: 250; Chemical elements: 700; Energy: 1,500; Population: 1 |
| 31 | Manta | 4320 | 1 | Iron: 300; Steel: 700; VV4A: 250; Chemical elements: 700; Energy: 1,100; Population: 1 |
| 32 | Downbringer | 5040 | 1 | Iron: 450; Steel: 1,200; VV4A: 750; Chemical elements: 800; Energy: 1,300; Population: 2 |
| 33 | Nightcrawler | 4320 | 1 | Iron: 1,500; Steel: 1,300; VV4A: 250; Chemical elements: 1,000; Energy: 1,000; Population: 1 |

### Bombers (4)

XML category: `Bomber`.

| ID | XML name | Duration (raw) | `baubar` | Nonzero costs |
| ---: | --- | ---: | ---: | --- |
| 21 | bürokratischer Formularbomber | 7200 | 1 | Iron: 1,000; Steel: 1,750; VV4A: 650; Chemical elements: 750; Energy: 500; Population: 1 |
| 34 | Stormbringer | 10800 | 1 | Iron: 2,000; Steel: 2,100; VV4A: 1,200; Chemical elements: 1,000; Energy: 1,000; Population: 2 |
| 35 | Nova | 12600 | 1 | Iron: 3,000; Steel: 2,800; VV4A: 1,800; Chemical elements: 1,500; Energy: 1,500; Population: 3 |
| 36 | Nepomuk | 14400 | 1 | Iron: 4,000; Steel: 1,000; VV4A: 3,250; Chemical elements: 3,500; Energy: 4,000; Population: 5 |

### Corvettes (5)

XML category: `Korvetten`.

| ID | XML name | Duration (raw) | `baubar` | Nonzero costs |
| ---: | --- | ---: | ---: | --- |
| 14 | Hunter | 10800 | 1 | Steel: 1,850; VV4A: 500; Chemical elements: 1,000; Energy: 1,000; Population: 25 |
| 27 | Lionheart | 9000 | 1 | Steel: 1,550; Chemical elements: 850; Energy: 800; Population: 10 |
| 37 | Victim | 10800 | 1 | Steel: 3,100; VV4A: 750; Chemical elements: 2,100; Energy: 1,500; Population: 30 |
| 38 | Gatling | 14400 | 1 | Steel: 3,400; VV4A: 850; Chemical elements: 2,500; Energy: 2,000; Population: 30 |
| 39 | Eraser | 14400 | 1 | Steel: 2,300; VV4A: 870; Chemical elements: 4,000; Energy: 3,150; Population: 25 |

### Destroyers (5)

XML category: `Zerstörer`.

| ID | XML name | Duration (raw) | `baubar` | Nonzero costs |
| ---: | --- | ---: | ---: | --- |
| 20 | Pflaumenmus (Carrier) | 64800 | 1 | Iron: 10,000; Steel: 5,000; Chemical elements: 5,000; Energy: 15,000; Credits: 1,000; Population: 50 |
| 40 | Slayer | 18000 | 1 | Steel: 2,000; Chemical elements: 900; Energy: 2,000; Population: 35 |
| 41 | Vendeta | 21600 | 1 | Steel: 3,000; VV4A: 900; Chemical elements: 1,000; Energy: 2,800; Population: 50 |
| 42 | Crawler | 30600 | 1 | Steel: 6,200; VV4A: 2,600; Ice: 50; Chemical elements: 4,500; Energy: 2,000; Credits: 500; Population: 75 |
| 43 | Widowmaker | 28800 | 1 | Steel: 3,600; VV4A: 1,000; Chemical elements: 3,000; Energy: 3,350; Population: 80 |

### Cruisers (6)

XML category: `Kreuzer`.

| ID | XML name | Duration (raw) | `baubar` | Nonzero costs |
| ---: | --- | ---: | ---: | --- |
| 44 | Hitman | 43200 | 1 | Iron: 4,000; Steel: 5,000; VV4A: 1,500; Chemical elements: 2,000; Energy: 4,000; Population: 50 |
| 45 | Silent Sorrow | 68400 | 1 | Iron: 5,000; Steel: 4,500; VV4A: 850; Chemical elements: 27,500; Energy: 40,000; Population: 65 |
| 46 | Succubus | 54000 | 1 | Iron: 9,000; Steel: 15,500; VV4A: 6,550; Chemical elements: 12,000; Energy: 10,000; Population: 100 |
| 47 | Sirius XPi | 72000 | 1 | Iron: 15,000; Steel: 19,250; VV4A: 6,150; Chemical elements: 12,300; Energy: 9,500; Population: 80 |
| 53 | X12 (Carrier) | 86400 | 1 | Iron: 20,000; Steel: 5,800; VV4A: 4,565; Chemical elements: 8,000; Energy: 35,000; Credits: 1,500; Population: 150 |
| 58 | TAG Vario Kreuzer | 64800 | 1 | Iron: 15,000; Steel: 17,000; VV4A: 5,890; Chemical elements: 11,000; Energy: 12,000; Population: 75 |

### Battleships (4)

XML category: `Schlachtschiffe`.

| ID | XML name | Duration (raw) | `baubar` | Nonzero costs |
| ---: | --- | ---: | ---: | --- |
| 28 | Mule (Carrier) | 100800 | 1 | Iron: 35,000; Steel: 18,000; VV4A: 9,000; Chemical elements: 12,000; Energy: 40,000; Credits: 2,000; Population: 250 |
| 48 | Big Daddy | 72000 | 1 | Iron: 8,000; Steel: 10,000; VV4A: 4,900; Chemical elements: 7,500; Energy: 12,000; Population: 160 |
| 49 | Kronk | 86400 | 1 | Iron: 20,000; Steel: 20,500; VV4A: 9,150; Chemical elements: 19,900; Energy: 29,850; Population: 300 |
| 50 | Quasal | 108000 | 1 | Iron: 20,000; Steel: 25,500; VV4A: 10,500; Chemical elements: 20,400; Energy: 15,000; Population: 310 |

### Dreadnoughts (5)

| ID | XML name | Duration (raw) | `baubar` | Nonzero costs |
| ---: | --- | ---: | ---: | --- |
| 26 | Rosa-Plüschhasen-Spezialschiff | 0 | 0 | All resources: 0 |
| 29 | Nimbus BP-1729 | 0 | 0 | All resources: 0 |
| 51 | Zeus | 126000 | 1 | Iron: 25,000; Steel: 40,000; VV4A: 15,000; Chemical elements: 25,000; Energy: 40,000; Population: 500 |
| 52 | Tempest | 162000 | 1 | Iron: 50,000; Steel: 65,000; VV4A: 22,000; Chemical elements: 35,000; Energy: 59,000; Population: 750 |
| 57 | Gargoil (Carrier) | 172800 | 1 | Iron: 55,000; Steel: 75,000; VV4A: 27,500; Chemical elements: 45,000; Energy: 50,000; Credits: 2,000; Population: 450 |

### Special Ships (2)

XML category: `Spezielle Schiffe`.

| ID | XML name | Duration (raw) | `baubar` | Nonzero costs |
| ---: | --- | ---: | ---: | --- |
| 8 | Scout | 5040 | 1 | Iron: 100; Steel: 250; Chemical elements: 250; Energy: 500; Population: 5 |
| 77 | Kampfhuhn | 0 | 0 | All resources: 0 |

## Testing the Hypothesis That Higher IDs Mean Higher Tiers

**Result.** IDs are unreliable as a general ordering rule even within a category. Some named families show plausible progression. ID, cost, and duration do not establish unlock requirements.

| Comparison | XML observation | Interpretation |
| --- | --- | --- |
| Probes 13 → 24 → 64 | Duration 360 → 720 → 900. VV4A first appears at 64, and chemical elements run 25 → 50 → 100. | Consistent with progression. Exact unlocks unproven. |
| Corvettes 14 Hunter → 27 Lionheart | Steel 1,850 → 1,550, VV4A 500 → 0, and duration 10,800 → 9,000. | Counterexample to monotonically increasing material or build complexity by ID. |
| Fighters 30 Sirius X300 → 31 Manta | Steel 1,500 → 700, iron 500 → 300, energy 1,500 → 1,100. VV4A and chemical elements are equal, and duration falls from 4,680 to 4,320. | ID 31 is no more expensive in any field, cheaper in several, and faster to build. Actual tiers remain unverified. |
| Fighters 32 Downbringer → 33 Nightcrawler | VV4A 750 → 250 and energy 1,300 → 1,000, while iron and chemical elements rise. | Different cost profiles do not form a simple resource ladder. |
| Destroyers 20 Pflaumenmus (Carrier) → 40 Slayer | The carrier has much higher costs and a duration of 64,800 versus 18,000. | The XML category includes different named roles. |
| Cruisers 44 Hitman → 45 Silent Sorrow | VV4A 1,500 → 850 and steel 5,000 → 4,500. Chemical elements 2,000 → 27,500 and energy 4,000 → 40,000. | Lower VV4A does not imply lower overall cost. A one-dimensional tier score lacks a basis. |
| Bombers 34 Stormbringer → 35 Nova → 36 Nepomuk | VV4A 1,200 → 1,800 → 3,250 and duration 10,800 → 12,600 → 14,400. Steel finally falls from 2,800 to 1,000. | Broad progression is plausible, with some nonmonotonic costs. |
| Kampfbasis 54 Alpha → 61 Beta → 67 Gamma | All used cost fields and duration increase. | Plausible family progression within the civilian category. |
| Class 1 hyperspace transports 15 Gorgol 9 → 59 Kamel Z-98 → 70 Flughund | Steel 2,300 → 6,500 → 50,000, VV4A 0 → 1,200 → 15,000, and duration 36,000 → 43,200 → 86,400. | Plausible family progression. Capacity and performance are absent from the XML. |

### Cross-Check with Community Diagrams

[IW-11, Evo 1](https://drive.google.com/file/d/1yt-JLQm8b-CSgF6wfcE693S_PHFz6i_q/view) shows Lionheart and [IW-13, Evo 3](https://drive.google.com/file/d/1S-C77ux_m7YuEm88GJ6I2x7VAx5zSrgV/view) shows Hunter. Matched by name to the XML, this weighs against the assumed unlock order by ID (Hunter 14, Lionheart 27). A common version has yet to be established, so diagram costs stay separate from the XML table.

### Next Steps

- Keep IDs as identities. Database insertion order or later additions may explain ID patterns, but those explanations are unverified.
- Identify possible families by name and cost profile. A category can contain several progression paths.
- Name-based [research and Evo mappings](icewars-ship-research.md) exist for 38 entries. 26 remain open. Keep source versions, direct research, indirect building requirements, and unknown construction requirements distinct. Complete transitive research chains remain unverified.
- Avoid totaling iron, energy, credits, and population without weights. Resource progression and economic value are separate measures and are still incompletely documented.

## Extraction Checks

- 64 unique unit IDs, each present once across the category tables.
- Cost references resolve to the ten resources from IW-01, with no unknown IDs.
- Every entry contains all ten cost fields. All nonzero values are included, and names, categories, duration, and `baubar` retain the XML data.
- 56 entries have `baubar=1` and eight have `baubar=0`. All categories are included, including life forms and special entries.
- Durations remain raw and categories follow the source. Tier assignments are not inferred.
