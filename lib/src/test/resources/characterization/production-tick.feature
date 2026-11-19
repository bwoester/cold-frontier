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

  Scenario Outline: Production and consumption accumulate over game time
    Given a prototype planet has 5 energy and one iron mine at a tick duration of <tick duration> ms
    When the ticker advances to game time 1000 ms
    And the planet has 1 iron and 4 energy
    When the ticker advances to game time 2000 ms
    And the ticker advances to game time 2000 ms
    Then the planet has 2 iron and 3 energy

    Examples:
      | tick duration |
      | 1000          |
      | 250           |
      | 100           |

  Scenario Outline: A queued mine completes on the first tick at or after its construction duration
    Given a prototype planet has 5 energy and a queued iron mine at a tick duration of <tick duration> ms
    When the ticker advances to game time <before completion> ms
    Then the planet has 0 completed iron mines
    When the ticker advances to game time <completion> ms
    Then the planet has 1 completed iron mines
    And the planet has 0 iron and 5 energy
    When the ticker advances to game time <after production> ms
    Then the planet has 1 iron and 4 energy

    Examples:
      | tick duration | before completion | completion | after production |
      | 1000          | 2000              | 3000       | 4000             |
      | 250           | 2000              | 2250       | 3250             |
      | 750           | 2250              | 3000       | 4500             |

  Scenario: An off-grid target is rejected before processing ticks
    Given a prototype planet has 5 energy and one iron mine at a tick duration of 250 ms
    When the ticker advances to game time 1250 ms
    Then advancing to game time 2125 ms is rejected
    And the planet has 1 iron and 4 energy
