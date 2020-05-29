Feature: city table display area

  Background: 
    Given I am on the vacation page
    And there are results in vacation page

  #    3a
  # Scenario 1
  Scenario Outline: proper table set up
    Then there will be a column "<column heading>" with the id "<column heading id>"

    Examples: 
      | column heading | column heading id |
      | City           | cityHeading       |
      | Country        | countryHeading    |
      | Avg Min Temp   | avgMinHeading     |
      | Avg Max Temp   | avgMaxHeading     |
      | Distance       | distanceHeading   |

  #    3b
  # Scenario 2
  Scenario: n or less cities shown
    Then there will be 10 or less cities displayed

  #    4c, 4ci
  # Scenario 3
  Scenario: distance to current city
    Then results are ordered least first

  #    4d
  # Scenario 4
  Scenario: reverse ordering
    Then results are ordered least first
    When I click on the "distanceHeading"
    Then the order will flip

  #    4e
  # Scenario 5
  Scenario: favorites changer
    Given I wait for results in "resultsTable"
    Then I add city to favorites successfully
