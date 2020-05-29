Feature: Main Unit Changer

	Background: Landing page
		Given I am on the landing page

  
  #5
	# Scenario 1
	Scenario: Defaults to Fahrenheit
		Then The unit is Fahrenheit

	# Scenario 2
	Scenario: Changing unit updates all temperature on the main display page
		When I toggle the unit changer
		And I type "Los Angeles" in the main text box
		And I click on the "SMTWButton"
		And I wait for the "weatherWidget" to load
    	Then The temperature unit on the main weather display is Celcius

	# Scenario 3
  	Scenario: Changing unit updates all temperature on the main display page 2
		Given The unit is set to Celcius
		When I toggle the unit changer
		And I type "Los Angeles" in the main text box
		And I click on the "SMTWButton"
		And I wait for the "weatherWidget" to load
    	Then The temperature unit on the main weather display is Fahrenheit

	# Scenario 4
 	Scenario: Selection of unit persists to activity page
  		Given The unit is set to Celcius
		When I go to the analysis page
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