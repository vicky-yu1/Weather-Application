Feature: all radius input boxes work

  Background: user is logged in
    Given I am logged in

  Scenario: radius input search works in activity planning page
    Given I am on the activity page
    And I search for "Jogging" in "input_activity" "Activity" page
    And I search for "10" in "input_radius" "Activity" page
    When I click on the "find_result"
    Then the max radius is less than 10
    
  Scenario: illegal values for input field
  	Given I am on the activity page
    When I enter "" in the "input_radius"
    And I click on the "find_result"
    Then the login "input_radius" should be red
    
  Scenario: radius input search works in vacation planning page
    Given I am on the vacation page
    And I search for "0" in "input_range_lower" "Vacation" page
    And I search for "100" in "input_range_upper" "Vacation" page
    And I search for "10" in "input_radius" "Vacation" page
    When I click on the "find_result"
    Then the max radius is less than 10
    
  Scenario: illegal values for input field
  	Given I am on the vacation page
    When I enter "" in the "input_radius"
    And I click on the "find_result"
    Then the login "input_radius" should be red