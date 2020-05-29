Feature: login page sign in feature

  Background: Login page
    Given I am on the login page

  Scenario: Login prompt text disappears when the user starts typing
    When I start typing in "username" box
    Then in login input I get the prompt text ""
    
  Scenario: Password prompt text disappears when the user starts typing
    When I start typing in "password" box
    Then in password input I get the prompt text ""