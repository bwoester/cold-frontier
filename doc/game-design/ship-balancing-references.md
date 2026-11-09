---
status: in-klaerung
type: game-design-research
---
# Schiffbalancing – offene Referenzen und Geleitschutz

**Stand:** 2026-11-08. Recherchevorschlag; keine Übernahme eines Kampfsystems oder fremder Daten beschlossen.

## Ausgangsidee und gewünschte Eigenschaften

**Idee des Projektinhabers:** Werte vorhandener, gut ausbalancierter Einheiten aus anderen Spielen als Startpunkt verwenden, sofern Lizenz und fachliche Übertragbarkeit passen. Gewünscht sind Geschwindigkeit, Initiative, Angriff und Verteidigung, gegebenenfalls nach Schiffs- oder Waffenklassen. Große Schiffe könnten von genügend kleinen Eskorten profitieren.

**Analyse:** Ein offener Datensatz spart die erste Parametrisierung, überträgt aber nicht automatisch dessen Balancing. Dieses entsteht gemeinsam aus Kosten, Verfügbarkeit, Zielwahl, Reihenfolge, Trefferregeln, räumlicher Bewegung und Verlustfolgen. Die folgenden Kandidaten sind fachlich begründete Referenzen, keine unabhängig als überlegen oder vollständig ausgewogen nachgewiesenen Systeme.

## Recherchebefunde

| Kandidat | Passender Inhalt | Lizenzbefund | Übertragungsgrenze |
| --- | --- | --- | --- |
| FreeOrion | Raumschiffe und Komponenten; Struktur, Schilde, Waffen und Schusszahl, Geschwindigkeit, Tarnung und Ortung. Dokumentierte Änderungen beschreiben Jäger und spezialisierte Flak. | default/COPYING nennt CC BY-SA 3.0 für Spielinhalte einschließlich Funktionsskripten; Skripte zusätzlich GPL v2. | Komponentenmodell statt bloß fester Schiffsklassen. Konkrete Version, Technologieeffekte und Kampfauflösung müssen zusammen gelesen werden. Keine allgemein passende numerische Initiative nachgewiesen. |
| Battle for Wesnoth | Explizite Einheitenkosten, Lebenspunkte, Bewegung, Waffen, Schaden und Schlagzahl sowie Schadenstypen und Resistenzen; Firststrike als Angriffseigenschaft. | Offizielle Copyright-Seite: Code einschließlich WML und Daten GPL v2 oder später; einzelne abweichende Hinweise beachten. Wiki-Texte separat GFDL. | Geländebezogene Ausweichchance, Tageszeit, Zugfolge und Gegenschläge beeinflussen die Stärke. Firststrike ist kein allgemeiner Initiativewert. |
| Zero-K | Raider-, Riot-, Skirmisher-, Assault- und Artillerierollen; Kombinationen mit gegenseitiger Absicherung. | Offizielle Projektinformation beschreibt Lua-Code grundsätzlich als GPL mit Ausnahmen. Eine Entwicklerauskunft nennt überwiegend GPL-2.0. Keine pauschale Freigabe aller Inhalte; einzelne Dateien noch nicht geprüft. | Echtzeitbewegung, Reichweite, Geschosse und Steuerung tragen das Kontersystem. Zahlen verlieren bei abstrakter Flottenauflösung einen Teil ihrer Bedeutung. |

**Empfehlung als Analyse:** FreeOrion zuerst als fachlich nahe Schiffreferenz untersuchen; Wesnoth als Alternative für überschaubare diskrete Kampfprofile; Zero-K besonders für Rollen und Zusammenspiel. Werte verschiedener Quellen nicht ohne gemeinsamen Kampfvertrag zu einem vermeintlich fertigen Balancing mischen.

Für keinen Kandidaten wurde in dieser Recherche ein vollständiger, an einen konkreten Commit gebundener Einheitenbestand extrahiert oder unter Cold-Frontier-Regeln getestet. Aussagen über die Qualität des übertragenen Balancings bleiben deshalb Hypothesen. Ältere Designseiten oder Changelog-Einträge belegen keine unveränderten aktuellen Regeln.

## Lizenz und Herkunft bei einer späteren Übernahme

Die genannten Lizenzen sind Bedingungen für Wiederverwendung, keine Aufgabe von Rechten. FreeOrions Inhaltslizenz erlaubt auch kommerzielle Bearbeitung unter ihren Bedingungen. Dazu gehören insbesondere Namensnennung, Lizenzverweis, Änderungskennzeichnung und ShareAlike bei Weitergabe von Bearbeitungen. Welche Teile einer konkreten Integration als Bearbeitung einzuordnen sind, wird hier nicht pauschal für das Gesamtprojekt entschieden.

Bei einer tatsächlichen Datenübernahme sind genaue Quelldateien und Version, Urheberhinweise, Lizenz, Änderungen und eingebundene Abhängigkeiten zu dokumentieren. Makros und Forschungsmodifikatoren können für die effektiven Werte relevant sein. Es wird weder behauptet, einzelne Zahlen seien stets geschützt, noch dass das Kopieren ganzer Tabellen durch Umbenennen automatisch frei von Pflichten wäre. GPL- und Inhaltslizenzpflichten anhand der konkreten Übernahme prüfen; fremde Grafiken, Namen und Beschreibungen nicht automatisch mitübernehmen.

## Vor Wertevergleich fachlich klären

