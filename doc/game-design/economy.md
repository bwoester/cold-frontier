---
status: in-klaerung
type: game-design
focus: true
---
# Planetare Wirtschaft

**Stand:** Fachliche Exploration mit einzelnen ausdrücklich bestätigten Regeln und Designrichtungen; keine vollständige akzeptierte Regelspezifikation.

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

## Sinkende Ergiebigkeit und Zeit als Ressource

**Status:** Vom Projektinhaber am 2026-11-01 bestätigte Designrichtung; konkrete Regeln, Kurven und Grenzwerte bleiben in Klärung. Historische IceWars-Werte sind separat in [[icewars-references#Erfahrungsbericht – Eisförderung und Ausbaukonkurrenz]] dokumentiert.

Sinkende Rohstoffergiebigkeit soll den Aufwand zur Aufrechterhaltung der Versorgung erhöhen. Zusätzliche Förderanlagen konkurrieren dabei mit Forschungs-, Militär- und anderen Wirtschaftsgebäuden um begrenzte Baukapazität. Technologien können diesen Druck vorübergehend abfedern; alternative Quellen sollen langfristig Entwicklungsspielraum zurückgewinnen.

**Zeit als Ressource** bezeichnet hier die begrenzte Möglichkeit, innerhalb eines Zeitraums Gebäude zu errichten. Auch bei ausreichenden Rohstoffbeständen lassen sich hunderte zusätzliche Minen nicht kurzfristig bauen. Die Kompensation sinkender Ergiebigkeit verzögert andere Vorhaben. Das legt weder eine einzelne Bauwarteschlange noch eine technische Modellierung von Zeit als lagerbaren Bestand fest.

- **Designabsicht:** Spieler zu Anpassung und vorausschauender Versorgung bewegen und die Folgen von Raubbau humoristisch vermitteln.
- **Übergangsmöglichkeiten:** Andere Technologien beziehungsweise Förderverfahren sollen zunächst helfen können; genaue Wirkungen sind offen.
- **Strategische Alternativen:** Kolonisation oder großangelegtes Mining. Letzteres ist eine separate, destruktive Mechanik, deren Regeln und Wechselwirkungen noch zu entwerfen sind.
- **Folgerung:** Eine positive Mindestförderung kann mit wirksamem Anpassungsdruck vereinbar sein. Zusätzliche Minen mögen technisch möglich sein, während ihre Bauzeit und die verdrängte Entwicklung einen Quellenwechsel strategisch erforderlich machen.
- **Offen:** Wie früh erkennen Spieler den Druck, und welche Gegenmaßnahmen bleiben erreichbar? Strategischer Druck soll nicht unbemerkt in eine kaum auflösbare Abwärtsspirale führen.

### Mindestergiebigkeit und Überleben bei Abwesenheit

**Bestätigtes Schutzziel:** Ressourcenverknappung soll einen Account bei längerer Abwesenheit, etwa im Urlaub, nicht allein durch vollständiges Versiegen der Eisversorgung aussterben lassen. Insbesondere neue Spieler sollen eine verbleibende Versorgungsgrundlage behalten.

**Fachlicher Zusammenhang laut Projektinhaber:** Aus Eis wird Wasser hergestellt; die Bevölkerung verbraucht Wasser zum Überleben. Fehlendes Wasser kann zum Tod der Bevölkerung führen. Raten, Zeitverhalten und Sterberegeln sind noch offen.

Für Eis soll deshalb eine positive Mindestergiebigkeit die weitere Förderung und damit grundsätzlich die Herstellung von Wasser ermöglichen. Gemeint ist eine verbleibende Fördermöglichkeit, kein automatisch gefüllter Eis- oder Wasservorrat. Ziel ist, zumindest eine gewisse Bevölkerung am Leben erhalten zu können; der Erhalt der gesamten bisherigen Bevölkerung oder Wirtschaftsleistung ist damit nicht zugesagt. Eine entsprechende Untergrenze für alle anderen Ressourcen ist noch nicht beschlossen.

**Noch nachzuweisen:** Eine positive Eisdichte allein garantiert keine überlebensfähige Versorgung. Energie, Arbeitskräfte, Umwandlungsanlagen und die Verteilung knapper Ressourcen können die Kette dennoch unterbrechen. Zu klären ist, wie die Mindestversorgung ohne manuelles Eingreifen weiterläuft und ob sich eine überlebensfähige Restbevölkerung stabilisieren kann. Konkrete Schutzmechanismen werden daraus erst abgeleitet.

**Balancing offen:** Anfangsdichte, Mindestdichte, Förderleistung, Abbaukurve, Dauer bis zum spürbaren Druck und Versorgung einer Restbevölkerung. Die historischen 5 beziehungsweise 15 Prozent sind keine übernommenen Cold-Frontier-Werte.

## Gebäude und Bau

**Code-Ist:** Die Enum enthält Eisenmine; Solarzelle, Solar-, Kohle- und Atomkraftwerk; Stahlwerk; Observatorium; Energiespeicher; Wohngebäude; Bunker; Stadtzentrum; Verteidigungsplattform; Schiffswerft. Es existieren Bauwarteschlange und Baufortschritt. Das sind **Kandidaten**, keine festgeschriebene Gebäudeliste.

### Kosten, Abbruch und Fortschritt

**Quelle / Stand:** Klärung durch den Projektinhaber am 2026-11-02. Heutige Regeln und Designrichtungen sind von historischer Inspiration und Vorschlägen getrennt.

| Aspekt | Festgehalten | Status / Grenze |
| --- | --- | --- |
| Baukosten | Bei Beginn eines Baus werden die eingesetzten Ressourcen abgezogen. | Bestätigte Regel. Ob bereits das Einreihen in eine Warteschlange als Beginn gilt, ist noch offen. Kostenzeitpunkt für Forschung nicht ausdrücklich separat bestätigt. |
| Freiwilliger Bauabbruch | Der Spieler erhält nur einen Teil seiner eingesetzten Ressourcen zurück. | Bestätigte Regel. Quote, Rundung, betroffene Ressourcen und Fortschrittsabhängigkeit offen. |
| Zweck des Abbruchverlusts | Bauplätze sollen keine verlustfreie Möglichkeit sein, Ressourcen vor Angriffen zu sichern. | Bestätigte Designabsicht. Teilrückzahlung verhindert allein noch nicht jede profitable Schutzstrategie. |
| Gebäude und Forschung bei beeinträchtigten Bedingungen | Fortschritt soll sich verlangsamen, etwa bei Strommangel. | Bestätigte Designrichtung. Auslöser, Stärke, Kombination von Effekten, möglicher Stillstand und Erholung noch zu definieren. Kein allgemeiner Verlust bereits erreichten Fortschritts festgelegt. |
| Orbitale Werften | Denkbar sind härtere Folgen: Verlust gerade produzierter Schiffe oder Absturz der Werft. | Vorschläge des Projektinhabers, keine beschlossenen Regeln. Ereignisse, Häufigkeiten, Schutzmöglichkeiten und Konsequenzen offen. |
| Unterschiedliche Auftragstypen | Bedingungen und Folgen dürfen sich nach Gebäudebau, Forschung und Schiffbau unterscheiden. Weitere Regeln können später hinzukommen. | Fachlicher Gestaltungsspielraum; daraus folgt kein universelles Job-Framework oder Plugin-System. |

**Zeit und Kosten getrennt betrachten:** Vorausbezahlte Baukosten und zeitabhängiger Fortschritt sind verschiedene fachliche Größen. Begrenzte Baukapazität macht Zeit strategisch wertvoll, erfordert aber kein gemeinsames Verbrauchskonto für Material und Zeit. Das frühere Modell, Zeit kontinuierlich wie eine Ressource zu verbrauchen, ist historische Lösungsarbeit. Ob Energie oder Arbeitskräfte als laufender Bedarf wirken und wie sie berechnet werden, ist gesondert zu klären; aus dem anfänglichen Kostenabzug folgt dazu keine Regel.

**Offene Randfälle / Prüfung der Designabsicht:**

- **Warteschlange:** Wann genau werden Kosten bezahlt: beim Einreihen oder erst beim tatsächlichen Arbeitsbeginn? Welche Regeln gelten für noch nicht begonnene Aufträge?
- **Abbruchrückzahlung:** Feste Quote oder abhängig von Fortschritt beziehungsweise Auftragstyp? Maßgeblich sind die tatsächlich eingesetzten Ressourcen; eine genaue Berechnungsregel ist noch nicht beschlossen. Verhalten bei zwischenzeitlichen Balancing-Änderungen und Lagergrenzen offen.
- **Ressourcenschutz:** Auch mit Verlust kann Einlagern in Bauaufträge lohnen, wenn die erwartete Rückzahlung höher ist als der nach einem Angriff verbleibende Bestand. Das ist eine zu prüfende Folgewirkung, kein bereits nachgewiesener Exploit einer fertigen Regel.
- **Abbruch, Zwischenfall und Zerstörung unterscheiden:** Die Teilrückzahlung ist bisher für freiwilligen Bauabbruch festgehalten. Erstattungen und Fortschrittsfolgen bei zerstörtem Bauplatz, verlorener Werft oder verlorenem Schiff sind nicht daraus abgeleitet.
- **Fortschritt:** Konkrete Auslöser für Verlangsamung, mögliche Rate null, Wiederaufnahme, Reihenfolge gleichzeitiger Ereignisse und das Wirksamwerden einer Fertigstellung sind offen.
- **Bau-Frei-Minuten:** Historische Beschleunigungsmechanik separat in [[icewars-references#Erfahrungsbericht – Bau-Frei-Minuten]]; keine automatische Übernahme für Cold Frontier.

**Weitere offene Fragen:** Wie viele Bauplätze oder parallele Aufträge? Welche Voraussetzungen müssen nur beim Start beziehungsweise bis zur Fertigstellung erfüllt sein?

## Handel und Transport

**Projektziel:** Ressourcen handeln und zwischen Planeten transportieren. Noch offen: Warenbesitz während Transport, Credits-Transfers, Handelsregeln, Verzögerung, Angriffe auf Konvois und atomare Buchungen.

## Erste Szenarien (noch ohne erfundene Erwartungswerte)

### S1 – Energieknappheit

**Gegeben:** Zwei Minen brauchen jeweils Energie, das Kraftwerk liefert weniger als beide zusammen benötigen.  
**Wenn:** Ein Produktionszeitraum vergeht.  
**Dann muss entschieden werden:** Wer erhält wie viel Energie? Proportional, priorisiert oder gar kein Teilbetrieb? Was sieht der Spieler?

### S2 – Erschöpftes Vorkommen (offene Variante für andere Ressourcen)

Dieses Szenario legt keine vollständige Erschöpfung von Eis fest; dafür gelten die Designrichtung zur Mindestergiebigkeit und S5.

**Gegeben:** Eine Mine baut Eisen aus einem begrenzten Vorkommen ab.  
**Wenn:** Das Vorkommen vor Ende des Produktionszeitraums leer wird.  
**Dann muss entschieden werden:** Exakter Erschöpfungszeitpunkt, Teilförderung, Wiedererschließung, Folgen für abhängige Betriebe.

### S3 – Zwei gleichzeitig konkurrierende Ausgaben

**Gegeben:** Zwei Bauaufträge wollen dieselben planetaren Ressourcen verwenden.  
**Wenn:** Beide nahezu gleichzeitig eingehen.  
**Dann muss gelten (Kandidat für Invariante):** Keine doppelte Ausgabe desselben Bestands; Reihenfolge, Ablehnung und Spielerfeedback sind zu definieren.

### S4 – Sinkende Ergiebigkeit bindet Baukapazität

**Gegeben:** Die Eisförderung eines entwickelten Planeten verliert durch sinkende Eisdichte an Ertrag. Forschung, Militär und Wirtschaft benötigen ebenfalls Gebäudeausbau.  
**Wenn:** Der Spieler zusätzliche Eisminen baut, um den bisherigen Ertrag zu halten.  
**Dann soll gelten (Designrichtung):** Dieser Ausbau benötigt Zeit und konkurriert mit anderen Bauvorhaben. Alternative Versorgung soll Entwicklungsspielraum eröffnen.  
**Noch zu bestimmen:** Baukapazität, Kompensationsdauer, verfügbare Gegenmaßnahmen und verständliche Vorwarnung. Ein einzelnes Queue-Modell wird nicht vorausgesetzt.

### S5 – Mindestergiebigkeit bei längerer Abwesenheit

**Gegeben:** Ein Planet verfügt über eine funktionierende Eis-Wasser-Versorgung; der Spieler greift über längere Zeit nicht ein.  
**Wenn:** Die Eisdichte ihre positive Untergrenze erreicht und vorhandene Vorräte verbraucht werden.  
**Dann soll gelten (Schutzziel):** Die Quelle versiegt nicht vollständig; eine fortgesetzte Wasserversorgung soll zumindest eine gewisse Bevölkerung erhalten können. Vollständiges Aussterben des Accounts allein durch diese Verknappungsmechanik soll vermieden werden.  
**Noch zu bestimmen:** Dauer und Ausgangsbedingungen des Abwesenheitsszenarios, Mindestversorgung, Energie-/Arbeitskräfteabhängigkeiten, Verbraucherprioritäten und Verhalten bei Wassermangel. Die Untergrenze allein gilt noch nicht als Nachweis des Schutzziels.

### S6 – Kostenabzug und freiwilliger Abbruch

**Gegeben:** Ein Spieler hat die Ressourcen für einen Bau.  
**Wenn:** Der Bau beginnt.  
**Dann gilt (bestätigte Regel):** Die Baukosten werden abgezogen.  
**Wenn:** Der Spieler diesen Bau anschließend abbricht.  
**Dann gilt (bestätigte Regel):** Er erhält nur einen Teil der eingesetzten Ressourcen zurück.  
**Noch zu bestimmen:** Beginn bei Warteschlangennutzung, Rückzahlungsformel, Rundung und Umgang mit verschiedenen Kostenarten.

### S7 – Beeinträchtigter Bau- oder Forschungsfortschritt

**Gegeben:** Ein Gebäude wird gebaut oder eine Forschung läuft.  
**Wenn:** Eine für die Fortschrittsrate relevante Bedingung beeinträchtigt wird, etwa die Stromversorgung.  
**Dann soll gelten (Designrichtung):** Der Fortschritt verlangsamt sich. Ein automatischer Fehlschlag nur wegen der Verlangsamung ist damit nicht festgelegt.  
**Noch zu bestimmen:** Auslöser, Rate einschließlich möglichem Stillstand, Kombination mehrerer Engpässe und Verhalten bei Erholung.

### S8 – Zwischenfall an einer orbitalen Werft (offener Vorschlag)

**Gegeben:** Eine orbitale Werft produziert ein Schiff.  
**Wenn:** Ein noch zu definierender Zwischenfall eintritt.  
**Dann ist zu entscheiden:** Nur Verlangsamung oder auch Schiffsverlust, Werftverlust beziehungsweise Absturz? Welche Folgen für gezahlte Kosten, andere Aufträge und mögliche betroffene Objekte?  
**Status:** Keine dieser härteren Folgen ist beschlossen.

## Offene fachliche Fragen – zuerst klären

1. Welche Ressourcen sind lagerbar, welche stellen Kapazität/Rate/Population dar?
2. Welche Ebene besitzt welche Bestände (Planet, Spieler, Flotte)?
3. Wie werden knappe Eingangsressourcen auf Verbraucher verteilt?
4. Welche Ereignisse ändern Produktionsraten?
5. Wie werden Lagergrenzen und erschöpfbare Vorkommen behandelt?
6. Welche Zeitsemantik erwarten Spieler bei gleichzeitigen Aktionen?

## Nächster Modellierungsschritt

Ein *Domain Storytelling* zu einem einzigen Planeten mit Mine, Kraftwerk und nachgelagertem Betrieb durchführen. Anschließend Regeln mit *Example Mapping* (Regel / Beispiel / Frage) konkretisieren. Architekturentscheidungen erst daraus ableiten.
