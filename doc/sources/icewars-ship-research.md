---
status: research
type: source-analysis
---
# IceWars – Forschungszuordnung der historischen Einheiten

**Stand:** 2026-11-01. Ausschließlich historische Rekonstruktion. Diese Einheiten und Voraussetzungen sind keine Cold-Frontier-Regeln; sie dienen dort allenfalls als Inspiration.

## Umfang und Quellen

Die [Einheitenliste mit XML-Kosten](icewars-ships.md) enthält alle 64 Einträge des vorhandenen Exports. Hier erhält jede ID einen Forschungsbefund oder einen ausdrücklichen Unbekannt-Status. Das belegt weder alle jemals existierenden IceWars-Einheiten noch ihre zeitgleiche Verfügbarkeit.

**Serie B** bezeichnet die fünf ähnlich gestalteten Dateien IceWars-Techtree-Evo1 bis Evo5. Die gemeinsame Gestaltung ist ein Gruppierungskriterium, kein unabhängiger Versionsnachweis. Uploaddaten von 2025 datieren nicht die Spielregeln.

| Seite | Quelle | SHA-256 der ausgewerteten Grafik |
| --- | --- | --- |
| B1 / Evo 1 | [IW-11](https://drive.google.com/file/d/1yt-JLQm8b-CSgF6wfcE693S_PHFz6i_q/view) | `147b5b4402889e385857b646f4426d909e20e620b6062366982c6177e62eaa5c` |
| B2 / Evo 2 | [IW-12](https://drive.google.com/file/d/10j0oA_e1vozl-W2JMRedfr8BU6yID_Bh/view) | `14a6b5d754a741efc274d944bacb09b771cec29768b7ed21bf8227b1cbe5ac82` |
| B3 / Evo 3 | [IW-13](https://drive.google.com/file/d/1S-C77ux_m7YuEm88GJ6I2x7VAx5zSrgV/view) | `b948eaf811d8c7d8061d090e057c4034ca6b02cdbe15d14fb481e3df75de2cf8` |
| B4 / Evo 4 | [IW-14](https://drive.google.com/file/d/1Tzu_CmrF-Bi_YUVVNtj2ZATInYKacSHr/view) | `d637b798ca571e208d6ac6eb90b4f17b18c6758b7f0f47a35a71c02109cf368d` |
| B5 / Evo 5 | [IW-15](https://drive.google.com/file/d/1tQWofJMO0Z-jJ2WgiDTjqYixuLYhlkeQ/view) | `018d80f12bbd4fd3f49af8a020a61afa4f24c2eea40ff3991842283135107da3` |

**Serie A** bezeichnet die älteren Dateien evo1.png bis evo5.png sowie evo7.png (IW-05 bis IW-10). [A1 / IW-05](https://drive.google.com/file/d/0B_fvsE93Ci4dUmNOTkxwc29xSlE/view) und [A2 / IW-06](https://drive.google.com/file/d/0B_fvsE93Ci4dRzFsTm0xbWNEcDQ/view) sind ausreichend lesbar. A3–A5 und A7 haben sehr geringe Originalauflösung. Hochskalieren stellt verlorene Schriftinformation nicht wieder her. Evo 6 fehlt. Die separat benannte Evo-0-Grafik ist nicht sicher zugeordnet.

Prüfsummen der ausgewerteten älteren Grafiken: A1 `96ad4dbe3865ee26962171e27545869d182691151bf968949eeab97429a91c97`, A2 `84359733bdbec42629fc07221a275cdeced313f1c2a924f37e74fd6dfb8df649`. Quelleninventar und Bearbeitungsstand: [Quellenkatalog](icewars-catalog.md).

## Leseregeln und Grenzen

- **F:** sichtbare eingehende Pfeile zum Forschungs-Knoten. **R:** ausdrücklich violett markierte zusätzliche Forschung im Kasten. Mehrere gelistete Elemente sind gemeinsam dargestellte Bedingungen; logische UND/ODER-Semantik ist nicht unabhängig vom Community-Plan verifiziert.
- **FP:** Forschungspunkte des Einheiten-Forschungs-Knotens; keine Materialkosten oder kumulierten Kosten des gesamten Forschungspfads. Die Legende kennt auch ungeprüfte FP; Zahlen sind nicht unabhängig validiert.
- Ein Einheitenname als Voraussetzung bezeichnet hier Forschung, nicht Besitz oder Verbrauch eines gebauten Schiffes. Ein Upgrade- oder Ersatzmechanismus wird daraus nicht abgeleitet.
- Die Matrix nennt direkte Vorgänger und violette Zusatzfelder. Vorgelagerte Bedingungen werden in ausgewählten Forschungsketten separat aufgeführt; die Matrix ist keine vollständige transitive Voraussetzungsliste.
- **Orange Zeilen** sind laut Legende benötigte Gebäude. Gebäudeabbildungen unterhalb eines Knotens gelten als zugeordnete Freischaltungen, nicht als automatisch benötigte Gebäude. Konkrete Werftstufen und Bauvoraussetzungen sind dadurch nicht belegt.
- Die Grafiken enthalten keine XML-IDs. Verknüpft wird über Namen und gegebenenfalls Rollenbezeichnungen. XML-Kategorien bleiben erhalten; ein gemeinsamer Balancing-Stand ist nicht nachgewiesen.
- **Offen** bedeutet fehlender belastbarer Beleg, nicht Voraussetzungslosigkeit, Nichtexistenz oder sicher fehlende Freischaltung.
- Keine standardmäßige Werftpflicht, Forschung auf demselben Planeten, Bauplatzregel oder direkte Vorgängereinheit aus dem Namen ergänzen.

## Zuordnung für alle 64 XML-Einträge

37 namensbasierte Zuordnungen in B1–B5 und eine zusätzliche Zuordnung in A2; 26 XML-Einträge ohne belastbare Forschungszuordnung. Terraformer Alpha ist nur eingeschränkt als Einheiten-Freischaltung zuordenbar; SilentSorrow ist eine Schreibvariante. **Alle Materialkosten, Dauern und baubar-Rohwerte stehen im [XML-Katalog](icewars-ships.md).**

| XML-ID | XML-Name | Quelle | Eigener Forschungs-Knoten (FP) | Abgelesene direkte Forschungsvoraussetzungen | Einschränkung |
| ---: | --- | --- | ---: | --- | --- |
| 8 | Scout | B1 | 9.600 | Raumschiffchassis (F) | Keine Forschungs- oder Gebäudezeile im Einheitenkasten. |
| 9 | Systrans (Systemtransporter Klasse 1) | B1 | 9.600 | Raumschiffchassis (F) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 10 | Crux (Systemtransporter Kolonisten) | B1 | 16.000 | Eistransport (F) | Keine orange Gebäudezeile; Kolonistentransporter nicht mit Kolonieschiff gleichsetzen. |
| 11 | Lurch (Systemtransporter Klasse 2) | B1 | 16.000 | Eistransport (F) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 12 | KISS-01 (Systemkolonieschiff) | B1 | 40.000 | Eistransport; Kolonie auf felsigen Planeten (F) | Indirekte Gebäudebedingung Startrampe über Extraterrestrisches Siedeln, siehe Forschungsketten. |
| 13 | Sonde X11 | B1 | 1.920 | Visuelle Optik (F) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 14 | Hunter | B3 | 200.000 | energetische Panzerung; Hyperraumphotonenantrieb (F) | Lionheart nicht als zusätzliche direkte Voraussetzung eingetragen: in dieser Serie kein entsprechendes Feld/Pfeil am Hunter-Knoten. |
| 15 | Gorgol 9 (Hyperraumtransporter Klasse 1) | B2 | 60.000 | große Schiffsrümpfe; Systrans (F+R) | Systrans ist eine violett markierte Forschungsvoraussetzung, kein Nachweis eines benötigten vorhandenen Transporters. |
| 16 | Kolpor (Hyperraumtransporter Kolonisten) | B2 | 75.000 | große Schiffsrümpfe; Cryogenic; Crux (F+R) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 17 | Eisbär (Hyperraumtransporter Klasse 2) | B2 | 50.000 | große Schiffsrümpfe; Lurch (F+R) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 18 | INS-03A (interstellares Kolonieschiff) | B2 | 100.000 | große Schiffsrümpfe; Cryogenic; KISS-01 (F+R) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 20 | Pflaumenmus (Carrier) | B2 | 75.000 | große Schiffsrümpfe (F) | Grafik: kleiner Carrier; XML-Kategorie Zerstörer unverändert. |
| 21 | bürokratischer Formularbomber | Offen | Nicht belegt | Nicht sicher rekonstruierbar | B2/A2 nennt Atombomber; Gleichsetzung mit bürokratischem Formularbomber nicht belegt. |
| 22 | Sheep | B2 | 25.000 | kleine Lebenserhaltungssysteme; Gausskanone (F) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 23 | Shark | B3 | 110.000 | Photonenantrieb; energetische Panzerung; Sheep (Jäger) (F+R) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 24 | Terminus Sonde | B2 | 145.000 | Cryogenic (F) | Namensgleicher Knoten; vollständige ältere Voraussetzungen nicht zusätzlich angenommen. |
| 25 | Terraformer Alpha | B3 | 500.000 | Grundlagen Terraformen (F) | Namensgleicher Forschungsknoten ohne Einheitenbild: Verknüpfung zum XML-Terraformer plausibel, aber kein unabhängiger Bauplanbeleg. |
| 26 | Rosa-Plüschhasen-Spezialschiff | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. baubar=0 erklärt die Verfügbarkeit nicht. |
| 27 | Lionheart | B1 | 15.000 | Raumschiffchassis (F) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 28 | Mule (Carrier) | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. |
| 29 | Nimbus BP-1729 | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. baubar=0 erklärt die Verfügbarkeit nicht. |
| 30 | Sirius X300 | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. |
| 31 | Manta | B4 | 895.000 | Fast Tracking Systeme; Impuls Vektor Triebwerk; Pulslasertechnik; Shark (Jäger) (F+R) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 32 | Downbringer | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. |
| 33 | Nightcrawler | B5 | 450.000 | Tarnung ist alles (F) | Grafik: Tarnjäger; XML: Jäger. |
| 34 | Stormbringer | B4 | 1.800.000 | Fusionstechnologie (F) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 35 | Nova | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. |
| 36 | Nepomuk | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. |
| 37 | Victim | B4 | 950.000 | Alpha Class Schilde; Fast Tracking Systeme; Vendeta (Zerstörer) (F+R) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 38 | Gatling | Offen | Nicht belegt | Nicht sicher rekonstruierbar | SDI Gatling bei Gausskanone in B2 nicht mit XML-Korvette Gatling gleichgesetzt. |
| 39 | Eraser | B5 | 650.000 | Tarnung ist alles (F) | Grafik: Tarnkorvette; XML: Korvetten. |
| 40 | Slayer | B1 | 50.000 | Raumschiffchassis (F) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 41 | Vendeta | B3 | 120.000 | energetische Panzerung; Hyperraumphotonenantrieb (F) | Schreibweise Vendeta in XML und Grafik erhalten. |
| 42 | Crawler | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. |
| 43 | Widowmaker | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. |
| 44 | Hitman | B2 | 100.000 | große Schiffsrümpfe; chemischer Hyperraumantrieb (F) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 45 | Silent Sorrow | B5 | 2.400.000 | Tarnung von großen Sachen mittels einer Decke (F) | Grafik schreibt SilentSorrow (Tarnkreuzer); XML Silent Sorrow. Namensvariante, keine ID in Grafik. |
| 46 | Succubus | B4 | 1.700.000 | Alpha Class Schilde; Die Macht der Verführung (F) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 47 | Sirius XPi | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. |
| 48 | Big Daddy | B3 | 250.000 | sehr große Schiffsrümpfe; Hyperraumphotonenantrieb (F) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 49 | Kronk | B5 | 3.000.000 | Gravitonenstrahl (F) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 50 | Quasal | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. |
| 51 | Zeus | B5 | 4.000.000 | Größe macht doch was aus; Gravitonenstrahl (F) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 52 | Tempest | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. |
| 53 | X12 (Carrier) | B4 | 1.500.000 | Alpha Class Schilde; verbesserte Jägerstarttechnologie; Hyperraumphotonenantrieb (F+R) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 54 | Kampfbasis Alpha | B2 | 50.000 | orbitale Dockingsysteme; Ballistische Panzerung (F) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 55 | Robominer V1 | B3 | 75.000 | Asteroidenmining; Hyperraumphotonenantrieb (F) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 57 | Gargoil (Carrier) | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. |
| 58 | TAG Vario Kreuzer | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. |
| 59 | Kamel Z-98 (Hyperraumtransporter Klasse 1) | B4 | 699.800 | Fusionstechnologie; Hyperraumphotonenantrieb (F+R) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 60 | Waschbär (Hyperraumtransporter Klasse 2) | B4 | 785.000 | Fusionstechnologie; Hyperraumphotonenantrieb (F+R) | Namensbasiert; gemeinsamer Versionsstand mit XML offen. |
| 61 | Kampfbasis Beta | B4 | 650.000 | Fusionstechnologie (F) | Keine Kampfbasis Alpha als zusätzliche direkte Voraussetzung aus Name/Versionsbuchstabe abgeleitet. |
| 63 | Weihnachtsmannschlitten (Transporter) | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. baubar=0 erklärt die Verfügbarkeit nicht. |
| 64 | Sonde X13 | B5 | 1.000.000 | Bessere Informationsgewinnung (F) | Der benachbarte Knoten Wir wollen aber nicht ausspioniert werden ist ein anderer Nachfolger, keine abgelesene Voraussetzung der Sonde. |
| 67 | Kampfbasis Gamma | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. |
| 69 | Raketentransporter (mit Plutoniumdrachenantrieb) | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. baubar=0 erklärt die Verfügbarkeit nicht. |
| 70 | Flughund (Hyperraumtransporter Klasse 1) | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. |
| 71 | Seepferdchen (Hyperraumtransporter Klasse 2) | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. |
| 72 | Artefaktsammelbasis Alpha | A2 / IW-06 | 150.000 | Suche nach neuen alten Sachen (F) | Namensvariante „Artefaktsammelbasis Alpha“ in A2; nicht in B ergänzt. |
| 73 | Artefaktsammelbasis Beta | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. |
| 76 | Settlers Delight | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. baubar=0 erklärt die Verfügbarkeit nicht. |
| 77 | Kampfhuhn | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. baubar=0 erklärt die Verfügbarkeit nicht. |
| 83 | Eichhörnchen | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. baubar=0 erklärt die Verfügbarkeit nicht. |
| 84 | wilde Katze | Offen | Nicht belegt | Nicht sicher rekonstruierbar | Kein belastbarer Forschungsbeleg in B1–B5 und den sonst ausreichend lesbaren Quellen. baubar=0 erklärt die Verfügbarkeit nicht. |

## Forschungsketten und Gebäude

Ausgewählte vorgelagerte Bedingungen; kein vollständiger Export des gesamten Forschungsbaums. Abkürzungen für Gebäude werden nicht spekulativ ausgeschrieben.

| Quelle | Forschungs-Knoten | Vorgelagerte Forschung / Zusatzfeld | Orange Gebäudevoraussetzung | Abgebildete Freischaltung (keine behauptete Baupflicht) |
| --- | --- | --- | --- | --- |
| B1 | Chemischer Antrieb | Race Into Space; Verbrennungstechnik (R) | Keine markiert | Keine |
| B1 | Raumfahrt | Chemischer Antrieb | Keine markiert | Startrampe, Taverne, kl. Werft, Fl.scanner |
| B1 | Raumschiffchassis | Raumfahrt | Keine markiert | Keine |
| B1 | Eistransport | Raumschiffchassis | Keine markiert | Keine |
| B1 | Extraterrestrisches Siedeln | Mehrere Pfeile; hier nicht vollständig transkribiert | **Startrampe** | Keine |
| B1 | Kolonie auf felsigen Planeten | Extraterrestrisches Siedeln | Keine zusätzliche markiert | Keine |
| B2 | Große Schiffsrümpfe | Ballistische Panzerung; chemischer Hyperraumantrieb; Raumschiffchassis (R) | Keine markiert | mit. plan. Werft |
| B2 | Kleine Lebenserhaltungssysteme | Große Schiffsrümpfe | Keine markiert | Keine |
| B2 | Cryogenic | Aufnahme in die zivilisierten Welten | Keine markiert | Keine |
| B2 | Aufnahme in die zivilisierten Welten | Antrag auf imperiale Besteuerung | **Imp. Steuerbehörde** | Keine |
| B3 | Sehr große Schiffsrümpfe | Zero G Forschung; große Rümpfe (R, abgekürzte Bezeichnung) | Keine markiert | gr. Werft |
| B3 | Grundlagen Terraformen | Genetik | Keine markiert | Keine |
| B5 | Größe macht doch was aus | Wieso haben alle einen Partner nur ich nicht? | Keine markiert | DN Werft, kl. Stahlkomplex |
| B5 | Verstehen der Zusammenhänge | Mehrere Pfeile; hier nicht vollständig transkribiert | **Quantencomputer** | Keine |
| A2 | Suche nach neuen alten Sachen | Eine kurze Abhandlung über den Nutzen… | **prähistorische Ausgrabung; große prähist. Ausgrabung; Artefakt-Ausgrabungszentrum** | Keine |

**Indirekte Gebäudebedingungen:** KISS-01 ← Kolonie auf felsigen Planeten ← Extraterrestrisches Siedeln enthält eine ausdrücklich markierte Startrampe. Das beweist noch nicht deren fortdauernde Notwendigkeit beim späteren Bau jedes Schiffes. Entsprechend liegt die Imp. Steuerbehörde indirekt vor Cryogenic und den darüber verknüpften Einheiten.

**Werft-Freischaltung und Bauvoraussetzung unterscheiden:** Zeus hat Größe macht doch was aus als Vorgänger; dort ist eine DN Werft abgebildet. Daraus wird keine konkrete Zeus-Bauwerft oder Werftstufe behauptet.

## Ältere Serie A: eigenständige Befunde

Nicht in die B-Matrix hineingemischt. Die Auswahl enthält nur sicher lesbare lokale Beziehungen, keine vollständige Voraussetzungsliste.

| XML-Bezug nach Name | Quelle | Lesbarer Befund | Abgrenzung |
| --- | --- | --- | --- |
| Sonde X11 (13) | A1 | Visuelle Optik → Sonde X11 | Entspricht lokal B1. |
| Scout (8), Systrans (9), Slayer (40), Lionheart (27) | A1 | Raumschiffchassis → jeweiliger Einheiten-Knoten | Keine globale Tier-Reihenfolge nach ID. |
| Terraformer Alpha (25), eingeschränkt | A1 | Grundlagen Terraformen → Terraformer Alpha; Grundlagen Terraformen nennt Kontrollzentrum m. Startr. in orange | A1 nennt 30.000 FP für Grundlagen Terraformen, B3 500.000 FP und andere Vorgänger. Gebäudeangabe nicht auf B übertragen. |
| Artefaktsammelbasis Alpha (72), Namensvariante | A2 | Knoten Artefaktsammelbasis Alpha, 150.000 FP; eingehend Suche nach neuen alten Sachen | XML-ID fehlt in Grafik; kein eigenständiger Nachweis aller Bauvoraussetzungen. |
| Sheep (22), Hitman (44) | A2 | Einheiten-Knoten sichtbar; Weiterverweise zu Shark (Evo 3) und Succubus (Evo 4) | Seitenverweise ersetzen keine geprüften Voraussetzungen der unscharfen Folgeseiten. |
| Atombomber, keine sichere XML-ID | A2 | Einheiten-Knoten 40.000 FP sichtbar | Kein Beleg für Gleichsetzung mit bürokratischem Formularbomber. |

## Namenskonflikte und zusätzliche Grafikobjekte

| Grafikbefund | XML-Bezug | Umgang |
| --- | --- | --- |
| B2/A2: Atombomber (40.000 FP) | Kein namensgleicher XML-Eintrag; ID 21 heißt bürokratischer Formularbomber | Als zusätzlichen historischen Grafiknamen erhalten. Identität, XML-Kosten und Verfügbarkeit nicht sicher belegt. B2 zeigt mindestens kleine Lebenserhaltungssysteme als Vorgänger; vollständige Voraussetzungen hier nicht rekonstruiert. |
| B2: SDI Gatling als Freischaltung von Gausskanone | ID 38 heißt Gatling, Kategorie Korvetten | Keine automatische Gleichsetzung. |
| B3: Robominer V1 | ID 55 Robominer V1 | Bergbaugebäude unter anderen Knoten sind nicht deshalb Bauvoraussetzungen des Schiffes. |
| B4: X12 (Carrier) | ID 53, XML-Kategorie Kreuzer | Beide Angaben erhalten; keine Kategorie korrigiert. |
| B5: SilentSorrow (Tarnkreuzer) | ID 45 Silent Sorrow | Plausible Schreib-/Rollenvariante; keine ID in Grafik. |
| B3/A1: Terraformer Alpha | ID 25 Terraformer Alpha | Namensgleich; Forschungs-Knoten allein sichert die Einheiten-Freischaltung nicht vollständig. |

## Kosten und historische Verfügbarkeit

- B1 nennt für Systrans 1.500 Stahl; IW-03 enthält 1.000. Für Sonde X11 stehen 15 Stahl in B1 und 10 im XML. Viele Werte ähneln Faktor 1,5, aber Rundung, Modifikatoren oder Versionsunterschiede sind nicht geklärt. Keine allgemeine Umrechnungsregel ableiten.
- Die vollständigen Materialkosten bleiben **XML-Stand**; FP und Forschungsbedingungen sind **Grafik-Stand**. Beide werden nicht als garantiert gemeinsam gültige Spielkonfiguration ausgegeben.
- baubar=1 ist ein Exportfeld. Forschungsknoten und baubar-Wert belegen weder universelle Freischaltung noch damalige Accountvoraussetzungen. Lebensformen und Nullkosten-/baubar=0-Einträge bleiben ohne erfundene Event-, NPC- oder Bonusrolle.
- Waffenwerte, Geschwindigkeit, Fracht, Tankvolumen, Besatzungssemantik, konkrete Werftstufen und Upgradefähigkeit lassen sich nicht zuverlässig ergänzen.
- Fehlende Zuordnung betrifft insbesondere spätere, spezielle und abweichend benannte Einträge. Das bedeutet nicht, dass diese Einheiten historisch nicht existierten.

## Nachweisstand und offene Lücken

- Alle 64 XML-IDs genau einmal in der Matrix; 38 mit mindestens einem namensbasierten Grafikbezug, 26 ohne belastbare Forschungszuordnung. Einschränkungen der Namensverknüpfung bleiben sichtbar.
- Direkte eingehende Pfeile und Zusatzfelder der 37 B-Einheiten visuell verfolgt. Dies verifiziert den Community-Plan nicht unabhängig gegen damaligen Spielcode.
- Keine vollständige transitive Liste aller Forschungen pro Schiff. Vorgelagerte Werft-/Kolonisationspfade und explizite Gebäudefelder sind ausgewählt dokumentiert.
- B endet mit Evo 5; A fehlen Evo 6 und lesbare Details späterer Seiten. Bessere Originale oder weitere Primärdaten könnten die übrigen Einheiten und Bedingungen klären.
- Die Quellen-Originale sind weiterhin nicht im Repo archiviert. Links und Prüfsummen ersetzen diese Archivierung nicht.
