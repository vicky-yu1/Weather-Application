Feature: Main page navigation bar

  Background: 
    Given I am on the landing page

  #6
  # Scenario 1
  Scenario Outline: Click on the icon on the Navigation Bar
    When I click on the <Nav Bar button>
    Then Check Title <Title> of the page

    Examples: 
      | Nav Bar button   | Title           |
      | "homeButton"     | "Home Page"     |
      | "activityButton" | "Activity Page" |
      | "vacationButton" | "Vacation Page" |
      | "analysisButton" | "Analysis Page" |
