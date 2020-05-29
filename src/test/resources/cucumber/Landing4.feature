Feature: Main Weather display Area

  Background: Landing page
    Given I am on the landing page

  #4a, 4b, 4c,4d, 4e
  # Scenario 1
  Scenario: Weather Display Area Check
    When I type "Los Angeles" in the main text box
    And I click on the "SMTWButton"
    And I wait for the "weatherWidget" to load
    Then I get city name "Los Angeles"
    And I get date for today
    And I get weather graphic matching with decription
    And I get temperature
    And I get description
