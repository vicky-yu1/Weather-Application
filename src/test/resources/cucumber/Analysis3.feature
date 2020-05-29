Feature: Favorite Cities List area

  Background: 
    Given I am on the analysis page
    And there are results in analysis page

  #
  Scenario: 3.a alphabetically ordered
    Then cities are organized alphabetically

  Scenario: 3.b,c the “Remove from Favorites” button
    When I click on the "removeButton"
    Then popup box reads Are you sure you want to remove city name from favorites?
    And popup box Options are Yes and Cancel
    And clicking on yes removes city from the list
