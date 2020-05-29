# TODO searching for the navigation bar
Feature: activity page navigation bar
  Background:
    Given I am on the activity page
  Scenario Outline: click an icon in the Navigation Bar
    When I click on the <Icon> on the Nav Bar
    Then The we will be on page <Page>
    Examples:
      | Icon | Page |
      | Main Icon | Home Page |
      | Activity Icon | Activity Page |
      | Vacation Icon | Vacation Page |
      | Analysis Icon | Analysis Page |