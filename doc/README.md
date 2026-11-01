# Cold Frontier – Wissensbasis

Diese Dokumentation ist bewusst **ein wachsendes Arbeitsmodell**, kein fertiges Pflichtenheft und keine verbindliche Soll-Architektur.

## Einstieg in Obsidian

Öffne das Repository (oder den Ordner `doc/`) als Obsidian-Vault. Einstieg: [[game-design/overview]] oder die visuelle Karte [[game-design/game-design.canvas]]. Interne Links beziehen sich auf Dateien in `doc/`.

## Bereiche

- [[game-design/overview|Spielmechaniken]] – Themenkarte und aktueller Erkenntnisstand.
- [[game-design/economy|Planetare Wirtschaft]] – erster vertiefter Bereich mit Fragen und Beispielen.
- [[sources/icewars-catalog|Alte IceWars-Unterlagen – Fortschritt]] – Quelleninventar, Analyse, Dokumentation und Archivierung.
- [[game-design/icewars-references|IceWars-Recherche]] – historische Inspiration, **keine** Cold-Frontier-Anforderungen.
- [[architecture/current-state|Technischer Ist-Zustand]] – beobachteter Prototyp, keine Architekturentscheidung.
- [[notes|Frühere technische Notizen]] – unveränderte Ideensammlung.
- [[personas/overall-tone|Erzählton]] und [[personas/interpersonal-dynamics|Berater-Beziehungen]] – vorhandene Figurenkonzepte.

## Qualitätspriorität

**Vom Projektinhaber am 2026-11-01 klargestellt:** Das Simulationssystem soll einfach verständlich, nachvollziehbar und beherrschbar sein. Technische Raffinesse oder spekulative Ressourceneinsparungen rechtfertigen keine schwer nachvollziehbare Verarbeitung. Zeitmodell und Ausführungsstrategie werden anhand fachlicher Szenarien und realistischer Betriebsziele bewertet; weder globale Ticks noch Lazy-Fortschreibung sind damit beschlossen oder grundsätzlich ausgeschlossen. Die Erfahrungen aus dem früheren Lazy-Entwurf stehen im [[sources/icewars-catalog#Einordnung RD-01 – früherer Lazy-Ansatz und heutige Priorität|Quellenkatalog]].

## Konventionen

**Evidenzkennzeichnungen:** `Projektziel` (vom Projekt formuliert), `Code-Ist` (im Quellcode beobachtet), `IceWars-Quelle` (historische Anregung), `Vorschlag`, `Offene Frage`. Aus einem Code-Ist oder einer historischen Quelle folgt **keine** verbindliche Spielregel.

**Reifegrad:** `Idee` → `In Klärung` → `Entschieden`. Ohne ausdrücklich getroffene Entscheidung bleibt eine Regel offen. ADRs und formale Requirement-IDs werden nur bei tatsächlichem Bedarf eingeführt.

**Arbeitsweise:** Von der Übersicht zu einer Mechanik navigieren; Spielziel, Regeln, Beispiele und Fragen dort in Markdown präzisieren. Canvas ist eine zusätzliche Sicht, nicht die zweite Wahrheit. Neue belastbare Erkenntnisse in den entsprechenden Markdown-Seiten festhalten. Änderungen via Git versionieren.

**Quellenarbeit:** Nach Bearbeitung alter Unterlagen den [[sources/icewars-catalog|Quellenkatalog]] mit Ergebnislinks aktualisieren. Sichtung, fachliche Übernahme und Archivierung sind getrennte Fortschritte.

**Zusammenarbeit im Git:** Kleine, überschaubare Änderungen dürfen direkt auf `main` erfolgen. Größere Änderungen zunächst gemeinsam abstimmen; Branch und Pull Request nach Umfang wählen. Beim Aktualisieren bestehender Dateien parallele Änderungen berücksichtigen.

**Nächster Fokus:** planetare Wirtschaft – Abhängigkeiten, Bestände, Energie und Ressourcenerschöpfung; danach Zeit- und Konkurrenzverhalten. 
