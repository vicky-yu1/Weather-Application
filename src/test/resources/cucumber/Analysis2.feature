Feature: Weather analysis area

  Background: 
    Given I am on the analysis page
    And there are results in analysis page
    When I wait

  Scenario: Shows the current weather in the form of the “weather display area” 2.b
    Then there is "cityName"
    And there is "date"
    And there is "weatherIcon"
    And there is "weatherDescription"
    And there is "tempValue"

  Scenario: Shows Five Day Forecasts 2.b
    Then there is date in each forecast
    And there is icon in each forecast
    And there is high temp in each forecast
    And there is low temp in each forecast
