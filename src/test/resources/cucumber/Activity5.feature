Feature: Activity Unit Changer
  Background:
    Given I am on the activity page

  # Scenario 1
  Scenario: Defaults to Fahrenheit
  Then The unit is Fahrenheit

  # Scenario 2
  Scenario: Changing units updates all temperatures in the Table Display Area
    Given The unit is set to Fahrenheit
    When I toggle the unit changer
    And The activity search inputs yield results
    Then the temperature units in the table display area are Celcius

  # Scenario 3
  Scenario: Changing units updates all temperatures in the Table Display Area 2
    Given The unit is set to Celcius
    When I toggle the unit changer
    And The activity search inputs yield results
    Then the temperature units in the table display area are Fahrenheit

    # Scenario 4
 	Scenario: Selection of unit persists to Main page
      Given The unit is set to Celcius
      When I go to the main page
      Then The unit is Celcius

    # Scenario 5
	Scenario: Selection persists to vacation page
      Given The unit is set to Fahrenheit
      When I go to the vacation page
      Then The unit is Fahrenheit

    # Scenario 6
	Scenario: Selection persists to analysis page
      Given The unit is set to Celcius
      When I go to the analysis page
      Then The unit is Celcius