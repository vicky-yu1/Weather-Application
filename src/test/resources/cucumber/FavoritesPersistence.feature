Feature: favorite city data is persisted

  Background: On home page and city not yet in favorites
    Given I am logged in
    Given "Mountain View" not yet in favorite cities list

  Scenario: Data persists after user logs out
  	Given I am on the vacation page
  	And I set geolocation
  	And the vacation search inputs yield results
  	And I add "Mountain View" to favorites
    When I click on the "signoutButton"
    And I log back in
    And I go to the analysis page
    Then "Mountain View" is in favorite cities