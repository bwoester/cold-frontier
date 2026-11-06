---
status: proposed
type: architecture-notes
---
# Fachlicher Vertrag der Ressourcensimulation

## Etappe 1

Die Ressourcensimulation schreibt die Wirtschaft einer vorbereiteten Kolonie mit vorgegebenen Anlagen fort. Sie erhält den wirtschaftlich relevanten Zustand zu einer bekannten Spielzeit und eine Zielspielzeit. Die Spielregeln stehen der Simulation zur Verfügung. Sie ermittelt Produktion, Verbrauch und daraus entstehende Übergänge aus dem sich entwickelnden Zustand.

Das Ergebnis enthält den fortgeschriebenen Zustand mit erreichter Spielzeit und die eingetretenen fachlichen Ereignisse mit wirksamer Spielzeit. Der Ergebniszustand dient als Ausgangslage für den nächsten Abschnitt. Ohne äußere Eingriffe ergeben direkte und gestückelte Fortschreibung denselben fachlichen Endzustand und dieselben Ereignisse über den gesamten Zeitraum.

**Unabhängigkeit von Zwischenzeitpunkten.** Bei gleicher Ausgangslage und ohne äußere Eingriffe dürfen zusätzliche Zwischenabfragen oder Fortschreibungsabschnitte weder Ressourcen erzeugen noch vernichten. Bis zur gleichen Zielspielzeit stimmen Bestände, Produktion und Verbrauch sowie die fachlichen Ereignisse mit ihren wirksamen Spielzeiten überein. Mengenanteile dürfen durch die Aufteilung der Fortschreibung nicht verloren gehen. Produzierte Ressourcen werden erst als ganze Einheiten verfügbar. Bruchteile bleiben als Produktionsfortschritt im fortgeschriebenen Zustand erhalten. Die technische Zahlendarstellung, einschließlich einer möglichen Ganzzahlskala, bleibt offen.

Für den [Wasserwerk-Abnahmefall](../game-design/scenarios/water-production.feature) gehört das Erschöpfungsereignis zum Abschnitt, der Minute 36 erreicht. Ein Folgeabschnitt ab Minute 36 meldet es nicht erneut. Anlagen werden gemeinsam im Kontext der Kolonie fortgeschrieben. Der Aufrufer bestimmt weder Raten noch die Reihenfolge einzelner Anlagen.

### Einsatzstoffe für eine laufende Produktion

Das Wasserwerk startet eine Einheit nur, wenn im Ressourcenpool mindestens 1 Eis und 2 Energie verfügbar sind. Beim Start gehen beide Einsatzstoffe aus dem Pool ins Wasserwerk über. Die Einheit bleibt bis zur Fertigstellung als laufende Produktion im Zustand erhalten. Bei Fertigstellung geht 1 ganze Einheit Wasser in den Pool über.

- **Minute 0:** Der vorbereitete Pool enthält vor dem ersten Produktionsstart 6 Eis und 0 Wasser. Bei der Initialisierung entnimmt das Werk 1 Eis und 2 Energie und startet die erste Einheit. Der initialisierte Zustand zu Minute 0 enthält 5 Eis und 0 Wasser im Pool sowie die begonnene erste Einheit. Eine Fortschreibung zur bereits erreichten Spielzeit gibt diesen Zustand ohne neue Ereignisse zurück. Die erste Einheit wird in Minute 6 verfügbar.
- **Minute 30:** Die fünfte Einheit Wasser wird verfügbar. Das Werk entnimmt die letzte Einheit Eis und 2 Energie für die sechste Einheit. Im Pool ist kein Eis mehr.
- **Minute 33:** Im Pool sind 5 Wasser und 0 Eis. Die sechste Einheit ist halb fertig.
- **Minute 36:** Die sechste Einheit Wasser wird verfügbar. Das Werk kann wegen fehlenden Eises keine weitere Einheit starten. Zu diesem Zeitpunkt tritt das Erschöpfungsereignis ein.

Im Wasserwerk-Abnahmefall bezeichnet das Erschöpfungsereignis den Übergang von laufender Produktion zu fehlendem Eis für die nächste Einheit. Der Poolbestand erreicht null bereits bei der Entnahme in Minute 30. Was bei einem Abbruch mit den bereits entnommenen Einsatzstoffen geschieht, bleibt offen.

## Zuständigkeiten bei weiteren Spielmechaniken

