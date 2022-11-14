@Tracking

Feature: Logging into Shipping feature

  Scenario Outline: Verifying that the user can use all tracking services feature
    Given I open the website in a browser main page
    When I checked specified "<tracking_number>" from documentation
    Then Fedex tracking page is presented with alert "<tracking_number>"

    Examples:
      | tracking_number |
      | 070358180009382 |

  Scenario Outline: Verifying that the user can use tracking service from main page
    Given I open the website in a browser main page
    When I checked specified "<tracking_number>" from main page
    Then Fedex tracking page is presented with alert "<tracking_number>"

    Examples:
      | tracking_number |
      | 070358180009382 |

  Scenario Outline: Verifying that the user can use tracking service from tracking wraper
    Given I open the website in a browser main page
    When I checked specified "<tracking_number>" from tracking wraper
    Then Fedex tracking page is presented with alert "<tracking_number>"

    Examples:
      | tracking_number |
      | 070358180009382 |
      |                 |

