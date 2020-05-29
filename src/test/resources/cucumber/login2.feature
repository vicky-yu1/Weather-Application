Feature: login and jump to landing page

  Background: Login page
    Given I am on the login page
  
  Scenario: Login successful
    Given I try to log in successfully
    When I click on the "login"
    Then Check pageURL is "Home Page"
    
  Scenario: Login unsuccessful
    Given I try to log in unsuccessfully
    Then "errorMsg" should appear
    
  Scenario Outline: illegal values for input field
    When I enter "" in the "<input_field_id>"
    And I click on the "login"
    Then the login "<input_field_id>" should be red
    Examples: 
      | input_field_id  |
      | username 	    |
      | password 	    |