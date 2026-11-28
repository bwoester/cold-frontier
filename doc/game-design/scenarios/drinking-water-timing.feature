@pending @issue-36
Feature: Completed water can satisfy drinking-water demand at the same game time
  Scope: Game behavior.
  Purpose: Specifies when a shortfall starts and ends.
  The quantities and times below are fixture values for current demand and production.
  They do not establish a periodic drinking-water charge or a population decline rate.

  Background:
    Given the colony has 0 units of water in stock before game minute 10
    And the residents need 1 unit of drinking water at game minute 10
    And the waterworks is the only source of water in the scenario

  Scenario: Water completed when demand is due prevents a shortage
    Given the waterworks will complete 1 unit of water at game minute 10
    When the simulation advances to game minute 10
    Then the residents receive 1 unit of drinking water at game minute 10
    And the colony has 0 units of water remaining in stock
    And no drinking-water shortage starts
    And no workers are reassigned due to a drinking-water shortage
    And no population decline due to a drinking-water shortage starts

  Scenario: Water completed later ends a shortage when it covers current demand
    Given the waterworks will complete 1 unit of water at game minute 11
    And the residents' current demand is still 1 unit of drinking water at game minute 11
    When the simulation advances to game minute 10
    Then 1 unit of drinking-water demand is unmet
    And the drinking-water shortage starts at game minute 10
    And population decline due to that shortage starts at game minute 10
    When the simulation advances to game minute 11
    Then the residents receive 1 unit of drinking water at game minute 11
    And the colony has 0 units of water remaining in stock
    And the drinking-water shortage ends at game minute 11
