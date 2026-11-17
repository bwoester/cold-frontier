---
status: open
type: architecture-notes
---
<a name="auftragsreihenfolge-und-konkurrierende-aktionen"></a>

# Command Ordering and Concurrent Actions

This open architecture question affects the entire game. A player can act through multiple tabs or devices. Input, server receipt, processing, and effective game time can occur in different orders. The requests alone cannot establish a common click order across all clients.

<a name="anforderungen-und-offene-entscheidungen"></a>

## Requirements and Open Decisions

- Determine which input order must be preserved within a client and which order takes precedence across clients.
- Apply an unambiguous and auditable priority rule to competing commands with the same game time. The priority may differ by mechanic.
- Retrying the same request must cause the action to take effect at most once.
- Keep shared stocks and limited slots consistent under concurrent actions. Which actions require a common order and which can be processed independently remains open.

<a name="anforderungen-an-das-audit"></a>

## Audit Requirements

For concurrent actions, the audit trail should show the effective game time, the order used to make the decision, and the priority rule applied. It should include the commands involved and their outcomes. If a command is rejected because of a conflict, the reason should be visible. A retried request must be traceable to the same action. Resulting state changes should be traceable to the command that took effect.

Server receipt and effectiveness in the game world occur at separate times. Which additional timestamps to record remains open.

<a name="prüffälle"></a>

## Test Cases

- [S3 – Two Concurrent Spending Attempts](../game-design/economy.md#s3--two-concurrent-spending-attempts) checks competing access to the same stock.
- [S16 – Two Colonization Flights with One Free Colony Slot](../game-design/economy.md#s16--two-colonization-flights-with-one-free-colony-slot) checks priority, double allocation, and retried requests.

The binding order, handling of late requests, and technical implementation remain open.
