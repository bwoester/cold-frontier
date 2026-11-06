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

  Scenario: A player action uses delivered ice before the water plant can use it
    Given a water plant is due to finish its sixth unit of water at minute 36
    And the destination stockpile contains 5 units of water and no ice
    And starting the next unit requires 1 unit of ice and 2 units of energy
    And a transport is due to finish unloading 1 unit of ice into that stockpile at minute 36
    And the destination can accept the entire delivery
    And energy and water storage are sufficient
    And a player action costing 1 unit of ice is due at minute 36
    And the player action meets all other requirements and does not affect the water plant
    When the simulation advances to minute 36
    Then the player action succeeds and consumes the delivered ice
    And the stockpile contains 6 units of water and no ice
    And the water plant cannot start the seventh unit for lack of ice
    And the advance emits exactly one ice depletion event with effective game time minute 36

  Scenario: Cancelling unloading before completion leaves the cargo aboard
    Given a fleet is unloading 30 units of ice with completion due at minute 36
    And the destination has no ice and can accept the entire delivery
    And no other process changes the fleet cargo or destination ice stockpile
    When the player cancels unloading at minute 35
    And the simulation advances to minute 36
    Then all 30 units of ice remain aboard the fleet
    And the destination ice stockpile remains empty
    And no successful delivery completion event is emitted
