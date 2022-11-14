@Shipping
Feature: Logging into Shipping feature

  Scenario Outline: Verifying that the user can enter SHIP NOW section
    Given I open the website in a browser and go to all shipping services
    When I login with credentials "<username>" and "<password>" to SHIP NOW feature
    Then Sufficient error message is for inside Login presented "<username>" and "<password>"

    Examples:
      | username | password |
      | selenium | test     |
      |          | test     |
      | selenium |          |

