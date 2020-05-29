Feature: vacation page navigation bar

  Background: 
    Given I am on the vacation page

  # Scenario 1
  Scenario Outline: click an icon in the Navigation Bar
    When I click on the <Nav Bar button>
    Then Check Title <Title> of the page

    Examples: 
      | Nav Bar button   | Title           |
      | "homeButton"     | "Home Page"     |
      | "activityButton" | "Activity Page" |
      | "vacationButton" | "Vacation Page" |
      | "analysisButton" | "Analysis Page" |
