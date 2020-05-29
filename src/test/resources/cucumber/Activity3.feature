Feature: activity page search results temperature range

  Background: 
    Given I am on the activity page

  Scenario Outline: check winter/outdoor/water sports
    Given I search for <sports type>
    Then activity page will give results within <temperature low> and <temperature high>

    Examples: 
      | temperature low | temperature high | sports type   |
      |           -1000 |               40 | winter sports |
      |              40 |               80 | outdoor games |
      |              80 |             1000 | water sports  |
