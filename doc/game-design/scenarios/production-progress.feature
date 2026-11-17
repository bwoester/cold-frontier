Feature: Production progress across updates and building completion
  Scope: Game behavior.
  Purpose: Specifies agreed production behavior using explicit examples.
  Rationale: Available resources and retained production progress must not depend
  on the engine or the frequency of updates. See economy.md, section
  "Bauabschluss und Produktionsfortschritt".
  Rates and times below are test parameters, not general building balance values.
  These scenarios are specifications; engine step bindings are not provided yet.
  Each coverage label names the behavior checked by that row and is included in
  the expanded scenario name. The sequential scenario also checks emitted events;
  the outline rows assert only the explicitly listed quantities and building status.

  Background:
    Given a colony has 0 units of iron and no other producing buildings
    And a test mine produces iron at a constant rate of 3 units per hour while operational
    And the test mine requires no input resources for production
    And iron storage has capacity for 100 units
    And no actions consume or transfer iron during the scenario
    And all operating conditions remain satisfied throughout the scenario
    And the simulation starts at minute 0
    And production progress starts at 0 units

  Scenario Outline: Production progress - <coverage>
    Given the test mine is already completed and operational at minute 0
    When the simulation advances through minutes "<checkpoints>"
    Then at minute <target> the colony has exactly <available> whole units of iron available
    And the colony retains shared iron extraction progress of <progress> iron units

    Examples:
      | coverage | checkpoints                   | target | available | progress |
      | No elapsed time produces nothing | 0                             | 0      | 0         | 0        |
      | Half a unit remains unavailable but is retained | 10                            | 10     | 0         | 0.5      |
      | Splitting a half-unit interval preserves partial progress | 5, 10                         | 10     | 0         | 0.5      |
      | An incomplete first unit is not made available early | 19                            | 19     | 0         | 0.95     |
      | The first whole unit is available at its completion time | 20                            | 20     | 1         | 0        |
      | Two half-unit intervals yield one available unit | 10, 20                        | 20     | 1         | 0        |
      | Many sub-unit intervals do not lose production through rounding | 4, 8, 12, 16, 20              | 20     | 1         | 0        |
      | Repeated intermediate and final times do not duplicate production | 4, 8, 10, 10, 12, 16, 20, 20 | 20     | 1         | 0        |
      | Progress beyond a whole unit is retained | 21                            | 21     | 1         | 0.05     |
      | Direct advancement retains a remainder after a whole unit | 30                            | 30     | 1         | 0.5      |
      | Partitioned advancement retains the same whole unit and remainder | 10, 20, 30                    | 30     | 1         | 0.5      |

  Scenario: Repeated times preserve partial and whole production without emitting new events
    Given the test mine is already completed and operational at minute 0
    When the simulation advances to minute 10
    Then at minute 10 the colony has exactly 0 whole units of iron available
    And the colony retains shared iron extraction progress of 0.5 iron units
    When the simulation advances to minute 10
    Then the state is unchanged and the advance emits no events
    When the simulation advances to minute 20
    Then at minute 20 the colony has exactly 1 whole units of iron available
    And the colony retains shared iron extraction progress of 0 iron units
    When the simulation advances to minute 20
    Then the state is unchanged and the advance emits no events

  Scenario Outline: Production after construction - <coverage>
    Given the test mine is under construction with effective completion at minute 10
    And construction costs have already been paid
    And construction completes at minute 10 without further resource consumption
    When the simulation advances through minutes "<checkpoints>"
    Then at minute <target> the colony has exactly <available> whole units of iron available
    And the colony retains shared iron extraction progress of <progress> iron units
    And the test mine is "<status>"

    Examples:
      | coverage | checkpoints             | target | available | progress | status             |
      | A construction site produces nothing | 9                       | 9      | 0         | 0        | under construction |
      | Building completion itself produces no resources | 10                      | 10     | 0         | 0        | operational        |
      | Repeating the completion time adds no production | 10, 10                  | 10     | 0         | 0        | operational        |
      | Crossing construction completion credits only subsequent operating time | 20                      | 20     | 0         | 0.5      | operational        |
      | An explicit checkpoint at completion does not delay production | 10, 20                  | 20     | 0         | 0.5      | operational        |
      | The first unit after construction is not available early | 29                      | 29     | 0         | 0.95     | operational        |
      | The first unit after construction is available without extra delay | 30                      | 30     | 1         | 0        | operational        |
      | Splitting at completion and halfway preserves the first unit | 10, 20, 30              | 30     | 1         | 0        | operational        |
      | Crossing completion between checkpoints preserves its effective time | 5, 15, 20, 25, 30       | 30     | 1         | 0        | operational        |
      | Repeated completion and production checkpoints do not duplicate output | 9, 10, 10, 20, 30, 30  | 30     | 1         | 0        | operational        |
      | Remainder beyond the first unit after construction is retained | 31                      | 31     | 1         | 0.05     | operational        |
