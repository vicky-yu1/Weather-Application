Feature: city table display area
  Background:
    Given I am on the vacation page
    And there are results in vacation page


    #3a
    Scenario Outline: proper table set up
      Then there will be a column "<column heading>" with the id "<column heading id>"
      Examples:
      | column heading | column heading id |
      | City     	   | cityHeading       |
      | Country   	   | countryHeading    |
      | Avg Min Temp   | avgMinHeading     |
      | Avg Max Temp   | avgMaxHeading     |
      | Distance       | distanceHeading   |

    #3b
    Scenario: n or less cities shown
      Then there will be 10 or less cities displayed

    #4c, 4ci
    Scenario: distance to current city
      Then results are ordered least first

    #4d
    Scenario: reverse ordering
      Then results are ordered least first
      When I click on the "distanceHeading"
      Then the order will flip

    #4e
    Scenario: favorites changer
   	  Given I wait for results in "resultsTable"
      Then I add city to favorites successfully
      

