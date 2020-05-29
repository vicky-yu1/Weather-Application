Feature: likes upvote feature works in activity page

  Background: On home page
    Given I am logged in
    And I am on the activity page
    And I set geolocation
  	And the activity search inputs yield results
    And "Concord" has 0 likes

  Scenario: Clicking like upvote updates value
  	When I like "Concord" button
    Then "Concord" has 1 like
    
  