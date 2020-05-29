Feature: Pagination works for Vacation Page

  Background: 
    Given I am logged in
    Given I am on the vacation page

  Scenario: displays at most 5 cities in the table display area
    Given the vacation search inputs yield results
    Then the table display area displays at most 5 results
    
  Scenario: the table display area displays 5 page numbers
  	Given the vacation search inputs yield results
    Then the table display area displays 5 page numbers
    
  Scenario: page numbers are clickable if > 5 results
  	Given the vacation search inputs yield more than 5 results
  	Then I can click on page number 2

  Scenario: clicking will show current page indicator
  	Given the vacation search inputs yield results
  	When I click on the "pagenum2"
    Then page "2" will be indicated as the current page
  
  Scenario: next button takes user to next 5 results
  	Given the vacation search inputs yield more than 5 results
  	When I click on the next button
    Then I will go to the next page

  Scenario: previous button takes user to previous 5 results
  	Given the vacation search inputs yield more than 5 results
  	When I click on the next button
  	And I click on the next button
  	And I click on the previous button
    Then I will go to the previous page
    
  Scenario: reverse ordering
  	Given the vacation search inputs yield results
    When I click on the "distanceHeading"
    Then the order will flip