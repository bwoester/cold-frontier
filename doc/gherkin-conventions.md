# Gherkin feature convention

Write feature files in English. Directly below each `Feature:` heading, add these lines to the feature description:

| Field | What to state |
| --- | --- |
| `Scope:` | **Game behavior**, **Technical design decision**, or **Implementation detail**. Name the affected module or component for technical features. |
| `Purpose:` | Whether the feature specifies expected behavior, verifies behavior, or does both. For a specification still under discussion, state that it is proposed. |
| `Rationale:` | Briefly explain why the expectation belongs to that scope. Reference the relevant rule or decision when it is documented. |

Game behavior features belong in [game-design/scenarios](game-design/scenarios/). They describe expectations that apply across engine implementations. Module-specific features belong with the module's tests. Use a characterization feature to verify observed behavior that has no agreed game rule or technical guarantee behind it.

If the same situation matters at both levels, use separate features for the game rule and the implementation-specific expectation. An executable feature can both specify and verify behavior within its scope.
