---
status: observed-prototype
type: architecture-notes
---
# Technischer Ist-Zustand (keine Soll-Architektur)

Dieser Eintrag fasst **beobachteten Prototypcode** zusammen. Er ersetzt keinen Architekturentwurf und begründet keine Technologieentscheidung.

- `lib/`: Java-21-Library mit `TickCoordinator`, `TickComponent`, `Planet`, Services für Produktion, Gebäude, Baufortschritt, Buchungen und Inputs.
- `TickCoordinator`: Ein globaler Tick führt Phasen über alle registrierten Komponenten aus (Input, Ressourcen, Gebäude, Bewegung, Konflikte, Sichtbarkeit, Persistenz, Cleanup).
- `Planet`: orchestriert spezialisierte Services; Produktionsberechnung derzeit Summation von Gebäuderaten.
- `Value<T>`: zustandsorientierter Zugriff; In-Memory- und NATS-KV-Implementierungen. Ein historisierter Wert ist **nicht automatisch** ein fachliches Domain Event.
- `AccountingService`: prüft und verändert Spieler- und Planetenkonten; Mehrfachschreibvorgänge sind im gezeigten Ablauf nicht als gemeinsame Transaktion abgesichert.
- `backend/`: Quarkus-Prototyp mit REST und OIDC-Abhängigkeiten.
- `frontend/`: Vue-SPA; öffentliche und geschützte Seiten, Spielnavigation.

## Bekannte Lücken / Diskussionspunkte

- Engpässe und Ressourcenerschöpfung nicht fachlich fertig spezifiziert.
- Baufortschritt-/Tick-Zusammenspiel sowie konkurrierende Aufträge end-to-end prüfen.
- Keine akzeptierte Entscheidung zu Event Sourcing, Zeitmodell, Datenbank, Messaging, RPC, Backend-Framework oder CSS-Ansatz.
- Die framework-unabhängige fachliche Engine ist **Architekturziel**, aber die aktuelle `lib` enthält auch NATS-spezifische Abhängigkeiten.

## Verweise

[[../game-design/economy|Planetare Wirtschaft]] · [[../notes|Frühere technische Überlegungen]]

Quellen: `lib/src/main/java/de/bwoester/coldfrontier/`, `backend/build.gradle.kts`, `frontend/package.json` (Repositorystand bei Erstellung dieses Dokuments).
