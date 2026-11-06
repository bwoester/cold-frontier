Feature: Water production with a limited ice stockpile
  Scope: Game behavior.
  Purpose: Specifies proposed behavior for the first resource simulation stage.
  Rationale: Stock changes and the effective time of ice depletion are gameplay
  outcomes that should hold across simulation engine implementations.

  Background:
    Given a planet is prepared with 6 units of ice and 0 units of water before production starts
    And its water plant produces 10 units of water per hour when fully supplied
    And each unit of water requires 1 unit of ice and 2 units of energy
    And energy and water storage are sufficient
    And no other processes change these stocks
    And the simulation is initialized at minute 0

  Scenario: Initialization starts the first unit at minute 0
    Then at minute 0 the pool contains 5 units of ice and no water
    And the water plant has taken 2 units of energy from the pool
    And the first unit of water is in production
    When the simulation advances to minute 0
    Then the state is unchanged and the advance emits no events
    When the simulation advances to minute 6
    Then the pool contains 1 unit of water

  Scenario Outline: Water production stops when the next unit cannot start for lack of ice
    When the simulation advances through minutes "<checkpoints>"
    Then at minute 60 the planet has exactly 6 units of water and no ice
    And the water plant has consumed exactly 12 units of energy
    And the water plant is no longer producing
    And exactly one ice depletion event records minute 36 as its effective game time

    Examples:
      | checkpoints |
      | 60          |
      | 30, 33, 36, 60  |

  Scenario: A unit in progress survives a checkpoint and depletion belongs to the advance that reaches it
    When the simulation advances to minute 30
    Then the pool contains 5 units of water and no ice
    And the advance emits no ice depletion event
    When the simulation advances to minute 33
    Then the pool contains 5 units of water and no ice
    And the sixth unit of water is halfway complete
    And the advance emits no ice depletion event
    When the simulation resumes from the minute 33 state and advances to minute 36
    Then the pool contains 6 units of water and no ice
    And the water plant cannot start another unit for lack of ice
    And the advance emits exactly one ice depletion event with effective game time minute 36
    When the simulation advances to minute 60
    Then the pool still contains 6 units of water and no ice
    And the advance emits no ice depletion event
