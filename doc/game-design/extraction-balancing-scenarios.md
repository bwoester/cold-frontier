---
status: in-klaerung
type: balancing-exploration
---
# Ressourcenabbau über eine Runde: Szenarien und Extremfälle

**Stand: 2026-11-08.** Vom Projektinhaber gewünschte Exploration, kein beschlossenes Balancing. Für Eisen soll der Förderverlust möglicherweise praktisch vernachlässigbar bleiben. Für Eis ist die gewünschte Richtung: ungefähr ab dem ersten Rundendrittel spürbar, ab dem zweiten Drittel ein echtes strategisches Problem. Dies sind vorläufige zeitliche Zielmarken; was „spürbar“ und „Problem“ konkret bedeutet, bleibt zu operationalisieren.

Die [mathematische Grundlage](continuous-ice-extraction.md) erklärt Parameter, allgemeine Integralform und einfache Abschnittsformel. Hier untersuchen wir, welche Kurven dieses Modell unter unterschiedlichen Annahmen erzeugt. Es ist keine Simulation der gesamten Wirtschaft und keine Prognose realen Spielerverhaltens.

## Vergleichsrahmen ohne vorweggenommene Rundendauer

Wir normieren die Zeit als x = t/T. T ist eine noch festzulegende Rundendauer; die Quelle startet für diese Beispiele bei R₀ = S und d₀ = 1. Alle Minen nutzen diese Quelle, sind versorgt und können ihre Förderkapazität einsetzen. Eine Mindestergiebigkeit, alternative Quellen und Lager-/Verbraucherrückwirkungen sind in den Charts **nicht** enthalten. Gerade die Kurven nahe null zeigen daher die Grenzen des Grundmodells und kein akzeptiertes Verhalten des Spiels.

| Größe | Bedeutung |
| --- | --- |
| x = t/T | Verstrichener Anteil der Runde, 0 bis 1 |
| P₀ | Anfangskapazität bei voller Referenzergiebigkeit, beispielsweise in Eis/h |
| m(x) = P(t)/P₀ | Ausbauverlauf als Vielfaches dieser Kapazität |
| α = P₀T/S | Dimensionslose Abbauintensität; großes S bedeutet kleines α |
| J(x) = ∫₀ˣ m(v) dv | Dimensionslose kumulierte Referenzförderung |

Damit gilt für die vorgegebenen Ausbaupläne:

$$
d(x)=e^{-\alpha J(x)},\qquad \frac{q(x)}{P_0}=m(x)e^{-\alpha J(x)}.
$$

Die Pläne sind stückweise konstant: Jeder Kapazitätssprung steht für angenommene Fertigstellungen oder Upgrades. Zwischen diesen Ereignissen berechnen wir den Abbau direkt mit der einfachen Abschnittsformel. Die Chart-Stützpunkte dienen nur der Darstellung. **Kapazitätsfaktor ist nicht automatisch Minenzahl:** Unterschiedliche Minentypen, Stufen und Technologien können ebenfalls zu m beitragen.

## Szenario A: Sehr große, mittlere und sehr kleine Quelle

Für alle drei Kurven gilt derselbe Ausbauplan:

| Rundenanteil | 0 bis 1/4 | 1/4 bis 1/2 | 1/2 bis 3/4 | 3/4 bis Ende |
| --- | ---: | ---: | ---: | ---: |
| m = P/P₀ | 1 | 2 | 4 | 8 |

Die Ereignisse liegen bewusst in Vierteln, während die Vergleichsmarken in Dritteln liegen. Die Zielzeitpunkte ergeben sich also nicht aus einem dort gesetzten Kapazitätssprung.

![Sehr große bis sehr kleine Quelle: Effizienz, tatsächliche Förderung und Detail des kaum merklichen Verlusts](assets/ice-extraction/round-extremes.svg)

