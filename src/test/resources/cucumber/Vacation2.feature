Feature: "Find My Vacation Spot" Button

  Background: 
    Given I am on the vacation page

  # 2a, 2b
  # Scenario 1
  Scenario: displays information in the table display area
    Given the vacation search inputs yield results
    Then the table display area is not empty

  # Scenario 2
  Scenario: displays "No locations found." in the table display area
    Given the vacation search inputs yield no results
    Then the table display area displays "No locations found."
