---
status: in-klaerung
type: game-design
focus: true
---
# Planetare Wirtschaft

**Stand:** Erste fachliche Exploration, **keine akzeptierte Regelspezifikation**.

## Zielbild (Projektziel)

Die Wirtschaft soll mehr als konstante Einkommensraten abbilden: Gebäude produzieren und verbrauchen Ressourcen, Engpässe beeinflussen andere Zweige, planetare Rohstoffvorkommen können sich erschöpfen. Spieler sollen Ursachen und Folgen nachvollziehen können.

## Bisher beobachtete Unterscheidung (Code-Ist)

| Ebene | Im Prototyp | Einordnung |
| --- | --- | --- |
| Spielerweit | **Credits** im `UserAccount`; `ResourceSetMsg` enthält `credits` | Spielerweit verfügbar laut Projektkontext; Semantik weiterer Währungsvorgänge offen |
| Planetarer Bestand | `PlanetResourceSetMsg`: **iron, energy, population** | Einfache Bestandsdarstellung, keine endgültige Ressourcenliste |
| Planetare Vorkommen | `MiningResourcesMsg`: **iron, deepIron, chemicals, ice, deepIce** | Struktur für Rohstoffvorkommen, Erschöpfungslogik TODO |
| Weitere angedachte Ressourcen | **Stahl, VV4A, chemische Elemente, Wasser, Eis** u. a. | Projektideen; im genannten Bestandsrecord derzeit nicht repräsentiert |

Achtung: **Bestand** und **Vorkommen** sind unterschiedliche Konzepte. Ebenso ist offen, ob Energie und Bevölkerung wirklich wie lagerbare Verbrauchsgüter behandelt werden sollen.

Quellcode: [ResourceSetMsg](../../lib/src/main/java/de/bwoester/coldfrontier/accounting/ResourceSetMsg.java), [PlanetResourceSetMsg](../../lib/src/main/java/de/bwoester/coldfrontier/accounting/PlanetResourceSetMsg.java), [MiningResourcesMsg](../../lib/src/main/java/de/bwoester/coldfrontier/planet/MiningResourcesMsg.java), [UserAccount](../../lib/src/main/java/de/bwoester/coldfrontier/accounting/UserAccount.java).

## Produktion und Abhängigkeiten

**Code-Ist:** `ProductionService` summiert feste Produktionsvektoren je Gebäude; noch ohne Engpässe, verfügbare Ausgangsstoffe oder Modifikatoren. `IRON_MINE` ist als Beispiel mit positiver Eisen- und negativer Energieproduktion konfiguriert. Das ist **kein** endgültiges Balancing.

**Zu untersuchen:** Input/Output-Rezepte, gleichzeitig konkurrierende Verbraucher, Prioritäten, Teilbetrieb, Speicherfähigkeit, zeitliche Auflösung, Rundungsregeln, Sättigung und Kaskadenengpässe.

## Gebäude und Bau

**Code-Ist:** Die Enum enthält Eisenmine; Solarzelle, Solar-, Kohle- und Atomkraftwerk; Stahlwerk; Observatorium; Energiespeicher; Wohngebäude; Bunker; Stadtzentrum; Verteidigungsplattform; Schiffswerft. Es existieren Bauwarteschlange und Baufortschritt. Das sind **Kandidaten**, keine festgeschriebene Gebäudeliste.

**Offene Fragen:** Kosten wann abziehen/reservieren? Wie beeinflussen Energie/Bevölkerung Baustopps? Wann wird eine Fertigstellung wirksam? Wie viele Bauplätze oder parallele Aufträge?

## Handel und Transport

**Projektziel:** Ressourcen handeln und zwischen Planeten transportieren. Noch offen: Warenbesitz während Transport, Credits-Transfers, Handelsregeln, Verzögerung, Angriffe auf Konvois und atomare Buchungen.

## Erste Szenarien (noch ohne erfundene Erwartungswerte)

### S1 – Energieknappheit

**Gegeben:** Zwei Minen brauchen jeweils Energie, das Kraftwerk liefert weniger als beide zusammen benötigen.  
**Wenn:** Ein Produktionszeitraum vergeht.  
**Dann muss entschieden werden:** Wer erhält wie viel Energie? Proportional, priorisiert oder gar kein Teilbetrieb? Was sieht der Spieler?

### S2 – Erschöpftes Vorkommen

**Gegeben:** Eine Mine baut Eisen aus einem begrenzten Vorkommen ab.  
**Wenn:** Das Vorkommen vor Ende des Produktionszeitraums leer wird.  
**Dann muss entschieden werden:** Exakter Erschöpfungszeitpunkt, Teilförderung, Wiedererschließung, Folgen für abhängige Betriebe.

### S3 – Zwei gleichzeitig konkurrierende Ausgaben

**Gegeben:** Zwei Bauaufträge wollen dieselben planetaren Ressourcen verwenden.  
**Wenn:** Beide nahezu gleichzeitig eingehen.  
**Dann muss gelten (Kandidat für Invariante):** Keine doppelte Ausgabe desselben Bestands; Reihenfolge, Ablehnung und Spielerfeedback sind zu definieren.

## Offene fachliche Fragen – zuerst klären

1. Welche Ressourcen sind lagerbar, welche stellen Kapazität/Rate/Population dar?
2. Welche Ebene besitzt welche Bestände (Planet, Spieler, Flotte)?
3. Wie werden knappe Eingangsressourcen auf Verbraucher verteilt?
4. Welche Ereignisse ändern Produktionsraten?
5. Wie werden Lagergrenzen und erschöpfbare Vorkommen behandelt?
6. Welche Zeitsemantik erwarten Spieler bei gleichzeitigen Aktionen?

## Nächster Modellierungsschritt

Ein *Domain Storytelling* zu einem einzigen Planeten mit Mine, Kraftwerk und nachgelagertem Betrieb durchführen. Anschließend Regeln mit *Example Mapping* (Regel / Beispiel / Frage) konkretisieren. Architekturentscheidungen erst daraus ableiten.