- **Geschwindigkeit:** Reisegeschwindigkeit gegenüber taktischer Beweglichkeit. Beides muss nicht derselbe Wert sein.
- **Initiative:** Reihenfolge, erster Schuss oder Häufigkeit der Aktionen? Bei Reihenfolge ist relevant, ob zerstörte Gegner noch zurückschießen.
- **Angriff:** Schaden pro Treffer, Zahl der Schüsse und Trefferchance unterscheiden.
- **Verteidigung:** Strukturpunkte, Ausweichen, Panzerung und Schilde haben unterschiedliche Wirkungen. Nicht alle brauchen eigene Werte.
- **Kosten:** Bauressourcen und Bauzeit sowie erst noch zu bestimmender Betrieb/Unterhalt. Gleiche Stückzahl ist kein fairer Vergleich unterschiedlicher Schiffsklassen.
- **Zielwahl und Konter:** Welche Waffen dürfen welche Gegner angreifen, und wie werden Ziele innerhalb der Flotten verteilt?

Die Liste ist ein Fragenkatalog, keine verbindliche Einführung sämtlicher Attribute. Für den Einstieg nur diejenigen Größen vorsehen, die konkrete gewünschte Entscheidungen tragen.

## Geleitschutz als Vorschlag

**Spielerziel aus der Idee:** Kleine Schiffe sollen auch neben großen Schiffen einen dauerhaften Nutzen haben. Gemischte Flotten können großen Einheiten Schutz bieten.

Zwei Alternativen, noch nicht entschieden:

1. **Schutz durch spezialisierte Kampfwirkung:** Eskorten bekämpfen Bomber, Jäger oder andere konkrete Bedrohungen. Der Schutz folgt aus dem Kampfverlauf, sofern Zielwahl und Reihenfolge ihn ermöglichen.
2. **Abstrakter Geleitbonus:** Geeignete, einsatzfähige Eskorten stellen Schutzkapazität bereit; große Schiffe erzeugen Schutzbedarf. Abdeckung und Bonus sind begrenzt.

Illustrative Formel für Alternative 2, keine beschlossene Regel:

```text
Abdeckung = min(1, wirksame Schutzkapazität / Schutzbedarf)
Bonus     = maximaler Bonus * Abdeckung
```

Ohne Schutzbedarf wird kein Geleitbonus angewandt. Die Kapazität darf im selben Wirkungsbereich nicht mehreren Schiffen jeweils vollständig zugerechnet werden. Kleine Einheiten zählen nach tatsächlicher Schutzfähigkeit; bloße Stückzahl soll keine unbegrenzt stapelbare Wirkung erzeugen. Ob die Abdeckung flottenweit oder je geschütztem Schiff gilt, bleibt offen.

**Zu klären:** Schutz gegen welche Waffen oder Rollen; erforderliche Einsatzfähigkeit; Zeitpunkt der Neuberechnung nach Verlusten; Grenzen und Gegenmaßnahmen. Ein pauschaler Schutzbonus gegen alles könnte Geleit zur Pflicht machen und Spezialisierung verdrängen. Ein Bonus zusätzlich zu bereits wirksamem Abfangen kann denselben Schutz doppelt belohnen.

**Vorgeschlagene Szenarien:** Große Einheiten allein, mit zu wenig/passendem/überzähligem Geleit; Verlust von Eskorten während des Gefechts; viele billige ungeeignete Schiffe; mehrere Großschiffe mit gemeinsamem Geleit. Vergleich bei gleichem Gesamtbudget gegen verschiedene gegnerische Zusammensetzungen. Geleit ist attraktiv, wenn es den vorgesehenen Bedrohungen hilft; es muss nicht gegen jede Flotte die beste Investition sein.

## Nächster überschaubarer Schritt

Nach Klärung des groben Kampfablaufs einen Kandidaten und dessen feste Version wählen. Drei bis fünf Rollen mit vollständigen wirksamen Profilen und Kosten rekonstruieren. Zuerst unter den zugehörigen Regeln als Referenz prüfen, dann Anpassungen an Cold Frontier und Geleit jeweils einzeln bewerten. Keine direkte Implementierungsentscheidung.

## Geprüfte Quellen

Abruf am 2026-11-08; Links auf bewegliche Projektstände sind noch keine fixierte Datenversion.

- [FreeOrion README und Inhaltsstruktur](https://github.com/freeorion/freeorion)
- [FreeOrion Inhaltslizenz: default/COPYING](https://github.com/freeorion/freeorion/blob/master/default/COPYING)
- [FreeOrion FOCS-Datenmodell](https://www.freeorion.org/index.php/FOCS_Scripting_Details)
- [FreeOrion Changelog: unter anderem Jäger, Flak und Schildänderungen; historische Einträge](https://github.com/freeorion/freeorion/blob/master/ChangeLog.md)
- [Wesnoth Copyrights: Code, WML, Daten und Wiki getrennt](https://wiki.wesnoth.org/Wesnoth:Copyrights)
- [Wesnoth UnitTypeWML](https://wiki.wesnoth.org/UnitTypeWML)
- [Wesnoth Verteidigung und Resistenz](https://wiki.wesnoth.org/Defense_and_resistance)
- [Wesnoth Angriffseigenschaften einschließlich Firststrike](https://wiki.wesnoth.org/AbilitiesWML)
- [Zero-K Klassen und Rollen](https://zero-k.info/mediawiki/Unit_classes)
- [Zero-K offizielle Projektvorstellung mit Lua-Lizenzhinweis](https://zero-k.info/Forum/Thread/20428)
- [Zero-K Entwicklerauskunft mit Verweis auf Dateiausnahmen](https://zero-k.info/PostHistory/ViewEntry/93546)
- [CC BY-SA 3.0: Bedingungen](https://creativecommons.org/licenses/by-sa/3.0/)
