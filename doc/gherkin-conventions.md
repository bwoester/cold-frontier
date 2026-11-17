# Gherkin feature convention

Write feature files in English. Directly below each `Feature:` heading, add these lines to the feature description:

| Field | What to state |
| --- | --- |
| `Scope:` | **Game behavior**, **Technical design decision**, or **Implementation detail**. Name the affected module or component for technical features. |
| `Purpose:` | Whether the feature specifies expected behavior, verifies behavior, or does both. For a specification still under discussion, state that it is proposed. |
| `Rationale:` | Briefly explain why the expectation belongs to that scope. Reference the relevant rule or decision when it is documented. |

Game behavior features belong in [game-design/scenarios](game-design/scenarios/). They describe expectations that apply across engine implementations. Module-specific features belong with the module's tests. Use a characterization feature to verify observed behavior that has no agreed game rule or technical guarantee behind it.

If the same situation matters at both levels, use separate features for the game rule and the implementation-specific expectation. An executable feature can both specify and verify behavior within its scope.

## Explicit, comparable acceptance cases

For game behavior scenarios, make all outcome-relevant initial values, actions,
effective game times, update checkpoints and expected results visible in the
feature, its Background or its Examples tables. Each Examples row starts from a
fresh scenario state. A sequence of checkpoints advances the same scenario state
successively to the listed absolute game times; it does not restart at each point.

Step definitions translate those instructions into engine operations and assertions.
They must not silently choose additional cases, random inputs, balancing values,
business rules or comparison tolerances, or implement missing simulation behavior.
A test fixture may prepare the explicitly stated state without exercising unrelated
mechanics, for example a construction whose costs have already been paid.

Express properties such as independence from update partitioning through explicit
Scenario Outline examples for now. These are finite examples of a property, not
exhaustive proofs or generative property tests. Every engine runs the same cases
against the same stated expectations. No custom test framework or cross-language
test protocol is required.

Fractional progress is a semantic assertion, not a required storage field or number
format. Fixed rates and times in examples are test parameters, not universal game
rules. A declared constant rate deliberately isolates production behavior from
other modifiers.

Feature files without engine step bindings remain specifications, not evidence of
passing automated acceptance tests. The existing engine's characterization tests
do not establish compliance with these game behavior scenarios.
