Feature: Sign out button works for every page

  Background: 
    Given I am logged in
    
  Scenario: home page is highlighted in nav bar
    Then "homeButton" is indicated
    
  Scenario: activity page is highlighted in nav bar
  	Given I am on the activity page
    Then "activityButton" is indicated
    
  Scenario: vacation page is highlighted in nav bar
    Given I am on the vacation page
    Then "vacationButton" is indicated
    
  Scenario: analysis page is highlighted in nav bar
    Given I am on the analysis page
    Then "analysisButton" is indicated
