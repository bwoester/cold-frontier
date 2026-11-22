Feature: Water production restarts when all inputs are available
  Scope: Game behavior.
  Purpose: Specifies input withdrawal and automatic restart after an energy shortage.
  Rationale: A plant starts a unit only when it can take all required inputs together.

  Scenario: The water plant leaves ice in the pool until enough energy is available
    Given a planet is prepared with 2 units of ice and 0 units of water before production starts
    And its water plant produces 10 units of water per hour when fully supplied
    And each unit of water requires 1 unit of ice and 2 units of energy
    And the planet has 3 units of energy before production starts
    And water storage has capacity for 100 units
    And the simulation is initialized at minute 0
    And a supply of 1 unit of energy becomes available in the pool at minute 9
    And no other processes change these stocks
    When water production advances to minute 6
    Then the pool contains 1 unit of water, 1 unit of ice and 1 unit of energy
    And the water plant is no longer producing
    When water production advances to minute 8 and second 59
    Then the pool contains 1 unit of water, 1 unit of ice and 1 unit of energy
    And the water plant is no longer producing
    When water production advances to minute 9
    Then the pool contains 1 unit of water, 0 units of ice and 0 units of energy
    And a unit of water is in production with 0 progress
    When water production advances to minute 14 and second 59
    Then the pool contains 1 unit of water
    And a unit of water is in production
    When water production advances to minute 15
    Then the pool contains 2 units of water, 0 units of ice and 0 units of energy
    And the water plant is no longer producing
