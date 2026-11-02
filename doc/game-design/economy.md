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

**Fachlicher Zusammenhang:** Aus Eis wird Wasser hergestellt; die Bevölkerung verbraucht Wasser zum Überleben. Die am 2026-11-02 bestätigten Bevölkerungsregeln und der ausdrücklich zu prüfende Überlebensschutz stehen in [[economy#Bevölkerung, Wohnraum und Wasserversorgung]]. Wachstums-/Schrumpfungsraten und konkrete Schutzmechanismen sind noch offen.

Für Eis soll deshalb eine positive Mindestergiebigkeit die weitere Förderung und damit grundsätzlich die Herstellung von Wasser ermöglichen. Gemeint ist eine verbleibende Fördermöglichkeit, kein automatisch gefüllter Eis- oder Wasservorrat. Ziel ist, zumindest eine gewisse Bevölkerung am Leben erhalten zu können; der Erhalt der gesamten bisherigen Bevölkerung oder Wirtschaftsleistung ist damit nicht zugesagt. Eine entsprechende Untergrenze für alle anderen Ressourcen ist noch nicht beschlossen.

**Noch nachzuweisen:** Eine positive Eisdichte allein garantiert keine überlebensfähige Versorgung. Energie, Arbeitskräfte, Umwandlungsanlagen und die Verteilung knapper Ressourcen können die Kette dennoch unterbrechen. Zu klären ist, wie die Mindestversorgung ohne manuelles Eingreifen weiterläuft und ob sich eine überlebensfähige Restbevölkerung stabilisieren kann. Konkrete Schutzmechanismen werden daraus erst abgeleitet.

**Balancing offen:** Anfangsdichte, Mindestdichte, Förderleistung, Abbaukurve, Dauer bis zum spürbaren Druck und Versorgung einer Restbevölkerung. Die historischen 5 beziehungsweise 15 Prozent sind keine übernommenen Cold-Frontier-Werte.

## Bevölkerung, Wohnraum und Wasserversorgung

**Quelle / Status:** Im Projektgespräch am 2026-11-02 ausdrücklich bestätigte Regeln und Schutzziel; keine aus IceWars-Quellen übernommenen Formeln.

| Situation | Bestätigtes Verhalten |
| --- | --- |
| Freier Wohnraum und ausreichendes Wasser | Bevölkerung wächst. Wachstumsrate und genaue Wachstumsbedingungen sind noch zu definieren. |
| Verlust von Wohnraum | Bevölkerung wird sofort auf die verbleibende Wohnkapazität begrenzt, soweit sie diese überschreitet. Verlust ungenutzter Kapazität allein tötet niemanden. |
| Wasserproduktion kleiner als Verbrauch, Vorrat noch ausreichend | Der Vorrat deckt den Bedarf. Das Produktionsdefizit allein löst noch keinen Bevölkerungsverlust aus. |
| Vorrat kann Wasserbedarf nicht mehr decken | Bevölkerung schrumpft über die Zeit, bis die Versorgung für die verbleibende Bevölkerung ausreicht. Schrumpfungsrate und Verhalten bei Teilversorgung sind offen. |

**Bestätigtes Schutzziel:** Eine zuvor bewohnte Welt soll nicht allein infolge von Wassermangel und der damit verbundenen Produktions-/Arbeitskräfte-Rückkopplung ihre gesamte Bevölkerung verlieren. Eine überlebensfähige Restbevölkerung soll erhalten bleiben, auch ohne Eingreifen des Spielers. Der Schutz ist planetenbezogen; das Fortbestehen einer anderen Kolonie allein erfüllt ihn nicht.

**Abgrenzung:** Das Schutzziel ist im Kontext der Versorgung formuliert. Es hebt die bestätigte Begrenzung durch Wohnraum nicht auf. Ob es auch bei Verlust sämtlichen Wohnraums, Anlagenzerstörung oder militärischer Vernichtung gelten soll, ist eine eigene offene Regel. Für den primären Prüffall sind positiver Wohnraum und vorhandene Versorgungsanlagen gegeben.

### Rückkopplungsfall: Wasserproduktion braucht Arbeitskräfte

**Risiko, noch kein behauptetes Simulationsergebnis:** Wassermangel senkt die Bevölkerung. Weniger Bevölkerung kann weniger Arbeitskräfte und dadurch weniger Eis- oder Wasserproduktion bedeuten. Die sinkende Versorgung könnte weiteren Bevölkerungsverlust auslösen. Priorisierung allein garantiert keine Stabilität, wenn bereits die gesamte Versorgungskapazität mit der Bevölkerung sinkt.

**Zwei vorgeschlagene Lösungsrichtungen, noch nicht entschieden:**

1. **Autonome Eis- und Wasserproduktion:** Diese Betriebe benötigen grundsätzlich keine Bevölkerung als Arbeitskräfte. Das entfernt diese Personalabhängigkeit, löst jedoch andere Abhängigkeiten (etwa Energie oder erforderliche Vorprodukte) nicht automatisch.
2. **Gesicherte Grundversorgung:** Eine noch zu definierende Versorgung erhält zumindest eine überlebensfähige Restbevölkerung. Umfang, Auslösung, Abhängigkeiten, Kosten und Wechselwirkung mit regulärer Produktion sind offen.

Die Optionen sind weder als abschließende Liste noch als zwingend gegenseitig ausschließend beschlossen. Auch Arbeitskräftebedarf der Energieversorgung und weitere Vorprodukte sind in die Prüfung einzubeziehen. Eine positive Mindest-Eisdichte allein ist kein Nachweis des Schutzziels.

**Noch zu definierende fachliche Prüfkriterien:** Welche Restbevölkerung ist überlebensfähig? Kann sie die erforderliche Grundversorgung dauerhaft aufrechterhalten und bei verbesserten Bedingungen wieder wachsen? Eine nur mathematisch positive Bevölkerung, die sich null annähert oder durch Rundung faktisch handlungsunfähig wird, ist kein ausreichender Nachweis. Daraus wird noch keine konkrete Bevölkerungsuntergrenze oder kostenloser Produktionsbonus festgelegt.

### Geplante Prüfung

S9–S11 unten dienen als fachliche Prüfspezifikation. Insbesondere S11 ist nach Festlegung von Produktions-, Verbrauchs-, Arbeitskräfte- und Bevölkerungsregeln als automatisierter Regressionstest der Simulation umzusetzen. **Derzeit dokumentiert, noch nicht als Simulation ausgeführt:** Die notwendigen Formeln und der Schutzmechanismus sind noch offen; eine Beispielimplementierung würde sonst nicht beschlossene Spielregeln erfinden.

## Bevölkerung und Arbeitskräfte

**Klärung durch den Projektinhaber am 2026-11-02:**

- Jeder Bürger ist grundsätzlich bereit zu arbeiten. Eine gesonderte Erwerbsquote oder Bevölkerungsgruppen werden zunächst nicht eingeführt.
- Laufender Arbeitskräftebedarf entsteht durch Gebäude. Wirtschaftliche Arbeit verbraucht die Bürger nicht als Material.
- Übersteigt die Bevölkerung den laufenden Personalbedarf, besteht **freie Bevölkerung**. Dieser Überschuss ermöglicht Aufträge, deren Kosten Bevölkerung enthalten.
- Unterschreitet die Bevölkerung den Personalbedarf, ist die Wirtschaft unterbesetzt. Wie die Produktivität der einzelnen Gebäude sinkt, ist noch zu entscheiden.
- Ein zusätzlicher laufender Personalbedarf jedes Bau- oder Forschungsauftrags wird aus dieser Aussage nicht abgeleitet. Ob solche Aufträge selbst laufend Personal binden oder nur über ihre ausführenden Gebäude, bleibt offen.

**Begriffe:** Mit Bevölkerung P und gesamtem laufendem Gebäude-Personalbedarf D beträgt der rechnerische Überschuss max(0, P − D), das Personaldefizit max(0, D − P). Diese Größen beschreiben den Bedarf; sie legen noch keine Besetzungsreihenfolge oder Produktivitätsformel fest. Ein Bedarf von 1.000 bei 800 Bürgern bedeutet 200 fehlende Arbeitskräfte, nicht minus 200 lagerbare Bevölkerung.

### Konstruktionskosten, laufender Verbrauch und Personalbindung

**Bestätigte fachliche Unterscheidung, 2026-11-02:** Gebäude und Fahrzeuge können Konstruktionskosten und laufende Unterhaltskosten haben. Die konkrete Zuordnung einzelner Kosten bleibt je Typ festzulegen.

| Kosten-/Bedarfsart | Einheit / Bedeutung | Wirkung |
| --- | --- | --- |
| Konstruktionskosten | Einmalige Ressourcenmenge, einschließlich möglicher Bevölkerungskosten | Wird aus dem entsprechenden Bestand abgezogen; keine bloße Bindung. Eine Rückzahlung beim Abbruch richtet sich nach der separat festzulegenden Rückzahlungsregel. |
| Laufender Ressourcenverbrauch | Ressourcenmenge pro Zeit; laut Projektinhaber voraussichtlich pro Stunde angegeben | Bestand nimmt über die Betriebsdauer entsprechend dem tatsächlichen Verbrauch ab. Exakte Raten, Teilbetrieb und Defizitverhalten sind noch offen. |
| Laufender Personalbedarf | Anzahl gleichzeitig gebundener Bürger | Die Bürger bleiben Bevölkerung, stehen aber nicht gleichzeitig für andere Arbeit beziehungsweise als freier Überschuss zur Verfügung. Kein wiederholter Abzug dieser Anzahl pro Stunde. |

**Bevölkerung als Konstruktionskosten:** Wird Bevölkerung als einmaliger Kostenposten angegeben, wird sie aus dem belasteten Bevölkerungsbestand entfernt. Als laufender Personalbedarf bindet dieselbe Größe dagegen Personen, ohne sie zu verbrauchen. Beide Angaben können fachlich nebeneinander vorkommen; daraus wird noch kein konkretes Flotten-Besatzungssystem abgeleitet. Die erzählerische Bedeutung ausgegebener Bürger (z. B. Wechsel in eine Besatzung) ist nicht gleichbedeutend mit festgelegten Todesfällen.

**Beispiel zur Begriffsprüfung, keine Typdefinition:** Bei 1.000 Einwohnern und 800 laufend benötigten Arbeitskräften sind 200 frei. Ein einmaliger Bevölkerungskostenposten von 100 reduziert den belasteten Bestand auf 900; bei unverändertem Gebäude-Personalbedarf verbleiben 100 freie Bürger. Ein zusätzlicher laufender Personalbedarf von 100 belässt dagegen 1.000 Einwohner, erhöht den Gesamtbedarf auf 900 und lässt ebenfalls 100 frei. Im zweiten Fall gehören die gebundenen Bürger weiterhin zur Bevölkerung für Wasser- und Wohnraumbedarf. Zusätzliche Versorgung außerhalb des planetaren Bestands im ersten Fall ist nicht festgelegt.

**Weiter offen:** Exakter Abbuchungszeitpunkt bei Warteschlangen, mögliche Kosten schon wartender Aufträge, Freigabe von Personal bei Stilllegung, Verluste bei Zerstörung, Rückgabe einmaliger Bevölkerungskosten bei Abbruch sowie die konkrete Zuordnung von Bevölkerungsbeständen bei Fahrzeugen. Laufender Personalbedarf ist kein automatisch angelegtes Reservierungskonto für wartende Bauaufträge.

**Nach hinten gestellte Ideen:**

- Flottenpersonal könnte gesondert modelliert oder ausschließlich über Bevölkerungs-Baukosten abgebildet werden.
- Personal zwischen Wirtschaft und Flotte verschieben und Schiffe vorübergehend stilllegen.
- Noch keine dieser Optionen ist beschlossen; keine Besatzungsverwaltung als Voraussetzung für das erste Wirtschaftsmodell.

**Nächste offene Entscheidung:** Unterbesetzung auf Gebäude verteilen (z. B. proportional zum Bedarf oder mit Schutz der Grundversorgung). Eine proportionale Produktivitätsformel P/D ist nur ein Kandidat, keine bestätigte Regel. Die in S11 dokumentierte Rückkopplung zwischen Wasserproduktion und Arbeitskräften bleibt dabei zu prüfen.

## Gebäude und Bau

**Code-Ist:** Die Enum enthält Eisenmine; Solarzelle, Solar-, Kohle- und Atomkraftwerk; Stahlwerk; Observatorium; Energiespeicher; Wohngebäude; Bunker; Stadtzentrum; Verteidigungsplattform; Schiffswerft. Es existieren Bauwarteschlange und Baufortschritt. Das sind **Kandidaten**, keine festgeschriebene Gebäudeliste.

### Kosten, Abbruch und Fortschritt

**Quelle / Stand:** Klärung durch den Projektinhaber am 2026-11-02. Heutige Regeln und Designrichtungen sind von historischer Inspiration und Vorschlägen getrennt.

| Aspekt | Festgehalten | Status / Grenze |
| --- | --- | --- |
| Baukosten | Bei Beginn eines Baus werden die eingesetzten Ressourcen abgezogen. | Bestätigte Regel. Ob bereits das Einreihen in eine Warteschlange als Beginn gilt, ist noch offen. Kostenzeitpunkt für Forschung nicht ausdrücklich separat bestätigt. |
| Freiwilliger Bauabbruch | Der Spieler erhält nur einen Teil seiner eingesetzten Ressourcen zurück. | Bestätigte Regel. Quote, Rundung, betroffene Ressourcen und Fortschrittsabhängigkeit offen. |
| Zweck des Abbruchverlusts | Bauplätze sollen keine verlustfreie Möglichkeit sein, Ressourcen vor Angriffen zu sichern. | Bestätigte Designabsicht. Teilrückzahlung verhindert allein noch nicht jede profitable Schutzstrategie. |
| Gebäude und Forschung bei beeinträchtigten Bedingungen | Fortschritt soll sich verlangsamen, etwa bei Strommangel. | Bestätigte Designrichtung. Auslöser, Stärke, Kombination von Effekten und Erholung noch zu definieren. Vorläufige Präferenz gegen vollständigen Stillstand; siehe Ergänzung unten. Kein allgemeiner Verlust bereits erreichten Fortschritts festgelegt. |
| Orbitale Werften | Denkbar sind härtere Folgen: Verlust gerade produzierter Schiffe oder Absturz der Werft. | Vorschläge des Projektinhabers, keine beschlossenen Regeln. Ereignisse, Häufigkeiten, Schutzmöglichkeiten und Konsequenzen offen. |
| Unterschiedliche Auftragstypen | Bedingungen und Folgen dürfen sich nach Gebäudebau, Forschung und Schiffbau unterscheiden. Weitere Regeln können später hinzukommen. | Fachlicher Gestaltungsspielraum; daraus folgt kein universelles Job-Framework oder Plugin-System. |

**Zeit und Kosten getrennt betrachten:** Vorausbezahlte Baukosten und zeitabhängiger Fortschritt sind verschiedene fachliche Größen. Begrenzte Baukapazität macht Zeit strategisch wertvoll, erfordert aber kein gemeinsames Verbrauchskonto für Material und Zeit. Das frühere Modell, Zeit kontinuierlich wie eine Ressource zu verbrauchen, ist historische Lösungsarbeit. Laufender Gebäude-Personalbedarf ist inzwischen als Bindung einer Personenanzahl geklärt; verbrauchbare Betriebsmittel werden dagegen als Menge pro Zeit beschrieben. Konkrete Energie-, Verbrauchs- und Fortschrittsregeln bleiben offen.

**Offene Randfälle / Prüfung der Designabsicht:**

- **Warteschlange:** Für IceWars ist inzwischen der Abzug beim Einreihen als historische Erinnerung dokumentiert; siehe [[icewars-references#Erfahrungsbericht – planetare Bauwarteschlangen]]. Für Cold Frontier sind der genaue Abbuchungszeitpunkt sowie Regeln für noch nicht begonnene Aufträge noch zu bestätigen.
- **Abbruchrückzahlung:** Feste Quote oder abhängig von Fortschritt beziehungsweise Auftragstyp? Maßgeblich sind die tatsächlich eingesetzten Ressourcen; eine genaue Berechnungsregel ist noch nicht beschlossen. Verhalten bei zwischenzeitlichen Balancing-Änderungen und Lagergrenzen offen.
- **Ressourcenschutz:** Auch mit Verlust kann Einlagern in Bauaufträge lohnen, wenn die erwartete Rückzahlung höher ist als der nach einem Angriff verbleibende Bestand. Das ist eine zu prüfende Folgewirkung, kein bereits nachgewiesener Exploit einer fertigen Regel.
- **Abbruch, Zwischenfall und Zerstörung unterscheiden:** Die Teilrückzahlung ist bisher für freiwilligen Bauabbruch festgehalten. Erstattungen und Fortschrittsfolgen bei zerstörtem Bauplatz, verlorener Werft oder verlorenem Schiff sind nicht daraus abgeleitet.
- **Fortschritt:** Konkrete Auslöser für Verlangsamung, mögliche Rate null, Wiederaufnahme, Reihenfolge gleichzeitiger Ereignisse und das Wirksamwerden einer Fertigstellung sind offen.
- **Bau-Frei-Minuten:** Historische Beschleunigungsmechanik separat in [[icewars-references#Erfahrungsbericht – Bau-Frei-Minuten]]; keine automatische Übernahme für Cold Frontier.

### Verzögerter Abbruch und Verlust von Voraussetzungen

**Klärung vom 2026-11-02 durch den Projektinhaber:**

- **Regelrichtung für freiwilligen Abbruch:** Zunächst wechselt der Auftrag in den Status **„wird abgebrochen“**. Erst nach Abschluss des Abbruchs werden Ressourcen anteilig zurückgezahlt und der Bauplatz wieder freigegeben. Die Dauer von **5 Minuten** ist ein vorläufiger Vorschlag, kein festes Balancing.
- **Noch offen zum Abbruch:** Arbeitet der Bau währenddessen weiter oder endet sein Fortschritt mit Annahme des Abbruchs? Was geschieht, wenn die reguläre Fertigstellung vorher läge? Kann der Abbruch widerrufen werden? Gelten für wartende Aufträge dieselben Regeln? Wie wirken Angriffe, zerstörte Bauplätze oder Lagergrenzen während der Abbruchphase?
- **Betriebsbedingungen:** Strom und freie Bevölkerung sind erste Kandidaten; die Liste ist erweiterbar und noch nicht abschließend beschlossen. „Freie Bevölkerung“ ist inzwischen als Überschuss über den laufenden Gebäude-Personalbedarf eingeordnet; siehe [[economy#Bevölkerung und Arbeitskräfte]]. Die genaue Verwendung bei Aufträgen ist noch offen.
- **Vorläufige Präferenz gegen Stillstand:** Der Projektinhaber möchte vollständigen Stillstand bei Betriebsmangel nach erster Einschätzung vermeiden, damit sich Accounts nicht vollständig blockieren. Mindestfortschritt, dessen fachliche Grundlage und Grenzen sind noch offen. Eine positive Rate ohne Strom oder Bevölkerung ist damit nicht bereits spezifiziert.
- **Verlust einer Auftragsgrundlage:** Automatischer Abbruch erscheint bei fehlender tragender Infrastruktur, etwa Schiffbau ohne Werft, plausibel. Dies ist ein Diskussionskandidat, keine bestätigte Regel. Kostenverlust, Erstattung, mögliche Rettung beziehungsweise Übertragung und Umgang mit bereits geleisteter Arbeit bleiben offen.
- **Designspannung:** Verlust einer Voraussetzung könnte wochenlang angesparte Investitionen vernichten. Die historische Häufigkeit solcher Fälle ist unbekannt; geringe vermutete Häufigkeit ist kein Beleg für akzeptable Folgen.

**Vorschlag zur fachlichen Unterscheidung:** Startvoraussetzungen (z. B. Freischaltung), Betriebsbedingungen für die Fortschrittsrate und die physische Grundlage eines Auftrags getrennt betrachten. Ein verloren gegangenes Forschungsgebäude muss nicht dieselben Folgen haben wie die Zerstörung der Werft, in der ein Schiff entsteht. Welche konkrete Voraussetzung zu welcher Gruppe gehört, wird erst anhand der Mechanik entschieden; keine allgemeine Abbruchregel für jeden Voraussetzungsverlust.

**Zusätzliche Prüfung der Blockadefreiheit:** Wenn ein verzögerter Abbruch den Bauplatz erst später freigibt, ist zu klären, ob auch dieser Abbruch durch Strom-/Personalmangel verlangsamt werden kann. Vorschlag zur Diskussion: Der freiwillige Abbruch bleibt als begrenzter Ausweg nutzbar. Dies ist noch keine getroffene Entscheidung.

### Warteschlangenaufschlag und Zusammenarbeit

**Bestätigte Designrichtung, 2026-11-02:** Der Projektinhaber möchte den Warteschlangenaufschlag beibehalten. Zeit beziehungsweise begrenzte Baukapazität soll eine besonders wertvolle strategische Größe bleiben. Vorausplanung darf gegenüber zeitnahem manuellem Beauftragen wirtschaftlich nachteilig sein; sinnlose kurze Logins sind dabei kein eigenständiges Motivationsziel.

**Noch zu definieren:** Höhe und Staffelung, betroffene Ressourcenkosten, konkrete Zeitaufschläge und Wechselwirkung mit BFM. Die historische Kombination aus 10 Prozent Ressourcen- und Zeitaufschlag ist nicht als exakter Cold-Frontier-Parametersatz bestätigt.

**Mögliche Entlastung:** Betreuung durch andere Spieler könnte individuellen Zeitdruck abfedern und Zusammenarbeit sowie Koordination belohnen. Historische Inspiration ist der [[icewars-references#Erfahrungsbericht – Sitter-Login|eingeschränkte Sitter-Login]]. Für Cold Frontier sind noch keine konkrete Berechtigungsliste oder Sitter-Regeln beschlossen.

**Abgrenzung als Analyse:** Ein Verbot unmittelbarer Interaktionen mit fremden Accounts beschränkt direktes Pushen über diese Rechte. Es beweist nicht, dass jede Mehrfachaccount-Nutzung oder indirekte Begünstigung ausgeschlossen ist. Die zulässigen Handlungen und die gewünschten Grenzen delegierter Accountpflege sind erst fachlich zu bestimmen; keine technischen Schutzmaßnahmen daraus vorwegnehmen.

**Weitere historische Kandidaten, noch nicht übernommen:** Eine Bauwarteschlange je Planet, Kostenabzug beim Einreihen sowie zusätzliche Plätze über Genetik. Siehe [[icewars-references#Erfahrungsbericht – planetare Bauwarteschlangen]].

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
**Dann gilt (Regelrichtung):** Der Auftrag wechselt zunächst auf „wird abgebrochen“. Erst nach Abschluss des Abbruchs werden ein Teil der eingesetzten Ressourcen zurückgezahlt und der Bauplatz freigegeben. Die vorgeschlagenen 5 Minuten sind vorläufig.  
**Noch zu bestimmen:** Beginn bei Warteschlangennutzung, Abbruchdauer und deren Einflussfaktoren, Fortschritt während des Abbruchs, Rückzahlungsformel, Rundung und Umgang mit verschiedenen Kostenarten.

### S7 – Beeinträchtigter Bau- oder Forschungsfortschritt

**Gegeben:** Ein Gebäude wird gebaut oder eine Forschung läuft.  
**Wenn:** Eine für die Fortschrittsrate relevante Bedingung beeinträchtigt wird, etwa die Stromversorgung.  
**Dann soll gelten (Designrichtung):** Der Fortschritt verlangsamt sich. Ein automatischer Fehlschlag nur wegen der Verlangsamung ist damit nicht festgelegt.  
**Noch zu bestimmen:** Strom und freie Bevölkerung als erste Bedingungskandidaten; konkrete Auslöser, Mindestrate, Kombination mehrerer Engpässe und Verhalten bei Erholung. Vorläufige Präferenz: vollständigen Stillstand und eine dadurch blockierte Accountentwicklung vermeiden; noch keine garantierte Mindestgeschwindigkeit festgelegt.

### S8 – Zwischenfall an einer orbitalen Werft (offener Vorschlag)

**Gegeben:** Eine orbitale Werft produziert ein Schiff.  
**Wenn:** Ein noch zu definierender Zwischenfall eintritt.  
**Dann ist zu entscheiden:** Nur Verlangsamung oder auch Schiffsverlust, Werftverlust beziehungsweise Absturz? Welche Folgen für gezahlte Kosten, andere Aufträge und mögliche betroffene Objekte?  
**Status:** Keine dieser härteren Folgen ist beschlossen.

### S9 – Wohnraumverlust begrenzt Bevölkerung

**Gegeben:** 1.000 Einwohner und 1.200 Wohnplätze.  
**Wenn:** 400 Wohnplätze verloren gehen.  
**Dann gilt:** Unmittelbar danach verbleiben 800 Einwohner bei 800 Wohnplätzen.  
**Gegenprobe:** Gehen stattdessen nur 100 ungenutzte Plätze verloren, bleiben 1.000 Einwohner bei 1.100 Plätzen. Die Werte sind ein bestätigtes Beispiel, keine Balancing-Vorgabe für Planetengrößen.

### S10 – Vorräte überbrücken ein Wasserdefizit

**Gegeben:** Wasserverbrauch übersteigt Wasserproduktion; der Vorrat deckt den tatsächlichen Bedarf noch vollständig.  
**Wenn:** Dieser Zustand über einen Zeitraum anhält.  
**Dann gilt:** Der Vorrat nimmt entsprechend der Bilanz ab; allein wegen dieses Defizits schrumpft die Bevölkerung noch nicht.  
**Wenn:** Der verfügbare Vorrat den Bedarf nicht mehr deckt.  
**Dann gilt:** Wassermangel kann Bevölkerungsschrumpfung auslösen. Zeitpunkt, Teilversorgung und Rate sind nach den noch festzulegenden Regeln zu berechnen.

### S11 – Überleben trotz Wasser-/Arbeitskräfte-Rückkopplung

**Gegeben:** Ein bewohnter Planet mit positivem Wohnraum, vorhandenen Eis-/Wasseranlagen und geringer Wasserversorgung. Produktions-, Verbrauchs- und Bevölkerungsregeln sind für den Test explizit festgelegt; benötigt die Produktionskette Arbeitskräfte, ist deren Bedarf enthalten.  
**Wenn:** Der Wasservorrat aufgebraucht wird, Bevölkerung schrumpft und die Simulation ohne Spieleraktion über längere Zeit fortschreitet.  
**Dann muss das bestätigte Schutzziel erfüllt sein:** Die Versorgungsspirale löscht die Bevölkerung nicht vollständig aus; eine nach noch festzulegenden Kriterien überlebensfähige Restbevölkerung bleibt erhalten. Für deren Versorgung reicht die reguläre oder geschützte Grundversorgung dauerhaft aus.  
**Noch nicht festgelegt:** Konkrete Untergrenze, Zeit bis zum Gleichgewicht, Wachstums-/Schrumpfungsformeln und gewählter Schutzmechanismus.

| Testvariante | Was zu prüfen ist |
| --- | --- |
| Sehr wenig Bevölkerung, leerer Wasservorrat | Schutz greift rechtzeitig; kein arbeitskräftebedingter Kollaps der gesamten Kette. |
| Eisförderung bei Mindestergiebigkeit | Verbleibendes Vorkommen allein wird nicht mit ausreichender Wasserversorgung gleichgesetzt. |
| Energieversorgung ebenfalls personalabhängig | Autonome Wasseranlagen dürfen die Rückkopplung nicht nur in die Stromerzeugung verlagern. |
| Keine verfügbare Energie | Explizit entscheiden, ob der Schutz dies abdecken muss und wodurch; noch keine kostenlose Energie angenommen. |
| Wiederholte kleine Zeitschritte und lange Fortschreibung | Schutz und Bilanz werden nicht durch Zeitschrittweite oder übersprungene Mangelschwellen umgangen; Vergleich innerhalb definierter numerischer Toleranzen. |
| Kleine Mengen und Rundung nahe null | Kein vollständiges Aussterben durch Rundung oder Reihenfolge von Produktion, Verbrauch und Schrumpfung. |
| Versorgung verbessert sich wieder | Bei freiem Wohnraum und ausreichendem Wasser ist erneutes Wachstum möglich; kein dauerhaft blockierter Restzustand. |
| Lange Abwesenheit / wiederholte Fortschreibung | Restbevölkerung bleibt dauerhaft versorgbar; bloßes Verzögern des Aussterbens reicht nicht. |

**Nachvollziehbarkeit für Entwicklung/Support:** Im Fehlerfall müssen Ausgangszustand, geltende Regeln, Zeitschritte beziehungsweise Ereignisfolge, Verteilung der Arbeitskräfte und Wasserbilanz reproduzierbar sein. Dies legt weder Event Sourcing noch die Speicherung jedes Zwischenzustands fest.

## Offene fachliche Fragen – zuerst klären

1. Welche Ressourcen sind lagerbar, welche stellen Kapazität/Rate/Population dar?
2. Welche Ebene besitzt welche Bestände (Planet, Spieler, Flotte)?
3. Wie werden knappe Eingangsressourcen auf Verbraucher verteilt?
4. Welche Ereignisse ändern Produktionsraten?
5. Wie werden Lagergrenzen und erschöpfbare Vorkommen behandelt?
6. Welche Zeitsemantik erwarten Spieler bei gleichzeitigen Aktionen?

## Nächster Modellierungsschritt

Ein *Domain Storytelling* zu einem einzigen Planeten mit Mine, Kraftwerk und nachgelagertem Betrieb durchführen. Anschließend Regeln mit *Example Mapping* (Regel / Beispiel / Frage) konkretisieren. Architekturentscheidungen erst daraus ableiten.