| Hypothetische Parametrisierung | α | Dichte bei 1/3 | Dichte bei 2/3 | Dichte am Ende |
| --- | ---: | ---: | ---: | ---: |
| Große Quelle; möglicher Eisen-Vergleich | 0,001 | 99,96 % | 99,86 % | 99,63 % |
| Mittlere Quelle; Eis-Untersuchung | 0,5 | 81,19 % | 49,25 % | 15,34 % |
| Kleine Quelle; zu frühe starke Verknappung | 4 | 18,89 % | 0,35 % | 0,000031 % |

Im mittleren Beispiel steigt der rechnerische Kapazitätsbedarf für denselben Durchsatz gegenüber einer unverminderten Quelle auf etwa **Faktor 1,23 bei einem Drittel**, **2,03 bei zwei Dritteln** und **6,52 am Ende**. Das sind jeweils 1/d am betrachteten Zustand: ein momentaner Vergleich, kein bereits simulierter Kompensationsausbau. Zusätzlicher tatsächlicher Ausbau würde den späteren Dichteverlauf wiederum ändern.

**Interpretation:** Das Modell kann einen fast unsichtbaren Verlust für eine große Quelle und einen zunehmend relevanten Effizienzverlust für Eis erzeugen. Der Kurvenverlauf ist aber noch kein Nachweis für die gewünschte Spielerwirkung. Tatsächliche Förderung kann dank Ausbau steigen, während die Effizienz fällt. Für einen Versorgungsmangel braucht es zusätzlich einen Bedarfspfad und die Entwicklung der Vorräte.

Beim großen Vorkommen liegt der Verlust in diesem Ausbauplan am Ende unter 0,4 Prozentpunkten. „Praktisch vernachlässigbar“ muss später gegen Anzeigeauflösung und tatsächliche wirtschaftliche Wirkung geprüft werden. Bei stärkerem Ausbau ist derselbe Wert nicht garantiert. Bei der kleinen Quelle führt das Grundmodell fast zu null; die geplante Mindestergiebigkeit muss diesen Bereich später anders behandeln.

## Szenario B: Gleiche Quelle, verschiedene Ausbaupläne

Alle Kurven verwenden α = 0,5. Nur der vorgegebene Ausbau wird verändert:

| Plan | Erstes Viertel | Zweites Viertel | Drittes Viertel | Letztes Viertel |
| --- | ---: | ---: | ---: | ---: |
| Langsam | 1 | 1,5 | 2 | 3 |
| Mittel | 1 | 2 | 4 | 8 |
| Schnell | 1 | 3 | 8 | 20 |
| Ausbaustopp ab 1/3 | 1 | 2 | 2 | 2 |

Beim Ausbaustopp wird bis 1/3 der mittlere Plan verfolgt. Die letzte Fertigstellung war bei 1/4; danach werden die ab 1/2 geplanten Ausbauten nicht mehr umgesetzt. Bereits betriebene Minen laufen weiter. Dies modelliert einen Ausbaustopp, keine Stilllegung und keine vollständige Abwesenheitssimulation.

![Ausbaupläne mit ihren Auswirkungen auf Dichte und tatsächliche Förderung](assets/ice-extraction/round-growth.svg)

| Plan | Dichte bei 1/3 | Dichte bei 2/3 | Dichte am Ende | Förderung am Ende in P₀ |
| --- | ---: | ---: | ---: | ---: |
| Langsam | 82,90 % | 61,93 % | 39,16 % | 1,175 |
| Mittel | 81,19 % | 49,25 % | 15,34 % | 1,227 |
| Schnell | 77,88 % | 31,14 % | 1,83 % | 0,366 |
| Ausbaustopp | 81,19 % | 58,18 % | 41,69 % | 0,834 |

**Befund:** Im schnellen Plan sinkt die Förderung am Rundenende trotz zwanzigfacher Referenzkapazität unter die Anfangsförderung. Der langsamere Ausbau liefert zu diesem Zeitpunkt mehr, obwohl er weniger Kapazität hat. Das sagt nichts darüber aus, welcher Spieler bis dahin insgesamt mehr gefördert oder wirtschaftlich mehr erreicht hat. Ein günstigerer Endwert ist kein automatischer Beleg für eine bessere Strategie.

