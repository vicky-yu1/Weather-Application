Feature: Sign out button works for every page

  Background: 
    Given I am logged in
    
  Scenario: Signing out from home page works
    When I click on the "signoutButton"
    Then Check Title is Login
    And I cannot access "home" page
    
  Scenario: Signing out from vacation page works
    When I click on the "signoutButton"
    Then Check Title is Login
    And I cannot access "vacation" page
    
  Scenario: Signing out from activity page works
    When I click on the "signoutButton"
    Then Check Title is Login
    And I cannot access "activity" page
    
  Scenario: Signing out from analysis page works
    When I click on the "signoutButton"
    Then Check Title is Login
    And I cannot access "analysis" page
