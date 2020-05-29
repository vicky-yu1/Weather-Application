Feature: Vacation Unit Changer

  Background: 
    Given I am on the vacation page

  # Scenario 1
  Scenario: Defaults to Fahrenheit
    Then The unit is Fahrenheit

  # Scenario 2
  Scenario: Changing unit updates all temperatures in the Table Display Area
    Given The unit is set to Fahrenheit
    And the vacation search inputs yield results
    When I toggle the unit changer
    Then the temperature units in the table display area are Fahrenheit

  # Scenario 3
  Scenario: Changing unit updates all temperatures in the Table Display Area 2
    Given The unit is set to Celcius
    And the vacation search inputs yield results
    When I toggle the unit changer
    Then the temperature units in the table display area are Celcius

  # Scenario 4
  Scenario: Selection persists to main page
    Given The unit is set to Fahrenheit
    When I go to the main page
    Then The unit is Fahrenheit

  # Scenario 5
  Scenario: Selection persists to activity page
    Given The unit is set to Celcius
    When I go to the activity page
    Then The unit is set to Celcius

  # Scenario 6
  Scenario: Selection persists to analysis page
    Given The unit is set to Celcius
    When I go to the analysis page
    Then The unit is set to Celcius
