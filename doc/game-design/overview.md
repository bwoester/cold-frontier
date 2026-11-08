---
status: in-klaerung
type: game-design
---
# Spielmechaniken – Übersicht

> [!important] Quellenlage
> Diese Karte sammelt **Bereiche und Ideen**, keine bereits entschiedenen Regeln. Status und Herkunft stehen bei den Themen. Für den Ist-Zustand siehe [[architecture/current-state]].

## Mechaniken

| Bereich | Heutiger Stand | Weiterführend |
| --- | --- | --- |
| Evolution / Balancing / Spielrhythmus | Projektziele und Evo-1–5-Vorschlag; ganzheitliche Gebäudeeffizienz, Bedienlast und historischer Abgleich in Klärung | [Evolution und Balancing](evolution-and-balancing.md) |
| Planeten / Kolonisation | Projektziel; `Planet` im Prototyp | [[economy]] |
| Ressourcen / Produktion / Engpässe | Projektziel und Teile im Code; **erste Vertiefung** | [[economy]] |
| Gebäude / Bauaufträge | Gebäudearten, Queue und Progress im Code; Regeln offen | [[economy#Gebäude und Bau]] |
| Forschung / Technologien | Projektziel; historische IceWars-Inspiration | [[icewars-references]] |
| Handel / Transport / Markt | Projektziel; Mechanik noch nicht spezifiziert | [[economy#Handel und Transport]] |
| Flotten / Schiffe / Bewegungen | Projektziel; Tick-Phasen als Code-Ist/Notiz | [[architecture/current-state]] |
| Kämpfe / Kriege | Projektziel; Mechanik noch nicht spezifiziert | [[icewars-references]] |
| Sichtbarkeit / Sensorik | Phasen und TODOs in Code und Notizen | [[architecture/current-state]] |
| Spielzeit / Ereignisse | Globaler phasenbasierter Ticker im Prototyp; Modell offen | [[architecture/current-state]] |
| Berater / Narration / Humor | Vorhandene Figuren- und Ton-Dokumente | [[personas/overall-tone]], [[personas/interpersonal-dynamics]] |
| Spieler / Allianzen / soziale Interaktion | Spieler vorhanden, Allianzen historisch belegt; Regeln offen | [[icewars-references]] |

## Bewusst nicht entschieden

- Globale oder lokale Ticks, ereignisdiskrete Berechnung oder Kombination?
- Wo verlaufen Konsistenzgrenzen zwischen Planet, Spieler, Flotte und Markt?
- Wie tief sollen Wirtschaftsketten und Ressourcenerschöpfung tatsächlich gehen?
- Welche Spielmechaniken aus IceWars übernehmen wir, welche verändern wir bewusst?

## Erste zu untersuchende Geschichte

Ein Spieler baut auf seinem Planeten eine Mine. Die Mine verbraucht Energie und fördert Eisen. Was geschieht, wenn Energie knapp wird oder das Eisenvorkommen zur Neige geht? Siehe [[economy#Erste Szenarien]].
