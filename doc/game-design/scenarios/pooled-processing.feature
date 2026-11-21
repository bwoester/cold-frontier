@pending @issue-17
Feature: Shared processing capacity for the same recipe
  Scope: Game behavior.
  Purpose: Specifies agreed common processing with explicit input, continuous-operation and construction cases.
  Rationale: Plants of the same recipe share capacity and fund one output unit together.
  See economy.md, section "Pooled Processing per Colony and Recipe".
  Values are test parameters.

  Background:
    Given a colony starts at minute 0 with 0 water and no processing in progress
    And the water recipe requires 1 ice and 2 energy per whole unit of water
    And water storage has capacity for 100 units
    And each plant maintains its stated operating capacity once completed
    And no processes other than the stated water production and construction change resource stocks or processing capacity

  Scenario Outline: Scarce ingredients and shared capacity - <coverage>
    Given the colony has <plants> water plants each capable of <rate> water units per hour
    And those plants are completed at minute 0
    And the pool contains 1 ice and 2 energy before processing starts
    When water processing starts at minute 0
    And the simulation advances through minutes "<checkpoints>"
    Then the pool contains 0 ice and 0 energy
    And exactly 1 ice and 2 energy have been taken for processing in total
    And exactly <water> whole water units are available
    And shared processing progress is <progress> water units
    And water processing is "<status>"

    Examples:
      | coverage | plants | rate | checkpoints | water | progress | status |
      | Two plants start with ingredients for only one output unit | 2 | 10 | 0 | 0 | 0 | running |
      | A single equivalent plant reaches half completion | 1 | 20 | 1.5 | 0 | 0.5 | running |
      | Two plants share progress without funding two separate units | 2 | 10 | 1.5 | 0 | 0.5 | running |
      | One equivalent plant completes the funded unit | 1 | 20 | 3 | 1 | 0 | waiting for ingredients |
      | Combined capacity completes the unit sooner than either plant alone | 2 | 10 | 3 | 1 | 0 | waiting for ingredients |
      | Intermediate updates preserve progress and do not debit twice | 2 | 10 | 0.75, 1.5, 2.25, 3 | 1 | 0 | waiting for ingredients |
      | Repeated completion and later times do not duplicate output | 2 | 10 | 1.5, 3, 3, 6 | 1 | 0 | waiting for ingredients |

  Scenario Outline: Incomplete recipes - <coverage>
    Given the colony has 2 water plants each capable of 10 water units per hour
    And those plants are completed at minute 0
    And the pool contains <ice> ice and <energy> energy before processing starts
    When water processing attempts to start at minute 0
    And the simulation advances to minute 6
    Then the pool still contains <ice> ice and <energy> energy
    And no ingredients have been taken for processing
    And exactly 0 whole water units are available
    And shared processing progress is 0 water units
    And water processing is "waiting for ingredients"

    Examples:
      | coverage | ice | energy |
      | Missing ice leaves energy untouched | 0 | 2 |
      | Insufficient energy leaves all ingredients untouched | 1 | 1 |

  Scenario: Consecutive units start without delay and withdraw each recipe exactly once
    Given the colony has 2 water plants completed at minute 0 each capable of 10 water units per hour
    And the pool contains 2 ice and 4 energy before processing starts
    When water processing starts at minute 0
    Then the pool contains 1 ice and 2 energy
    And exactly 0 whole water units are available
    And exactly 1 ice and 2 energy have been taken for processing in total
    And water processing is "running"
    When the simulation advances to minute 3
    Then exactly 1 whole water unit is available
    And the pool contains 0 ice and 0 energy
    And exactly 2 ice and 4 energy have been taken for processing in total
    And the second water unit has started with progress of 0 water units
    And water processing is "running"
    When the simulation advances to minute 4.5
    Then exactly 1 whole water unit is available
    And shared processing progress is 0.5 water units
    And exactly 2 ice and 4 energy have been taken for processing in total
    When the simulation advances to minute 6
    Then exactly 2 whole water units are available
    And shared processing progress is 0 water units
    And exactly 2 ice and 4 energy have been taken for processing in total
    And water processing is "waiting for ingredients"

  Scenario Outline: Construction accelerates only the remaining work - <coverage>
    Given water plant A is completed at minute 0 and capable of 10 water units per hour
    And water plant B is under construction and capable of 10 water units per hour once completed
    And plant B completes at minute 3 with its costs already paid and no further construction consumption
    And the pool contains 1 ice and 2 energy before processing starts
    When water processing starts at minute 0
    And the simulation advances through minutes "<checkpoints>"
    Then plant B is operational
    And exactly <water> whole water units are available
    And shared processing progress is <progress> water units
    And the pool contains 0 ice and 0 energy
    And exactly 1 ice and 2 energy have been taken for processing in total
    And water processing is "<status>"

    Examples:
      | coverage | checkpoints | water | progress | status |
      | Completion preserves the existing half-unit without retroactive production | 3 | 0 | 0.5 | running |
      | Remaining work finishes with the increased capacity without restarting | 3, 4.5 | 1 | 0 | waiting for ingredients |
      | Crossing construction completion uses its effective time without an explicit checkpoint | 4.5 | 1 | 0 | waiting for ingredients |
