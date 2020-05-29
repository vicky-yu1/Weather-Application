Feature: main page text box

  Background: Landing page
    Given I am on the landing page

  #	2a
  # Scenario 1
  Scenario: Prompt text disappears when the user starts typing
    When I start typing
    Then I get the prompt text ""

  #    2b, 2c
  # Scenario 2
  Scenario: Default focus is on the main text box
    Then The focus is on the main text box
    And I get the prompt text "Enter location (city or zip)"
