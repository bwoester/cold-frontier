Feature: Delivery completion makes cargo available immediately
  Scope: Game behavior.
  Purpose: Specifies when successfully unloaded cargo becomes available.
  Rationale: The economy rules distinguish an ongoing unloading operation from
  the completed transfer of cargo into the destination stockpile.

  Scenario: Completed unloading credits only the cargo that fits
    Given a transport is due to finish unloading 30 units of ice at minute 36
    And the destination can accept the delivery
    And the destination has room for exactly 10 more units of ice
    When the simulation advances to minute 36
    Then exactly 10 additional units of ice are available in the destination stockpile
    And none of the delivered ice remains aboard the transport
    And the delivery completion event at minute 36 records 10 units accepted and 20 units lost
    When the simulation advances by one more tick without other changes to the ice stockpile
    Then the destination ice stockpile is unchanged

  Scenario: Ice delivered at the completion of a water unit can supply the next unit
    Given a water plant is due to finish its sixth unit of water at minute 36
    And the destination stockpile contains 5 units of water and no ice
    And starting the next unit requires 1 unit of ice and 2 units of energy
    And a transport is due to finish unloading 1 unit of ice into that stockpile at minute 36
    And the destination can accept the entire delivery
    And energy and water storage are sufficient
    And no player action changes those stocks at minute 36
    When the simulation advances to minute 36
    Then the stockpile contains 6 units of water and no ice
    And the seventh unit of water is in production
    And the advance emits no ice depletion event
