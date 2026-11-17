Feature: Pooled extraction within a colony
  Scope: Game behavior.
  Purpose: Specifies agreed pooling of fractional extraction across mines.
  Rationale: Whole resource availability follows the colony's combined extraction,
  not completion of a whole unit by each mine. See economy.md, section
  "Pooled Extraction per Colony and Resource".
  All quantities and times are test parameters. Step bindings are not yet provided.

  Background:
    Given a colony starts at minute 0 with 0 available iron and 0 shared iron extraction progress
    And iron storage has capacity for 100 units
    And the test mines require no input resources
    And all mines operate at their stated constant rates once completed
    And no other processes consume, transfer or produce iron

  Scenario Outline: Equivalent total extraction - <coverage>
    Given the colony has <mines> completed mines at minute 0
    And each mine extracts <rate> iron units per hour
    When the simulation advances through minutes "<checkpoints>"
    Then the colony has exactly <available> whole units of iron available
    And the colony retains shared iron extraction progress of <remainder> iron units

    Examples:
      | coverage | mines | rate | checkpoints | available | remainder |
      | One faster mine retains a fraction before the first whole unit | 1 | 6 | 5 | 0 | 0.5 |
      | Two slower mines retain the same combined fraction | 2 | 3 | 5 | 0 | 0.5 |
      | One faster mine completes the first whole unit | 1 | 6 | 10 | 1 | 0 |
      | Two half-unit mine contributions make one available unit | 2 | 3 | 10 | 1 | 0 |
      | One faster mine retains excess beyond a whole unit | 1 | 6 | 15 | 1 | 0.5 |
      | Two slower mines retain the same excess | 2 | 3 | 15 | 1 | 0.5 |
      | Intermediate updates preserve pooled fractions | 2 | 3 | 5, 10, 15 | 1 | 0.5 |

  Scenario: A newly completed mine contributes without resetting pooled progress
    Given mine A is completed at minute 0 and extracts 3 iron units per hour
    And mine B is under construction and will extract 3 iron units per hour
    And mine B completes at minute 10 with its costs already paid and no further construction consumption
    When the simulation advances to minute 10
    Then mine B is operational
    And the colony has exactly 0 whole units of iron available
    And the colony retains shared iron extraction progress of 0.5 iron units
    When the simulation advances to minute 15
    Then the colony has exactly 1 whole unit of iron available
    And the colony retains shared iron extraction progress of 0 iron units
