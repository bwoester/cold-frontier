Feature: Water production restarts when all inputs are available
  Scope: Game behavior.
  Purpose: Specifies input withdrawal and automatic restart after an energy shortage.
  Rationale: A plant starts a unit only when it can take all required inputs together.

  Scenario: The water plant leaves ice in the pool until enough energy is available
    Given a planet is prepared with 2 units of ice, 3 units of energy and no water
    And its water plant takes 6 minutes to produce a unit of water
    And each unit requires 1 unit of ice and 2 units of energy
    And water storage is sufficient
    And the simulation is initialized at minute 0
    And a supply of 1 unit of energy becomes available in the pool at minute 9 before resource updates
    And no other process changes these stocks
    When the simulation advances to minute 6
    Then the pool contains 1 unit of water, 1 unit of ice and 1 unit of energy
    And the water plant cannot start the next unit for lack of energy
    When the simulation advances to minute 9
    Then the pool contains 1 unit of water, no ice and no energy
    And the water plant has automatically started the second unit
    When the simulation advances to minute 15
    Then the pool contains 2 units of water
