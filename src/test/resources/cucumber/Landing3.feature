Feature: Main "Show me the weather" button

  #all passes
  Background: Landing page
    Given I am on the landing page

  # #3a
  # Scenario 1
  Scenario: Clicking on this button with correct city name
    When I type "Los Angeles" in the main text box
    And I click on the "SMTWButton"
    Then I get weather information in the weather display

  #3a
  # Scenario 2
  Scenario: Clicking on this button with correct zip code
    When I type "90007" in the main text box
    And I click on the "SMTWButton"
    Then I get weather information in the weather display

  #3b
  # Scenario 3
  Scenario: Clicking on this button with no information
    When I type "" in the main text box
    And I click on the "SMTWButton"
    Then I get nothing in the weather display

  #3b
  # Scenario 4
  Scenario: Clicking on this button with incorrect information
    When I type "whatever" in the main text box
    And I click on the "SMTWButton"
    Then I get “No weather data found.” in the weather display
