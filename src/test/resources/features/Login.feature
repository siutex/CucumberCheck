@Login
Feature: Logging into the Fedex website

  Scenario Outline: Verifying that the user can login to the Fedex Website
    Given I open the website in a browser and close language window if exists
    When I login with credentials "<username>" and "<password>"
    Then Sufficient error message is presented "<username>" and "<password>"

    Examples:
      | username | password |
      | selenium | test     |
      |          | test     |
      | selenium |          |
