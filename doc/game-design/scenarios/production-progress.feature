Feature: Production progress across updates and building completion
  Scope: Game behavior.
  Purpose: Specifies agreed production behavior using explicit examples.
  Rationale: Available resources and retained production progress must not depend
  on the engine or the frequency of updates. See economy.md, section
  "Bauabschluss und Produktionsfortschritt".
  Rates and times below are test parameters, not general building balance values.
  These scenarios are specifications; engine step bindings are not provided yet.

  Background:
    Given a colony has 0 units of iron and no other producing buildings
    And a test mine produces iron at a constant rate of 3 units per hour while operational
    And the test mine requires no input resources for production
    And iron storage has capacity for 100 units
    And no actions consume or transfer iron during the scenario
    And all operating conditions remain satisfied throughout the scenario
    And the simulation starts at minute 0
    And production progress starts at 0 units

  Scenario Outline: Intermediate updates preserve whole units and fractional progress
    Given the test mine is already completed and operational at minute 0
    When the simulation advances through minutes "<checkpoints>"
    Then at minute <target> the colony has exactly <available> whole units of iron available
    And the test mine retains production progress of <progress> iron units

    Examples:
      | checkpoints                   | target | available | progress |
      | 0                             | 0      | 0         | 0        |
      | 10                            | 10     | 0         | 0.5      |
      | 5, 10                         | 10     | 0         | 0.5      |
      | 19                            | 19     | 0         | 0.95     |
      | 20                            | 20     | 1         | 0        |
      | 10, 20                        | 20     | 1         | 0        |
      | 4, 8, 12, 16, 20              | 20     | 1         | 0        |
      | 4, 8, 10, 10, 12, 16, 20, 20 | 20     | 1         | 0        |
      | 21                            | 21     | 1         | 0.05     |
      | 30                            | 30     | 1         | 0.5      |
      | 10, 20, 30                    | 30     | 1         | 0.5      |

  Scenario: Half a unit is retained until a whole unit has been produced
    Given the test mine is already completed and operational at minute 0
    When the simulation advances to minute 10
    Then at minute 10 the colony has exactly 0 whole units of iron available
    And the test mine retains production progress of 0.5 iron units
    When the simulation advances to minute 10
    Then the state is unchanged and the advance emits no events
    When the simulation advances to minute 20
    Then at minute 20 the colony has exactly 1 whole units of iron available
    And the test mine retains production progress of 0 iron units
    When the simulation advances to minute 20
    Then the state is unchanged and the advance emits no events

  Scenario Outline: A completed mine contributes only after its effective completion time
    Given the test mine is under construction with effective completion at minute 10
    And construction costs have already been paid
    And construction completes at minute 10 without further resource consumption
    When the simulation advances through minutes "<checkpoints>"
    Then at minute <target> the colony has exactly <available> whole units of iron available
    And the test mine retains production progress of <progress> iron units
    And the test mine is "<status>"

    Examples:
      | checkpoints             | target | available | progress | status             |
      | 9                       | 9      | 0         | 0        | under construction |
      | 10                      | 10     | 0         | 0        | operational        |
      | 10, 10                  | 10     | 0         | 0        | operational        |
      | 20                      | 20     | 0         | 0.5      | operational        |
      | 10, 20                  | 20     | 0         | 0.5      | operational        |
      | 29                      | 29     | 0         | 0.95     | operational        |
      | 30                      | 30     | 1         | 0        | operational        |
      | 10, 20, 30              | 30     | 1         | 0        | operational        |
      | 5, 15, 20, 25, 30       | 30     | 1         | 0        | operational        |
      | 9, 10, 10, 20, 30, 30  | 30     | 1         | 0        | operational        |
      | 31                      | 31     | 1         | 0.05     | operational        |
