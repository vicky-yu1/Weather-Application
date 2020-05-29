Feature: likes downvote feature works in vacation page
  
	Background: On home page
    Given I am logged in
    And I am on the vacation page
    And I set geolocation
  	And the vacation search inputs yield results
    And "Concord" has 1 likes

  Scenario: Clicking dislike updates value
  	When I dislike "Concord" button
    Then "Concord" has 0 like