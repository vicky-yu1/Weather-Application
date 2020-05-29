Feature: Analysis Unit Changer

	Background:
		Given I am on the activity page
  
  Scenario: Defaults to Fahrenheit
    Then The unit is Fahrenheit
    
	Scenario: Changing unit updates all temperature on the main display page
		Given the unit is Fahrenheit
		When I toggle the unit changer
    Then The temperature unit on the main weather display is Celcius
    
  Scenario: Changing unit updates all temperature on the main display page 2
		Given the unit is Celcius
		When I toggle the unit changer
    Then The temperature unit on the main weather display is Fahrenheit
    
 	Scenario: Selection persists to main page
		Given The temperature unit on the main weather display is Fahrenheit
		When I go to the activity page
    Then The temperature unit on the activity page is Fahrenheit
    
	Scenario: Selection persists to vacation page
		Given The temperature unit on the main weather display is Fahrenheit
		When I go to the activity page
    Then The temperature unit on the vacation page is Fahrenheit
    
	Scenario: Selection persists to activity page
		Given The temperature unit on the main weather display is Fahrenheit
		When I go to the activity page
		Then The temperature unit on the analysis page is Fahrenheit
	
