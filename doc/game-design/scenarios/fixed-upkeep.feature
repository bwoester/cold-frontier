@pending @issue-53
Feature: Fixed upkeep is paid in whole units before operating
  Scope: Game behavior.
  Purpose: Specifies payments, shortages, and recovery for upkeep-dependent operation.
  Rationale: Buildings prepay only the whole units due and accrue no upkeep during shutdown.

  Rule: Each resource is paid when its next coverage period starts

    Scenario: Different rates renew independently and simultaneous demands are paid together
      Given a building is due to complete at game minute 0
      And it needs 2 units of fuel and 1 unit of energy per game hour while operational
      And the colony has 3 units of fuel and 1 unit of energy before completion
      And no other process changes those stocks through game minute 60
      When the simulation advances to game minute 0
      Then the building is operational
      And the colony has 2 units of fuel and 0 units of energy
      When the simulation advances to game minute 30
      Then the building is operational
      And the colony has 1 unit of fuel and 0 units of energy
      When the simulation advances to game minute 59
      Then the building is still operational
      And the colony has 1 unit of fuel and 0 units of energy
      When the simulation advances to game minute 60
      Then the building is undersupplied
      And the colony has 1 unit of fuel and 0 units of energy
      And the upkeep audit at game minute 60 records 1 fuel and 1 energy due and no payment

    Scenario: A newly completed building cannot partly pay its initial upkeep
      Given a building is due to complete at game minute 0
      And it needs 2 units of fuel and 1 unit of energy per game hour while operational
      And the colony has 1 unit of fuel and 0 units of energy before completion
      And no other process changes those stocks at game minute 0
      When the simulation advances to game minute 0
      Then the building has completed and is undersupplied
      And the colony has 1 unit of fuel and 0 units of energy
      And the upkeep audit at game minute 0 records 1 fuel and 1 energy due and no payment

  Rule: Shutdown stops upkeep and does not create arrears

    Scenario: Research resumes from retained progress with upkeep paid only for new operation
      Given a research facility completes at game minute 0
      And it needs 1 unit of energy per game hour while operational
      And it contributes 10 research points per hour when supplied
      And the player has no other research capacity
      And the player's active topic requires 50 research points and has 0 progress at game minute 0
      And the colony has 1 unit of energy before the facility completes
      And no other process changes that stock except a resupply becoming eligible for upkeep at game minute 180
      And the resupply adds 2 units of energy
      When the simulation advances to game minute 60
      Then the facility is undersupplied
      And the colony has 0 units of energy
      And the player's research capacity is 0 research points per hour
      And the active topic has accumulated 10 research points
      When the simulation advances to game minute 179
      Then the facility is still undersupplied
      And the active topic has accumulated 10 research points
      And the upkeep audit records no further payments or shortfalls for the facility after game minute 60
      When the simulation advances to game minute 180
      Then the facility is operational
      And the colony has 1 unit of energy
      And the upkeep audit at game minute 180 records 1 energy paid for the facility
      And the player's research capacity is 10 research points per hour
      And the active topic still has 10 research points
      When the simulation advances to game minute 210
      Then the active topic has accumulated 15 research points

    Scenario: An orbital shipyard stops accruing orbital upkeep when it grounds
      Given a shipyard is operational in orbit and its paid coverage expires at game minute 60
      And its next payment of 1 unit of fuel is due at game minute 60
      And its controlled descent would finish before game minute 120
      And the colony has 0 units of fuel through game minute 60
      And its active ship order has 40 percent progress at game minute 60 before the shortage takes effect
      And the colony receives 2 units of fuel eligible for upkeep at game minute 120
      And no player orders an ascent and no other process changes the fuel stock through game minute 180
      When the simulation advances to game minute 60
      Then the upkeep audit records a shortage effective at game minute 60 and the start of a controlled descent
      And the ship order is paused at 40 percent progress
      When the simulation advances to game minute 180
      Then the shipyard is not operational in orbit
      And the ship order is paused at 40 percent progress
      And the colony has 2 units of fuel
      And the upkeep audit records no further orbital upkeep payments or shortfalls after game minute 60
