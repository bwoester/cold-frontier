Feature: Production batches in the current ticker
  This scenario characterizes the prototype for the Cucumber integration trial.
  It records observed behavior and does not define a game rule.
  In this fixture, the mine consumes 2 energy to produce 1 iron per tick.
  The house contributes 1 population per tick without consuming energy.

  Rule: The prototype rejects the whole batch when energy runs out

    Scenario: The first tick consumes the available energy
      Given a prototype planet has 2 energy and two buildings with the test production rates
      When the ticker advances through ticks "1"
      Then the planet has 1 iron, 0 energy and 1 population

    Scenario Outline: Later ticks also reject the independent population output
      Given a prototype planet has 2 energy and two buildings with the test production rates
      When the ticker advances through ticks "<checkpoints>"
      Then the planet has 1 iron, 0 energy and 1 population

      Examples:
        | checkpoints |
        | 3           |
        | 1, 3        |
