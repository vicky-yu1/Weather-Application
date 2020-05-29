Feature: vacation page input fields

  Background: vacation page
    Given I am on the vacation page

  #1ai, 1aii, 1aiii
  # Scenario 1
  Scenario Outline: illegal values for input field
    When I enter "" in the "<input_field_id>"
    And I click on the "FMVSButton"
    And I wait for the "errorMessage" to load
    Then the "<input_field_id>" should be red
    And an error message will be displayed for "<input_field>" in the table display area

    Examples: 
      | input_field       | input_field_id    |
      | lower range       | input_range_lower |
      | upper range       | input_range_upper |
      | location          | input_location    |
      | number of results | input_num_results |