| Rolle | Zuständigkeit | Grenze |
| --- | --- | --- |
| Ressourcensimulation | Bestände, Produktion, Verbrauch und daraus entstehende wirtschaftliche Übergänge ermitteln. | Kennt keine Transportpläne, Kampfregeln oder Spieleraufträge. Schreibt fremde Zustände nicht fort. |
| Zuständige Spielmechanik | Die eigene Aktion planen oder auswerten und ihre fachliche Wirkung bestimmen. Beispiele sind Transport, Kampf, Bau und Forschung. | Eine Mechanik schreibt Bestände oder andere fremde Zustände nicht direkt um. Ein Entladeabschluss übergibt beispielsweise die Fracht zur fachlichen Übernahme, deren Ergebnis von der aktuellen Lagerkapazität abhängt. |
| Spielablaufsteuerung | Die nächste relevante Änderung berücksichtigen, betroffene Zustände bis zur wirksamen Spielzeit fortschreiben, Aktionen und Folgewirkungen in fachlicher Reihenfolge anwenden lassen und danach weiterrechnen. | Definiert weder Produktions- noch Kampfregeln. Der bestehende Tick Coordinator ist ein möglicher Ausgangspunkt für diese Rolle. Seine Phasen legen noch keine fachliche Vorrangregel für sämtliche Fälle fest. |

Ein Zeitpunkt kann bereits feststehen oder erst aus dem Fortschritt einer Mechanik folgen. Der voraussichtliche Abschluss eines Baus kann sich zum Beispiel durch einen wirtschaftlichen Engpass verschieben. Ein spontaner Spielerauftrag erhält eine wirksame Spielzeit nach den Regeln der Spielablaufsteuerung. Die Behandlung verspäteter und gleichzeitiger Aktionen bleibt Teil der [offenen Auftragsreihenfolge](command-ordering.md).

## Phasenfolge des Tick-Prototyps

Der [`TickCoordinator`](../../lib/src/main/java/de/bwoester/coldfrontier/TickCoordinator.java) erhöht zuerst den Tickzähler. Dann führt er jede Phase für alle registrierten Komponenten aus, bevor die nächste Phase beginnt. Die Reihenfolge dient als Ausgangspunkt für die Diskussion.

1. Eingaben verarbeiten (`handleInput`)
2. Ressourcen aktualisieren (`handleResourceUpdates`)
3. Fertiggestellte Bauaufträge übernehmen (`handleFinishedUnits`)
4. Wartende Aufträge starten (`handleQueuedUnits`)
5. Bewegung verarbeiten (`handleMovement`)
6. Konflikte auflösen (`handleConflicts`)
7. Sichtbarkeit aktualisieren (`handleVisibilityUpdates`)
8. Zustand speichern (`handlePersistence`)
9. Aufräumen (`handleCleanup`)

Für Lieferungen gibt es im Coordinator keine eigene Phase. Die Einordnung des Entladeabschlusses und seiner Gutschrift im Zielpool bleibt offen. Für den Wasserwerk-Grenzfall ist entscheidend, ob die Gutschrift zur selben Spielzeit bereits vor dem Startversuch der nächsten Wassereinheit verfügbar ist.

## Ereignisse aus der Wirtschaft

Ein innerhalb der Wirtschaft behandelter Übergang kann als Ergebnis gemeldet werden. Im Wasserwerk-Szenario stoppt die Wirtschaft bei Eiserschöpfung den Betrieb selbst. Das Audit-Ereignis dokumentiert die wirksame Spielzeit.

Kann ein Übergang eine andere Mechanik zu einem früheren Zeitpunkt beeinflussen, muss die Spielablaufsteuerung die Wirkung dort berücksichtigen, bevor sie spätere abhängige Zustände verbindlich macht. Das gilt auch für Rückwirkungen auf die Wirtschaft. Ein erst am Ende ausgewertetes Ereignisprotokoll reicht dafür nicht aus.

**Beispiel zur Diskussion.** Ein wirtschaftlicher Engpass wird in Minute 36 wirksam und verlangsamt nach der angenommenen Spielregel einen laufenden Bau. Der Bau wird bis dahin mit dem bisherigen Tempo bewertet. Ab Minute 36 wirkt der Engpass auf die verbleibende Arbeit. Eine bisher erwartete Fertigstellung in Minute 45 kann sich dadurch verschieben. Das kann wiederum ändern, ab wann das neue Gebäude produziert. Im bestehenden Wasserwerk-Abnahmefall gibt es keinen solchen Bau.

**Vorschlag für die spätere Erweiterung.** Ein Lauf kann an einem für die Koordination relevanten Übergang enden und den Zustand zu dieser erreichten Spielzeit liefern. Die Spielablaufsteuerung verarbeitet dort die Folge und setzt den Lauf fort. Die Bedeutung der erreichten Spielzeit und der Ereignisse muss eindeutig bleiben. Ob eine vorzeitige Rückgabe oder eine andere Form der Abstimmung geeigneter ist, wird am ersten konkreten Fall mit Rückwirkung entschieden.

Offen bleiben die Reihenfolge bei gleichzeitigen Wirkungen, die Behandlung mehrstufiger Folgen zur selben Spielzeit und die Entscheidung, welche Übergänge eine Koordination erfordern. Etappe 1 braucht dafür weder eine allgemeine Ereignisschnittstelle noch die Festlegung eines technischen Zeitmodells.
