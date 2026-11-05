Feature: Iron production in the current ticker
  Scope: Implementation detail (lib, current ticker).
  Purpose: Verifies that the Cucumber integration drives production and accounting.
  Rationale: The scenario uses the prototype's tick counter and configured mine rates.
  These fixture values are specific to the current implementation.

  Scenario Outline: A supplied mine produces iron on each tick
    Given a prototype planet has 5 energy and one iron mine
    When the ticker advances through ticks "<checkpoints>"
    Then the planet has 3 iron and 2 energy

    Examples:
      | checkpoints |
      | 3           |
      | 1, 3        |
