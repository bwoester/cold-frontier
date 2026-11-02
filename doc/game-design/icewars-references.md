---
status: research
type: source-notes
---
# IceWars – historische Inspiration (keine Soll-Spezifikation)

Die folgenden Informationen stammen aus historischen Webquellen, bereitgestellten Unterlagen und einem ausdrücklich gekennzeichneten Erfahrungsbericht zum ursprünglichen IceWars. Sie beschreiben nicht automatisch Regeln für Cold Frontier. Zahlen oder Spielmechaniken können sich zwischen IceWars-Runden verändert haben.

Das Inventar der bereitgestellten Unterlagen sowie der Fortschritt von Analyse, Dokumentation und Archivierung stehen im [[sources/icewars-catalog|Quellenkatalog]].

## Belastbare Orientierung

- Besiedeln von Planeten, Ressourcenkonkurrenz, Schiffe, Forschung und komplexere Bau-/Technologieoptionen gehörten zur Selbstbeschreibung. Der Humor und die Selbstironie waren ausdrücklich Teil der Spielidentität. [GamesSphere – Spielbeschreibung](https://www.gamessphere.de/games/icewars)
- Der Spielbetrieb war rundenbasiert (in der Übersicht: rund 9–12 Monate je Runde), während das Spiel in Echtzeit lief. **Das legt kein Tick-Modell für Cold Frontier fest.** [GamesSphere – Überblick](https://www.gamessphere.de/games/icewars)
- 2009 wurden unter anderem Forschungsabläufe geändert, Aliens und Piraten als NPCs sowie Quests angekündigt. [GamesSphere – Rundenneustart](https://gamessphere.de/games/icewars/news/2480_rundenneustart_mit_neuen_features)
- 2008 gab es eigene Einsteiger-Galaxien mit Schutzmechaniken. [GamesSphere – Neueinsteiger](https://gamessphere.de/games/icewars/news/1577_neueinsteiger_in_zukunft_mit_mehr_schutz)
- 2010 wurde eine Aufrüstung vorhandener Schiffe mit Forschungsvoraussetzungen, Baukosten und zeitabhängigen Modifikatoren beschrieben. [GamesSphere – Schiffsupgrades](https://gamessphere.de/games/icewars/news/3565_pimp_my_shuttle_-_aus_alt_mach_neu)
- Zur neunten Runde 2009 meldete das IceWars-Team **mehrere tausend teilnehmende Spieler**. Die ursprünglich vermutete Größenordnung unter 1.000 ist daher keine belastbare historische Grenze. [GamesSphere – Rundenstart](https://gamessphere.de/games/icewars/news/2510_erfolgreicher_start_in_die_neunte_runde)

## Erfahrungsbericht – Eisförderung und Ausbaukonkurrenz

**Quelle:** Benjamin Wöster, Projektgespräch vom 2026-11-01. Historische Spielerfahrung; nicht unabhängig durch die bereitgestellten XMLs verifiziert. Spielrunde beziehungsweise Version noch nicht zugeordnet.

- Sinkende Ergiebigkeit betraf laut Erfahrungsbericht nur Eis. Die Eisdichte bestimmte proportional die Förderleistung: Bei 15 Prozent Dichte förderte dieselbe Minenausstattung halb so viel wie bei 30 Prozent.
- Die Dichte sank auf gewöhnlichen Planeten nicht unter 5 Prozent, auf speziellen Eisplaneten nicht unter 15 Prozent. Auch an dieser Grenze blieb Förderung möglich.
- Die Startdichte des Startplaneten lag **nach unsicherer Erinnerung** bei 30 Prozent; nicht als gesicherter Wert verwenden.
- Der Effekt war über Monate kaum spürbar. Beim Eintreten deutlicher Versorgungsprobleme verfügten Spieler bereits über hunderte Eisminen. Weitere Minen, Raubzüge und die Wahl eines Eisplaneten als nächste Kolonie waren typische Reaktionen.
- Eine Vervielfachung der Minenzahl hätte Monate gedauert und den Bau anderer Gebäude für Forschung, Militär und Wirtschaft verdrängt. Die Bauzeit war deshalb ein wesentlicher Engpass.
- Nach persönlicher Beobachtung kolonisierten auch gut organisierte Spieler selten mehr als sechs Planeten; von sieben oder acht wurde berichtet, Anfänger kamen teilweise auf vier. Das ist keine belegte harte Spielgrenze.

**Ergänzende Quellen, getrennt behandeln:**

- [Evo-1-Tech-Tree](https://drive.google.com/file/d/1yt-JLQm8b-CSgF6wfcE693S_PHFz6i_q/view): Community-Grafik mit großer Eismine unter „Robotik“; enthält keine Förderwerte.
- [Resourcenabbau](https://docs.google.com/spreadsheets/d/1Wng7Wi-kAfF1JS_VECUQVXRTWIYlHXlCo7YKTTP_vnA/edit): eigener früherer Modellierungsversuch, **kein Beleg für die damalige IceWars-Formel**. Die gelesenen Zahlen bilden Förderung proportional zum relativen Restvorkommen ab; die anfängliche Dichte 1 ist nicht ohne Weiteres eine absolute planetare Eisdichte von 100 Prozent.

Die daraus entwickelte Cold-Frontier-Designrichtung und das Schutzziel für Abwesenheit stehen in [[economy#Sinkende Ergiebigkeit und Zeit als Ressource]]. Historische Zahlen werden dadurch nicht automatisch übernommen.

## Rekonstruierte historische Einheiten

Der [XML-Einheitenkatalog](../sources/icewars-ships.md) bewahrt alle 64 Export-Einträge mit Materialkosten, Dauer und baubar. Die [Forschungszuordnung](../sources/icewars-ship-research.md) ergänzt für jede ID einen Befund: 37 namensbasierte Zuordnungen in Serie B1–B5 und eine zusätzliche in A2, 26 ohne belastbaren Forschungsbeleg. Forschungsvorgänger, explizite Zusatzfelder, ausgewählte indirekte Gebäudeanforderungen und unbekannte Bauvoraussetzungen werden getrennt. Versions- und Namenskonflikte bleiben sichtbar.

**Nur historische Referenz:** Für Cold Frontier sind diese Einheiten Inspiration; weder Liste, Kosten noch Voraussetzungen gelten als beschlossene Spielregeln.

## Erfahrungsbericht – Bau-Frei-Minuten

**Quelle:** Benjamin Wöster, Projektgespräch vom 2026-11-02. Historische Erinnerung an die BFM-Mechanik; nicht durch die bisher ausgewerteten Dateien unabhängig verifiziert. Keine Übernahmeentscheidung für Cold Frontier.

- Bau-Frei-Minuten (BFM) dienten vor allem Späteinsteigern zum schnelleren Aufholen in einer fortgeschrittenen Runde.
- Gebäudebauten ließen sich mit einem BFM-Guthaben beschleunigen. Die Differenz zur normalen Bauzeit wurde vom BFM-Konto abgezogen.
- Nach unsicherer Erinnerung war eine Beschleunigung auf eine verbleibende beziehungsweise minimale Bauzeit von **5 oder 15 Minuten** möglich. Exakter Grenzwert und genaue Anwendung sind nicht gesichert.
- Vergabe, Gültigkeit, Einsatzzeitpunkt, Rückzahlung bei Abbruch, Wechselwirkung mit Engpässen und Anwendbarkeit auf andere Auftragstypen sind offen.
- Die Mechanik zeigt einen explizit geführten Beschleunigungsvorrat. Sie belegt nicht, dass normale vergehende Zeit wie ein lagerbarer Ressourcenbestand modelliert werden muss.

Heutige Baukosten- und Abbruchregeln stehen in [[economy#Kosten, Abbruch und Fortschritt]].

## Erfahrungsbericht – planetare Bauwarteschlangen

**Quelle:** Benjamin Wöster, Projektgespräch vom 2026-11-02. Historische Erinnerung; nicht unabhängig durch die vorliegenden Dateien verifiziert. Diese Details sind noch keine Übernahmeentscheidung für Cold Frontier.

- Jeder Planet hatte eine eigene Bauwarteschlange.
- Die Ressourcen wurden nach Erinnerung bereits beim Einreihen des Auftrags abgezogen, nicht erst beim tatsächlichen Arbeitsbeginn.
- Das erste Gebäude wurde zu regulären Kosten und regulärer Bauzeit beauftragt; das zweite eingereihte Gebäude kostete **10 Prozent mehr Ressourcen und Zeit**.
- Manuelles Beauftragen nach Leeren der Schlange vermied den Warteschlangenaufschlag. Vorausplanung war damit teurer, ermöglichte aber längere Abwesenheit ohne ständiges Einloggen.
- Ein zusätzlicher Warteschlangenplatz war über später im Spiel verfügbare **Genetik-Optionen** erreichbar. Grundkapazität, genaue Option und Zahl möglicher Erweiterungen sind nicht gesichert.
- Staffelung für weitere Positionen (z. B. gleicher Aufschlag, linear oder multiplikativ), Umfang der betroffenen Ressourcen und Rundung sind offen.
- Ebenfalls offen: Blieb der beim Einreihen bestimmte Aufschlag beim Nachrücken bestehen? Wie wirkten sich Abbruch oder Umordnen auf Kosten und Zeit anderer Aufträge aus? Galt die teilweise Rückzahlung auch für noch nicht begonnene Aufträge?

**Analyse für die weitere Diskussion:** Der Aufschlag bewertet Abwesenheit und Komfort wirtschaftlich. Er gibt häufig aktiven Spielern neben früherer Reaktion auch einen Ressourcen- und Zeitvorteil. Ob dieser Aktivitätsvorteil zu Cold Frontier passt, ist als Spielziel zu klären; die historischen 10 Prozent legen das nicht fest.

## Mögliche Inspirationsfelder – ausdrücklich offen

- Tiefer Technologiebaum und ungewöhnliche Gebäude
- Wirtschaftliche Nebenwirkungen von Bevölkerungs-/Sozialmechaniken
- Diplomatie, Gemeinschaft und Allianzen
- Asymmetrische Schiffe, Piraten, Quests und Events
- Humor über Berater, Ereignistexte und Spielmechaniken

## Noch zu recherchieren

- Rohstoffliste einschließlich VV4A in der bereitgestellten Ressourcen-XML gelesen; siehe [[sources/icewars-catalog#Offizielle XMLs laut Projektinhaber (IW-01 bis IW-03)]]. Ursprünglichen Endpunkt und Spielversion weiter klären; Produktionsketten sind daraus noch nicht belegt.
- Weitere Gebäude- und Forschungsabhängigkeiten: Einheiten-Zuordnungen teilweise rekonstruiert; vollständige transitive Ketten, konkrete Bauwerften und fehlende/unleserliche späte Tech-Tree-Seiten bleiben offen
- Bau-, Transport- und Kampfregeln je Spielversion

**Quellenkritik:** Ähnliche Spiele (beispielsweise XWars oder GalaxyWars) nicht als Beleg für IceWars verwenden.
