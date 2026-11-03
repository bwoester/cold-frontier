---
status: open
type: architecture-notes
---
# Auftragsreihenfolge und konkurrierende Aktionen

Diese offene Architekturfrage betrifft das gesamte Spiel. Ein Spieler kann über mehrere Tabs oder Geräte handeln. Eingabe, Eingang am Server, Verarbeitung und Wirksamkeit in der Spielwelt können in unterschiedlichen Reihenfolgen liegen. Aus den Requests allein lässt sich keine gemeinsame Klickreihenfolge über alle Clients ableiten.

## Anforderungen und offene Entscheidungen

- Festlegen, welche Eingabereihenfolge innerhalb eines Clients erhalten bleiben muss und welche Reihenfolge zwischen Clients verbindlich ist.
- Für konkurrierende Aufträge mit demselben Spielzeitpunkt eine eindeutige und im Audit nachvollziehbare Vorrangentscheidung ermöglichen. Der fachliche Vorrang kann je Mechanik unterschiedlich sein.
- Wiederholungen desselben Requests dürfen dieselbe Aktion höchstens einmal wirksam machen.
- Gemeinsame Bestände und begrenzte Plätze müssen auch bei konkurrierenden Aktionen konsistent bleiben. Welche Aktionen eine gemeinsame Reihenfolge brauchen und wo unabhängige Verarbeitung möglich ist, bleibt offen.

## Anforderungen an das Audit

Bei konkurrierenden Aktionen soll das Audit den wirksamen Spielzeitpunkt, die für die Entscheidung maßgebliche Reihenfolge und die angewandte Vorrangregel nachvollziehbar machen. Dazu gehören die betroffenen Aufträge und ihre Ergebnisse. Wenn ein Auftrag wegen eines Konflikts abgelehnt wird, soll der Grund erkennbar sein. Ein erneut gesendeter Request muss sich derselben Aktion zuordnen lassen. Ausgelöste Zustandsänderungen sollen auf den wirksamen Auftrag zurückführbar sein.

Eingang am Server und Wirksamkeit in der Spielwelt sind unterschiedliche Zeitpunkte. Welche zusätzlichen Zeitpunkte erfasst werden, bleibt offen.

## Prüffälle

- [S3 – Zwei gleichzeitig konkurrierende Ausgaben](../game-design/economy.md#s3--zwei-gleichzeitig-konkurrierende-ausgaben) prüft konkurrierende Zugriffe auf denselben Bestand.
- [S16 – Zwei Gründungsflüge bei einem freien Kolonieplatz](../game-design/economy.md#s16--zwei-gründungsflüge-bei-einem-freien-kolonieplatz) prüft Vorrang, Mehrfachbelegung und wiederholte Requests.

Die verbindliche Reihenfolge, der Umgang mit verspäteten Requests und die technische Umsetzung sind offen.
