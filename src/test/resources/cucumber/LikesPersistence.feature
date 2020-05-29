Feature: likes city data is persisted

  Background: On home page
    Given I am logged in

  Scenario: Data persists after user logs out
  	Given I am on the vacation page
  	And the vacation search inputs yield results
  	And I like "Concord"
    When I click on the "signoutButton"
    And I log back in
    And I go to the vacation page
    And I search in vacation
    Then "Concord" has one more likes