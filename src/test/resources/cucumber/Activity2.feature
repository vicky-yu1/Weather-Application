Feature: “Find My Activity Spot” Button

  Background: 
  	Given I am logged in
    And I am on the activity page

  Scenario: displays information in the table display area
    Given The activity search inputs yield results
    Then the table display area is not empty

  Scenario: displays "No locations found." in the table display area
    Given the activity search inputs yield no results
    Then the table display area displays "No locations found."
