---
status: research
type: source-analysis
---
# IceWars – rekonstruierter Einheitenkatalog

**Stand:** 2026-11-01. Historische XML-Daten, keine Cold-Frontier-Spezifikation. Für Cold Frontier ausschließlich Inspirationsmaterial.

**Forschungen und Gebäude:** Die [Forschungszuordnung](icewars-ship-research.md) ergänzt für jede der 64 IDs entweder belegbare Grafikbezüge oder einen ausdrücklichen Unbekannt-Status. XML-Kosten und Grafik-Voraussetzungen bleiben unterschiedliche Quellenstände.

## Quellen und Aussagegrenzen

- **IW-01:** [ressourcen.xml](https://drive.google.com/file/d/0B_fvsE93Ci4daTh6cGsxanI1WkE/view) – Zuordnung von Ressourcen-IDs zu Namen.
- **IW-03:** [schiffkosten.xml](https://drive.google.com/file/d/0B_fvsE93Ci4dNXJwbzZhTzRZTkk/view) – 64 Einträge in elf Kategorien; interner Aktualisierungszeitpunkt 2014-05-03 22:00:01 UTC.
- Offizielle Herkunft laut Projektinhaber; ursprünglicher Export-Endpunkt, Spielrunde und eventueller Accountkontext sind nicht geklärt.
- Die Liste deckt den vorhandenen Export ab, nicht nachweislich alle Einheiten aller IceWars-Versionen. „Lebensformen“ sind im Export enthalten und werden nicht als Raumschiffe umgedeutet.
- Namen und Kategorien bleiben wörtlich erhalten, auch „Vendeta“, Carrier-Zuordnungen und die abweichenden Bezeichnungen aus Tech Trees.
- Dauer und baubar sind Rohwerte: Die Zeiteinheit und die globale oder accountbezogene Bedeutung von baubar sind nicht belegt. baubar=0 bedeutet insbesondere nicht automatisch Event-, NPC- oder unerforschtes Schiff.
- Im XML fehlen Kampfwerte, Transportkapazität, Forschungsabhängigkeiten und Rollen über die Benennung hinaus. Die [separate Forschungsrekonstruktion](icewars-ship-research.md) erschließt ausgewählte Voraussetzungen aus den Grafiken. Keine Tier-Zuordnung aus Kosten allein ableiten.
- [Quellenkatalog und Bearbeitungsstand](icewars-catalog.md). Die XML-Originale sind noch nicht im Repo archiviert.

### Identifikation der ausgewerteten Dateien

| Quelle | Bytes | SHA-256 der abgerufenen Originalbytes |
| --- | ---: | --- |
| IW-01 | 819 | `e9716e8f4bc9f2d40ff64ac69d70c945f3177cff151ff047fd143f8006a9d448` |
| IW-03 | 61004 | `9b6c6bfc309c6a5a44bb61c553c231267bd9f98ae9cf31b141e0e432a10bdbe7` |

## Ressourcen-Zuordnung

| ID | Name im XML |
| ---: | --- |
| 1 | Eisen |
| 2 | Stahl |
| 3 | VV4A |
| 4 | Eis |
| 5 | chem. Elemente |
| 6 | Wasser |
| 7 | Energie |
| 8 | Pinguine |
| 10 | Credits |
| 11 | Bevölkerung |

## Einheiten nach XML-Kategorie

Innerhalb jeder Kategorie numerisch nach ID sortiert, **nicht nach bestätigtem Tier**. Kosten nennen sämtliche Nichtnull-Werte; nicht genannte Ressourcen haben im XML den Wert 0. Bevölkerung und Credits werden unverändert als Kostenfelder dargestellt; Verbrauchs-/Reservierungssemantik ist nicht belegt. Pinguine sind bei allen 64 Einträgen 0. Nullkosten bei baubar=0 belegen keine kostenlose reguläre Herstellung.

### Lebensformen (2)

| ID | Name | Dauer (Rohwert) | baubar | Kosten (Nichtnull-Werte) |
| ---: | --- | ---: | ---: | --- |
| 83 | Eichhörnchen | 360 | 0 | Eisen: 10; Stahl: 10; VV4A: 5; chem. Elemente: 5; Wasser: 2; Energie: 5 |
| 84 | wilde Katze | 360 | 0 | Eisen: 10; Stahl: 10; VV4A: 5; chem. Elemente: 5; Wasser: 2; Energie: 5 |

### Sonden (3)

| ID | Name | Dauer (Rohwert) | baubar | Kosten (Nichtnull-Werte) |
| ---: | --- | ---: | ---: | --- |
| 13 | Sonde X11 | 360 | 1 | Stahl: 10; chem. Elemente: 25; Energie: 40 |
| 24 | Terminus Sonde | 720 | 1 | Stahl: 16; Eis: 10; chem. Elemente: 50; Energie: 40; Bevölkerung: 1 |
| 64 | Sonde X13 | 900 | 1 | Stahl: 25; VV4A: 5; chem. Elemente: 100; Energie: 100 |

### Zivile Schiffe (22)

| ID | Name | Dauer (Rohwert) | baubar | Kosten (Nichtnull-Werte) |
| ---: | --- | ---: | ---: | --- |
| 9 | Systrans (Systemtransporter Klasse 1) | 10800 | 1 | Stahl: 1.000; chem. Elemente: 500; Energie: 500; Bevölkerung: 15 |
| 10 | Crux (Systemtransporter Kolonisten) | 21600 | 1 | Stahl: 1.200; chem. Elemente: 500; Wasser: 150; Energie: 750; Credits: 50; Bevölkerung: 15 |
| 11 | Lurch (Systemtransporter Klasse 2) | 21600 | 1 | Stahl: 1.250; Eis: 50; chem. Elemente: 300; Wasser: 50; Energie: 800; Credits: 75; Bevölkerung: 15 |
| 12 | KISS-01 (Systemkolonieschiff) | 172800 | 1 | Eisen: 30.000; Stahl: 15.000; Eis: 1.000; chem. Elemente: 15.000; Wasser: 1.000; Energie: 25.000; Credits: 5.000; Bevölkerung: 50 |
| 15 | Gorgol 9 (Hyperraumtransporter Klasse 1) | 36000 | 1 | Stahl: 2.300; chem. Elemente: 750; Energie: 2.000; Bevölkerung: 5 |
| 16 | Kolpor (Hyperraumtransporter Kolonisten) | 28800 | 1 | Stahl: 2.000; chem. Elemente: 500; Energie: 2.000; Bevölkerung: 5 |
| 17 | Eisbär (Hyperraumtransporter Klasse 2) | 28800 | 1 | Stahl: 2.500; Eis: 100; chem. Elemente: 1.250; Wasser: 100; Energie: 1.250; Bevölkerung: 5 |
| 18 | INS-03A (interstellares Kolonieschiff) | 172800 | 1 | Eisen: 100.000; Stahl: 25.000; VV4A: 10.000; Eis: 5.000; chem. Elemente: 25.000; Wasser: 5.000; Energie: 30.000; Credits: 10.000; Bevölkerung: 50 |
| 25 | Terraformer Alpha | 18000 | 1 | Stahl: 1.500; Eis: 100; chem. Elemente: 1.500; Wasser: 100; Energie: 1.500; Credits: 1.500 |
| 54 | Kampfbasis Alpha | 28800 | 1 | Eisen: 10.000; Stahl: 7.500; VV4A: 750; chem. Elemente: 5.900; Energie: 5.000; Bevölkerung: 150 |
| 55 | Robominer V1 | 36000 | 1 | Eisen: 10.000; Stahl: 7.500; VV4A: 750; chem. Elemente: 5.900; Energie: 10.000; Bevölkerung: 150 |
| 59 | Kamel Z-98 (Hyperraumtransporter Klasse 1) | 43200 | 1 | Stahl: 6.500; VV4A: 1.200; chem. Elemente: 3.550; Energie: 5.000; Bevölkerung: 25 |
| 60 | Waschbär (Hyperraumtransporter Klasse 2) | 43200 | 1 | Stahl: 6.000; VV4A: 1.000; Eis: 100; chem. Elemente: 6.000; Wasser: 100; Energie: 3.000; Bevölkerung: 25 |
| 61 | Kampfbasis Beta | 43200 | 1 | Eisen: 50.000; Stahl: 45.500; VV4A: 5.750; chem. Elemente: 30.000; Energie: 25.000; Bevölkerung: 300 |
| 63 | Weihnachtsmannschlitten (Transporter) | 0 | 0 | Alle Ressourcen: 0 |
| 67 | Kampfbasis Gamma | 57600 | 1 | Eisen: 190.000; Stahl: 176.000; VV4A: 65.000; chem. Elemente: 210.000; Energie: 200.000; Bevölkerung: 900 |
| 69 | Raketentransporter (mit Plutoniumdrachenantrieb) | 0 | 0 | Alle Ressourcen: 0 |
| 70 | Flughund (Hyperraumtransporter Klasse 1) | 86400 | 1 | Eisen: 85.000; Stahl: 50.000; VV4A: 15.000; chem. Elemente: 30.000; Energie: 50.000; Bevölkerung: 120 |
| 71 | Seepferdchen (Hyperraumtransporter Klasse 2) | 86400 | 1 | Eisen: 75.000; Stahl: 50.000; VV4A: 7.500; Eis: 1.500; chem. Elemente: 50.000; Wasser: 1.500; Energie: 75.000; Bevölkerung: 50 |
| 72 | Artefaktsammelbasis Alpha | 86400 | 1 | Eisen: 25.000; Stahl: 20.500; VV4A: 1.000; chem. Elemente: 15.000; Energie: 25.000; Bevölkerung: 500 |
| 73 | Artefaktsammelbasis Beta | 172800 | 1 | Eisen: 150.000; Stahl: 175.000; VV4A: 28.000; chem. Elemente: 90.000; Energie: 100.000; Bevölkerung: 500 |
| 76 | Settlers Delight | 0 | 0 | Alle Ressourcen: 0 |

### Jäger (6)

| ID | Name | Dauer (Rohwert) | baubar | Kosten (Nichtnull-Werte) |
| ---: | --- | ---: | ---: | --- |
| 22 | Sheep | 2880 | 1 | Eisen: 100; Stahl: 400; VV4A: 50; chem. Elemente: 450; Energie: 600; Bevölkerung: 1 |
| 23 | Shark | 3600 | 1 | Eisen: 150; Stahl: 500; VV4A: 150; chem. Elemente: 550; Energie: 800; Bevölkerung: 1 |
| 30 | Sirius X300 | 4680 | 1 | Eisen: 500; Stahl: 1.500; VV4A: 250; chem. Elemente: 700; Energie: 1.500; Bevölkerung: 1 |
| 31 | Manta | 4320 | 1 | Eisen: 300; Stahl: 700; VV4A: 250; chem. Elemente: 700; Energie: 1.100; Bevölkerung: 1 |
| 32 | Downbringer | 5040 | 1 | Eisen: 450; Stahl: 1.200; VV4A: 750; chem. Elemente: 800; Energie: 1.300; Bevölkerung: 2 |
| 33 | Nightcrawler | 4320 | 1 | Eisen: 1.500; Stahl: 1.300; VV4A: 250; chem. Elemente: 1.000; Energie: 1.000; Bevölkerung: 1 |

### Bomber (4)

| ID | Name | Dauer (Rohwert) | baubar | Kosten (Nichtnull-Werte) |
| ---: | --- | ---: | ---: | --- |
| 21 | bürokratischer Formularbomber | 7200 | 1 | Eisen: 1.000; Stahl: 1.750; VV4A: 650; chem. Elemente: 750; Energie: 500; Bevölkerung: 1 |
| 34 | Stormbringer | 10800 | 1 | Eisen: 2.000; Stahl: 2.100; VV4A: 1.200; chem. Elemente: 1.000; Energie: 1.000; Bevölkerung: 2 |
| 35 | Nova | 12600 | 1 | Eisen: 3.000; Stahl: 2.800; VV4A: 1.800; chem. Elemente: 1.500; Energie: 1.500; Bevölkerung: 3 |
| 36 | Nepomuk | 14400 | 1 | Eisen: 4.000; Stahl: 1.000; VV4A: 3.250; chem. Elemente: 3.500; Energie: 4.000; Bevölkerung: 5 |

### Korvetten (5)

| ID | Name | Dauer (Rohwert) | baubar | Kosten (Nichtnull-Werte) |
| ---: | --- | ---: | ---: | --- |
| 14 | Hunter | 10800 | 1 | Stahl: 1.850; VV4A: 500; chem. Elemente: 1.000; Energie: 1.000; Bevölkerung: 25 |
| 27 | Lionheart | 9000 | 1 | Stahl: 1.550; chem. Elemente: 850; Energie: 800; Bevölkerung: 10 |
| 37 | Victim | 10800 | 1 | Stahl: 3.100; VV4A: 750; chem. Elemente: 2.100; Energie: 1.500; Bevölkerung: 30 |
| 38 | Gatling | 14400 | 1 | Stahl: 3.400; VV4A: 850; chem. Elemente: 2.500; Energie: 2.000; Bevölkerung: 30 |
| 39 | Eraser | 14400 | 1 | Stahl: 2.300; VV4A: 870; chem. Elemente: 4.000; Energie: 3.150; Bevölkerung: 25 |

### Zerstörer (5)

| ID | Name | Dauer (Rohwert) | baubar | Kosten (Nichtnull-Werte) |
| ---: | --- | ---: | ---: | --- |
| 20 | Pflaumenmus (Carrier) | 64800 | 1 | Eisen: 10.000; Stahl: 5.000; chem. Elemente: 5.000; Energie: 15.000; Credits: 1.000; Bevölkerung: 50 |
| 40 | Slayer | 18000 | 1 | Stahl: 2.000; chem. Elemente: 900; Energie: 2.000; Bevölkerung: 35 |
| 41 | Vendeta | 21600 | 1 | Stahl: 3.000; VV4A: 900; chem. Elemente: 1.000; Energie: 2.800; Bevölkerung: 50 |
| 42 | Crawler | 30600 | 1 | Stahl: 6.200; VV4A: 2.600; Eis: 50; chem. Elemente: 4.500; Energie: 2.000; Credits: 500; Bevölkerung: 75 |
| 43 | Widowmaker | 28800 | 1 | Stahl: 3.600; VV4A: 1.000; chem. Elemente: 3.000; Energie: 3.350; Bevölkerung: 80 |

### Kreuzer (6)

| ID | Name | Dauer (Rohwert) | baubar | Kosten (Nichtnull-Werte) |
| ---: | --- | ---: | ---: | --- |
| 44 | Hitman | 43200 | 1 | Eisen: 4.000; Stahl: 5.000; VV4A: 1.500; chem. Elemente: 2.000; Energie: 4.000; Bevölkerung: 50 |
| 45 | Silent Sorrow | 68400 | 1 | Eisen: 5.000; Stahl: 4.500; VV4A: 850; chem. Elemente: 27.500; Energie: 40.000; Bevölkerung: 65 |
| 46 | Succubus | 54000 | 1 | Eisen: 9.000; Stahl: 15.500; VV4A: 6.550; chem. Elemente: 12.000; Energie: 10.000; Bevölkerung: 100 |
| 47 | Sirius XPi | 72000 | 1 | Eisen: 15.000; Stahl: 19.250; VV4A: 6.150; chem. Elemente: 12.300; Energie: 9.500; Bevölkerung: 80 |
| 53 | X12 (Carrier) | 86400 | 1 | Eisen: 20.000; Stahl: 5.800; VV4A: 4.565; chem. Elemente: 8.000; Energie: 35.000; Credits: 1.500; Bevölkerung: 150 |
| 58 | TAG Vario Kreuzer | 64800 | 1 | Eisen: 15.000; Stahl: 17.000; VV4A: 5.890; chem. Elemente: 11.000; Energie: 12.000; Bevölkerung: 75 |

### Schlachtschiffe (4)

| ID | Name | Dauer (Rohwert) | baubar | Kosten (Nichtnull-Werte) |
| ---: | --- | ---: | ---: | --- |
| 28 | Mule (Carrier) | 100800 | 1 | Eisen: 35.000; Stahl: 18.000; VV4A: 9.000; chem. Elemente: 12.000; Energie: 40.000; Credits: 2.000; Bevölkerung: 250 |
| 48 | Big Daddy | 72000 | 1 | Eisen: 8.000; Stahl: 10.000; VV4A: 4.900; chem. Elemente: 7.500; Energie: 12.000; Bevölkerung: 160 |
| 49 | Kronk | 86400 | 1 | Eisen: 20.000; Stahl: 20.500; VV4A: 9.150; chem. Elemente: 19.900; Energie: 29.850; Bevölkerung: 300 |
| 50 | Quasal | 108000 | 1 | Eisen: 20.000; Stahl: 25.500; VV4A: 10.500; chem. Elemente: 20.400; Energie: 15.000; Bevölkerung: 310 |

### Dreadnoughts (5)

| ID | Name | Dauer (Rohwert) | baubar | Kosten (Nichtnull-Werte) |
| ---: | --- | ---: | ---: | --- |
| 26 | Rosa-Plüschhasen-Spezialschiff | 0 | 0 | Alle Ressourcen: 0 |
| 29 | Nimbus BP-1729 | 0 | 0 | Alle Ressourcen: 0 |
| 51 | Zeus | 126000 | 1 | Eisen: 25.000; Stahl: 40.000; VV4A: 15.000; chem. Elemente: 25.000; Energie: 40.000; Bevölkerung: 500 |
| 52 | Tempest | 162000 | 1 | Eisen: 50.000; Stahl: 65.000; VV4A: 22.000; chem. Elemente: 35.000; Energie: 59.000; Bevölkerung: 750 |
| 57 | Gargoil (Carrier) | 172800 | 1 | Eisen: 55.000; Stahl: 75.000; VV4A: 27.500; chem. Elemente: 45.000; Energie: 50.000; Credits: 2.000; Bevölkerung: 450 |

### Spezielle Schiffe (2)

| ID | Name | Dauer (Rohwert) | baubar | Kosten (Nichtnull-Werte) |
| ---: | --- | ---: | ---: | --- |
| 8 | Scout | 5040 | 1 | Eisen: 100; Stahl: 250; chem. Elemente: 250; Energie: 500; Bevölkerung: 5 |
| 77 | Kampfhuhn | 0 | 0 | Alle Ressourcen: 0 |

## Prüfung der Hypothese: höhere ID bedeutet höherer Tier

**Ergebnis:** Als allgemeine Ordnungsregel auch innerhalb einer XML-Kategorie nicht belastbar. Einzelne benannte Familien zeigen plausible Progression; ID, Kosten und Dauer ersetzen keine Freischaltvoraussetzungen.

| Vergleich | Beobachtung im XML | Einordnung |
| --- | --- | --- |
| Sonden 13 → 24 → 64 | Dauer 360 → 720 → 900; VV4A erst bei 64, chem. Elemente 25 → 50 → 100 | Mit Progression vereinbar, genaue Freischaltung nicht belegt. |
| Korvetten 14 Hunter → 27 Lionheart | Stahl 1.850 → 1.550; VV4A 500 → 0; Dauer 10.800 → 9.000 | Gegenbeispiel zur monoton steigenden Material-/Baukomplexität nach ID. |
| Jäger 30 Sirius X300 → 31 Manta | Stahl 1.500 → 700; Eisen 500 → 300; Energie 1.500 → 1.100; VV4A und chem. Elemente gleich; Dauer 4.680 → 4.320 | ID 31 ist in keinem Kostenfeld teurer, in mehreren günstiger und schneller gebaut. Belegt noch keine tatsächliche Tier-Reihenfolge. |
| Jäger 32 Downbringer → 33 Nightcrawler | VV4A 750 → 250; Energie 1.300 → 1.000; dafür Eisen und chem. Elemente höher | Unterschiedliche Kostenprofile, keine einfache Ressourcenleiter. |
| Zerstörer 20 Pflaumenmus (Carrier) → 40 Slayer | Carrier mit deutlich höheren Kosten und Dauer 64.800 gegenüber 18.000 | XML-Kategorie umfasst verschiedene benannte Rollen. |
| Kreuzer 44 Hitman → 45 Silent Sorrow | VV4A 1.500 → 850 und Stahl 5.000 → 4.500; chem. Elemente 2.000 → 27.500, Energie 4.000 → 40.000 | Weniger VV4A heißt nicht insgesamt günstiger; kein begründeter eindimensionaler Tier-Score. |
| Bomber 34 Stormbringer → 35 Nova → 36 Nepomuk | VV4A 1.200 → 1.800 → 3.250; Dauer 10.800 → 12.600 → 14.400; Stahl fällt zuletzt 2.800 → 1.000 | Grobe Progression plausibel, einzelne Ressourcenkosten nicht monoton. |
| Kampfbasis 54 Alpha → 61 Beta → 67 Gamma | Alle eingesetzten Kostenfelder und Baudauer steigen | Plausible Familienprogression innerhalb der zivilen Kategorie. |
| Hyperraumtransporter Klasse 1: 15 Gorgol 9 → 59 Kamel Z-98 → 70 Flughund | Stahl 2.300 → 6.500 → 50.000; VV4A 0 → 1.200 → 15.000; Dauer 36.000 → 43.200 → 86.400 | Plausible Familienprogression; Kapazität und Leistungsfähigkeit fehlen im XML. |

### Abgleich mit Community-Grafiken (andere Quelle)

[IW-11, Evo 1](https://drive.google.com/file/d/1yt-JLQm8b-CSgF6wfcE693S_PHFz6i_q/view) zeigt Lionheart; [IW-13, Evo 3](https://drive.google.com/file/d/1S-C77ux_m7YuEm88GJ6I2x7VAx5zSrgV/view) zeigt Hunter. Zusammen mit den namensgleichen XML-Einträgen spricht das gegen die vermutete Freischaltreihenfolge nach IDs (Hunter 14, Lionheart 27). Die Verknüpfung erfolgt über Namen; identischer Versionsstand von XML und Grafiken ist nicht nachgewiesen. Kosten aus den Grafiken werden deshalb nicht in die XML-Tabelle eingemischt.

### Weiteres Vorgehen

- ID als Identität bewahren. Historische Reihenfolge der Datenbankanlage oder spätere Ergänzungen sind mögliche Erklärungen für ID-Muster, aber unbewiesene Hypothesen.
- Kandidaten für Familien aus Namen und Kostenprofil ableiten; nicht jede Einheit einer Kategorie muss derselben Progressionslinie angehören.
- [Forschungs-/Evo-Zuordnung](icewars-ship-research.md) liegt jetzt für 38 Einträge namensbasiert vor; 26 bleiben offen. Die Quellenstände bleiben getrennt, ebenso direkte Forschung, indirekte Gebäudeanforderung und unbekannte Bauvoraussetzung. Vollständige transitive Forschungsketten sind nicht gesichert.
- Keine ungewichtete Summe aus Eisen, Energie, Credits und Bevölkerung als „Gesamtkosten“ verwenden. Ressourcenfortschritt und wirtschaftlicher Wert sind eigenständige, bisher nicht vollständig belegte Größen.

## Prüfung der Extraktion

- 64 eindeutige Einheiten-IDs; alle 64 Einträge genau einmal in den Kategorietabellen.
- Alle Kostenreferenzen auf die zehn Ressourcen aus IW-01 aufgelöst; keine unbekannte Ressourcen-ID.
- Jeder Eintrag enthält alle zehn Kostenfelder. Nichtnull-Werte vollständig übernommen; Namen, Kategorien, Dauer und baubar aus dem XML übernommen.
- 56 Einträge mit baubar=1, acht mit baubar=0. Alle Kategorien einschließlich Lebensformen und spezieller Einträge erhalten.
- Keine Zeiten umgerechnet, Kategorien korrigiert oder Tiers erfunden.
