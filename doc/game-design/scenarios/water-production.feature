Feature: Water production with a limited ice stockpile
  Scope: Game behavior.
  Purpose: Specifies proposed behavior for the first resource simulation stage.
  Rationale: Stock changes and the effective time of ice depletion are gameplay
  outcomes that should hold across simulation engine implementations.

  Scenario Outline: Water production stops when the next unit cannot start for lack of ice
    Given a planet starts at minute 0 with 6 units of ice and 0 units of water
    And its water plant produces 10 units of water per hour when fully supplied
    And each unit of water requires 1 unit of ice and 2 units of energy
    And energy and water storage are sufficient
    And no other processes change these stocks
    When the simulation advances through minutes "<checkpoints>"
    Then at minute 60 the planet has exactly 6 units of water and no ice
    And the water plant has consumed exactly 12 units of energy
    And the water plant is no longer producing
    And exactly one ice depletion event records minute 36 as its effective game time

    Examples:
      | checkpoints |
      | 60          |
      | 30, 36, 60  |
