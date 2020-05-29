Feature: Analysis page navigation bar	
	Background:
		Given I am on the analysis page


  Scenario Outline: Click on the icon on the Navigation Bar
  	When I click on the <Icon> on the Nav Bar
  	Then Check <Title> of the page
  	Examples: 
	  	|Icon						|Title				|
			|Main Icon			|Home Page		|
			|Activity Icon  |Activity Page|
			|Vacaction Icon |Vaction Page |
			|Analysis Icon  |Analysis Page|

		