Daraus folgt ein Balancingrisiko: Ein für den mittleren Plan passend dimensioniertes Vorkommen kann bei starkem Ausbau zu früh problematisch werden. Ob dies erwünschter Raubbau-Druck oder eine Bestrafung aktiver Spieler ist, muss über erreichbare Gegenmaßnahmen, Informationsqualität und konkrete Wirtschaftsverläufe bewertet werden. Eine vom Kalendertag allein bestimmte Verknappung wäre eine andere Mechanik als abbauabhängige Ergiebigkeit.

## Szenario C: Durchsatz trotz Verlust konstant halten

Ein weiterer Extremfall fragt: Wie viel Kapazität wäre nötig, um q = P₀ unverändert zu liefern? Für diese Gedankenrechnung darf der Spieler Kapazität jederzeit kontinuierlich und ohne Kosten oder Bauzeit anpassen. Dies ist eine idealisierte Anforderungskurve, kein umsetzbarer Bauplan.

Solange der Durchsatz vollständig gedeckt wird und keine Mindestergiebigkeit greift, gilt:

$$
R(t)=S-P_0t,\qquad d(x)=1-\alpha x,\qquad
\frac{P_{\mathrm{erforderlich}}(x)}{P_0}=\frac{1}{1-\alpha x},\quad \alpha x<1.
$$

![Idealisierter Kompensationsbedarf: lineare Dichteabnahme und immer steiler steigende Kapazität](assets/ice-extraction/round-compensation.svg)

Bei α = 0,9 braucht man zur Hälfte der Runde Faktor 1,82, bei zwei Dritteln Faktor 2,5 und am Ende Faktor 10 an Kapazität. Bei α = 1,2 wäre die endliche Quelle bei x = 5/6 aufgebraucht, wenn der Durchsatz bis dahin gehalten werden könnte. Schon davor wächst die nötige Kapazität unbegrenzt; ein realer Ausbau stößt früher an Grenzen. Der Chart zeigt den Kapazitätsbedarf nur bis Faktor 12 und setzt die Lösung jenseits der Erschöpfungsgrenze nicht fort.

Das widerspricht nicht der asymptotischen Annäherung bei konstantem P: Hier wird P gerade ständig erhöht und würde gegen unendlich gehen. Die idealisierte Annahme ist am Grenzzeitpunkt nicht mehr erfüllbar.

**Offene Verbindung zum Spiel:** Minentypen, Baukosten, Technologien und Bauzeiten bestimmen, welche Kapazität erreichbar ist und wie viel Bauzeit sie verdrängt. Man kann aus diesen Charts weder die Zahl notwendiger Minen noch die verlorene Forschungs- oder Militärentwicklung ablesen. Steigender tatsächlicher Bedarf würde den Kompensationsdruck zusätzlich verändern.

## Aus Zielmarken Parameter ableiten

Für einen angenommenen Ausbauplan lässt sich S aus einer gewünschten relativen Dichte d* bei x* berechnen:

$$
\alpha=\frac{-\ln d_*}{J(x_*)},\qquad
S=\frac{P_0T\,J(x_*)}{-\ln d_*}.
$$

Dies gilt hier für d₀ = 1, 0 < d* < 1 und positive Beanspruchung J(x*). Bei anderen Anfangsdichten tritt d*/d₀ in den Logarithmus ein. Nur ein Zielpunkt bestimmt α. **Zwei frei gewählte Zielpunkte sind nicht automatisch mit derselben Quelle und demselben Ausbauplan erreichbar:** Es muss gelten

$$
\frac{-\ln d_1}{J(x_1)}=\frac{-\ln d_2}{J(x_2)}.
$$

Beispielsweise wären 80 Prozent Dichte bei einem Drittel und 50 Prozent bei zwei Dritteln im mittleren Plan keine exakt zusammenpassenden Vorgaben: Sie erfordern α ≈ 0,536 beziehungsweise α ≈ 0,489. Das Vergleichsbeispiel α = 0,5 liegt nahe beiden Werten. Die Prozentzahlen sind Illustrationen, keine bereits beschlossenen Bedeutungen von „spürbar“ und „Problem“.

