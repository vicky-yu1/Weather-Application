Feature: login and jump to landing page

  Background: Login page
    Given I am on the login page
  
  Scenario: Registration successful
    Given I try to register successfully
    When I click on the "register"
    Then Check pageURL is "Home Page"
    
  Scenario: Registration unsuccessful - used username
    Given I try to register with used username
    Then "errorMsgReg" should appear
    
  Scenario: Registration unsuccessful - used username
    Given I try to register with invalid password check
    Then "errorMsgReg" should appear
    
  Scenario Outline: illegal values for input field
  When I enter "" in the "<input_field_id>"
  And I click on the "register"
  Then the login "<input_field_id>" should be red
  Examples: 
   | input_field_id |
   | usernameReg 	|
   | passwordReg 	|
   | passwordCheck  |