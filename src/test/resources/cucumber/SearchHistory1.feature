Feature: search result saved in

  Background: User is logged in
    Given I am logged in
    
  Scenario: Search result with location, image, and temperature shows up in search history
    When I search "Oakland"
    Then "Oakland" shows up in search history
    
  Scenario: Maximum 4 results in search history
  	When I search 5 times
    Then there is less than 4 results in search history
    
  Scenario: Recent results search history
  	When I search 5 times
    Then the 4 results in search history is most recent, with most recent being "Long Beach"

	Scenario: Clicking on search result in search history takes user back to search in weather display area
  	When I search "Los Angeles"
    And I click on "Los Angeles" search result
    Then weather display shows "Los Angeles"
	  