### Übertragung in reale Tage: reine Skalierungsbeispiele

Mit dem gleichen normierten Ausbauplan und α = 0,5 ergeben sich bei der willkürlich gesetzten Anfangskapazität P₀ = 100 Einheiten/h:

| Angenommene Rundendauer T | Erstes Drittel | Zweites Drittel | Referenzmenge S |
| --- | ---: | ---: | ---: |
| 90 Tage | Tag 30 | Tag 60 | 432.000 Einheiten |
| 180 Tage | Tag 60 | Tag 120 | 864.000 Einheiten |
| 360 Tage | Tag 120 | Tag 240 | 1.728.000 Einheiten |

Diese Skalierung hält den Ausbauplan relativ zur Runde fest. Sie behauptet nicht, dass Spieler in einer halb so langen Runde dieselben Technologien und Minen erreichen. Bei unveränderten realen Bauzeiten ist der Ausbauplan neu zu bestimmen. S ist weiterhin die feste Quellenreferenz, keine nach jedem Ausbau neu gesetzte Menge.

## Noch zu klärende Balancing-Annahmen

| Frage | Warum sie den Verlauf verändert |
| --- | --- |
| Wie lange dauert eine Runde, und was bedeutet Rundenende? | Bestimmt die absolute Zeitskala und das angestrebte Tempo. |
| Wie entwickeln sich Minentypen, Technologien und Bauzeiten? | Bestimmt den plausiblen Kapazitätspfad statt frei gewählter Sprünge. |
| Wie viel Kapazität bauen langsame, mittlere und sehr aktive Spieler? | Bestimmt die Bandbreite, für die das Balancing funktionieren muss. |
| Wie entwickeln sich Eis-/Wasserbedarf und Vorräte? | Erst damit werden tatsächliche Versorgungslücken und Warnzeiten sichtbar. |
| Wann werden andere Quellen erschlossen, und wie wirkt die Mindestergiebigkeit? | Bestimmt Gegenmaßnahmen und den späteren Kurvenast. |
| Gelten Ziele für Rundenalter oder Kolonie-/Spieleralter? | Späteinsteiger und neue Kolonien dürfen nicht versehentlich dem falschen Zeithorizont zugeordnet werden. |

**Vorschlag für überprüfbare Wirkungskriterien:** Ab ungefähr 1/3 muss Kompensation einen merklichen Anteil der verfügbaren Bauzeit beanspruchen. Ab ungefähr 2/3 wird ohne Quellenwechsel oder erheblichen Verzicht auf andere Entwicklung ein Versorgungsdefizit absehbar. Schwellen, Vorwarnzeit und erfasste Spielertypen sind gemeinsam festzulegen. Die Charts allein bestätigen diese Wirkung noch nicht.

Für die nächste Balancingrunde brauchen wir deshalb mindestens einen plausiblen Ausbaupfad mit Bauzeiten und einen Bedarfspfad. Zunächst Bandbreiten verwenden; Unsicherheit über Spielerverhalten nicht durch einen einzelnen scheinbar präzisen Minenzahlwert verdecken.

## Reproduktion und geprüfter Umfang

```sh
python -m pip install matplotlib
python doc/game-design/tools/plot_extraction_scenarios.py
```

Das [Hilfsskript](tools/plot_extraction_scenarios.py) erzeugt die drei SVG-Charts und lokale PNG-Vorschauen. Es prüft die Gleichheit von Integralform, Produkten der Abschnittsformeln und zusätzlich unterteilten Abschnitten für alle Ausbaupläne und Abbauintensitäten. Ein linearer zeitlicher Leistungsanstieg sowie die Kompensationsgleichung werden separat mathematisch geprüft. Keine Aussage über Laufzeit, Java-Engine oder die vollständige Wirtschaft ist damit getestet.
