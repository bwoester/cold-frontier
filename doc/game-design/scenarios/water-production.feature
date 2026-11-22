Feature: Water production with a limited ice stockpile
  Scope: Game behavior.
  Purpose: Specifies agreed behavior for the first resource simulation stage.
  Rationale: Stock, production progress, and the effective time of depletion
  should agree across simulation engine implementations.

  Background:
    Given a planet is prepared with 6 units of ice and 0 units of water before production starts
    And its water plant produces 10 units of water per hour when fully supplied
    And each unit of water requires 1 unit of ice and 2 units of energy
    And the planet has 12 units of energy before production starts
    And water storage has capacity for 100 units
    And no other processes change these stocks
    And the simulation is initialized at minute 0

  Scenario: Initialization starts the first unit at minute 0
    Then at minute 0 the pool contains 5 units of ice and no water
    And the water plant has taken 2 units of energy from the pool
    And the first unit of water is in production
    When water production advances to minute 6
    Then the pool contains 1 unit of water

  Scenario: Repeating minute 0 preserves initialized water production
    When water production advances to minute 0
    Then at minute 0 the pool contains 5 units of ice and no water
    And the water plant has taken 2 units of energy from the pool
    And a unit of water is in production with 0 progress

  Scenario Outline: Water production stops after using the available ice - checkpoints <checkpoints>
    When water production advances through minutes "<checkpoints>"
    Then the pool contains 6 units of water and no ice
    And the water plant has taken 12 units of energy from the pool
    And the water plant is no longer producing

    Examples:
      | checkpoints    |
      | 60             |
      | 30, 33, 36, 60 |

  Scenario: The last water unit retains progress across checkpoints and finishes without more ice
    When water production advances to minute 30
    Then the pool contains 5 units of water and no ice
    And a unit of water is in production with 0 progress
    When water production advances to minute 33
    Then the pool contains 5 units of water and no ice
    And a unit of water is in production with 0.5 progress
    When water production advances to minute 36
    Then the pool contains 6 units of water and no ice
    And the water plant is no longer producing
    When water production advances to minute 60
    Then the pool contains 6 units of water and no ice
    And the water plant is no longer producing

  @pending @issue-18
  Scenario: Repeating minute 0 emits no events
    When water production advances to minute 0
    Then the advance emits no events

  @pending @issue-18
  Scenario Outline: The ice depletion event has the same effective time across checkpoints <checkpoints>
    When water production advances through minutes "<checkpoints>"
    Then exactly one ice depletion event records minute 36 as its effective game time

    Examples:
      | checkpoints    |
      | 60             |
      | 30, 33, 36, 60 |

  @pending @issue-18
  Scenario: The depletion event belongs to the advance that reaches it
    When water production advances to minute 30
    Then the advance emits no ice depletion event
    When water production advances to minute 33
    Then the advance emits no ice depletion event
    When water production advances to minute 36
    Then the advance emits exactly one ice depletion event with effective game time minute 36
    When water production advances to minute 60
    Then the advance emits no ice depletion event
