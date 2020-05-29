Feature: Analysis Unit Changer

  Background: 
    Given I am on the activity page

  Scenario: Defaults to Fahrenheit
    Then The unit is Fahrenheit

  Scenario: Changing unit updates all temperature on the analysis display page
    Given there are results in analysis page
    And The unit is set to Fahrenheit
    When I toggle the unit changer
    And I wait
    Then The temperature unit on the analysis weather display is Celcius

  Scenario: Changing unit updates all temperature on the main display page 2
    Given there are results in analysis page
    Given The unit is set to Celcius
    When I toggle the unit changer
    And I wait
    Then The temperature unit on the analysis weather display is Fahrenheit

  Scenario: Selection persists to main page
    Given The unit is set to Celcius
    When I go to the activity page
    Then The unit is Celcius

  Scenario: Selection persists to vacation page
    Given The unit is set to Fahrenheit
    When I go to the activity page
    Then The unit is set to Fahrenheit

  Scenario: Selection persists to activity page
    Given The unit is set to Fahrenheit
    When I go to the activity page
    Then The unit is set to Fahrenheit
