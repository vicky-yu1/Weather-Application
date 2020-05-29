Feature: search history data is persisted

  Background: On home page
    Given I am logged in

  Scenario: Data persists after user logs out
    When I search "Los Angeles"
    And I search "San Jose"
    And I search "Cupertino"
    And I search "Emeryville"
    And I click on the "signoutButton"
    And I log back in
    Then search history persists