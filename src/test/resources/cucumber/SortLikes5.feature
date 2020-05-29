Feature: likes in order
  
	Background: On home page
    Given I am logged in
    And I set geolocation

  Scenario: results are ordered most like first in activity page
  	Given I am on the activity page
  	And the activity search inputs yield results
  	When I click on the "likesHeading"
  	Then results are ordered most like first
  	
  Scenario: results are ordered most like first in vacation page
  	Given I am on the vacation page
  	And the vacation search inputs yield results
  	When I click on the "likesHeading"
  	Then results are ordered most like first
  	