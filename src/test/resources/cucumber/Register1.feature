Feature: login page register feature

  Background: Login page
    Given I am on the login page

  Scenario: Register prompt text disappears when the user starts typing
    When I start typing in "usernameReg" box
    Then in login input I get the prompt text ""
    
  Scenario: Password prompt text disappears when the user starts typing
    When I start typing in "passwordReg" box
    Then in password input I get the prompt text ""
    
  Scenario: Password reenter prompt text disappears when the user starts typing
    When I start typing in "passwordCheck" box
    Then in password input I get the prompt text ""