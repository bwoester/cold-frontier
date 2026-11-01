# Cold Frontier – Wissensbasis

Diese Dokumentation ist bewusst **ein wachsendes Arbeitsmodell**, kein fertiges Pflichtenheft und keine verbindliche Soll-Architektur.

## Einstieg in Obsidian

Öffne das Repository (oder den Ordner `doc/`) als Obsidian-Vault. Einstieg: [[game-design/overview]] oder die visuelle Karte [[game-design/game-design.canvas]]. Interne Links beziehen sich auf Dateien in `doc/`.

## Bereiche

- [[game-design/overview|Spielmechaniken]] – Themenkarte und aktueller Erkenntnisstand.
- [[game-design/economy|Planetare Wirtschaft]] – erster vertiefter Bereich mit Fragen und Beispielen.
- [[game-design/icewars-references|IceWars-Recherche]] – historische Inspiration, **keine** Cold-Frontier-Anforderungen.
- [[architecture/current-state|Technischer Ist-Zustand]] – beobachteter Prototyp, keine Architekturentscheidung.
- [[notes|Frühere technische Notizen]] – unveränderte Ideensammlung.
- [[personas/overall-tone|Erzählton]] und [[personas/interpersonal-dynamics|Berater-Beziehungen]] – vorhandene Figurenkonzepte.

## Konventionen

**Evidenzkennzeichnungen:** `Projektziel` (vom Projekt formuliert), `Code-Ist` (im Quellcode beobachtet), `IceWars-Quelle` (historische Anregung), `Vorschlag`, `Offene Frage`. Aus einem Code-Ist oder einer historischen Quelle folgt **keine** verbindliche Spielregel.

**Reifegrad:** `Idee` → `In Klärung` → `Entschieden`. Ohne ausdrücklich getroffene Entscheidung bleibt eine Regel offen. ADRs und formale Requirement-IDs werden nur bei tatsächlichem Bedarf eingeführt.

**Arbeitsweise:** Von der Übersicht zu einer Mechanik navigieren; Spielziel, Regeln, Beispiele und Fragen dort in Markdown präzisieren. Canvas ist eine zusätzliche Sicht, nicht die zweite Wahrheit. Neue belastbare Erkenntnisse in den entsprechenden Markdown-Seiten festhalten. Änderungen via Git versionieren.

**Nächster Fokus:** planetare Wirtschaft – Abhängigkeiten, Bestände, Energie und Ressourcenerschöpfung; danach Zeit- und Konkurrenzverhalten. 
