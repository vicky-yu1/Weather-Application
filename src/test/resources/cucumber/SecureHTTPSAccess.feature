Feature: Securely access the web application via HTTPS

  Background: Access the Secure URL at Port 8443
    Given I access the secure url

  Scenario: The login page is correctly displayed
    Then The login page is correctly displayed;