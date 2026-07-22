Feature: UI smoke
  Scenario: Open base URL
    Given the API base URL is "http://192.168.1.47:8085"
    When I open the application
    Then the page opens
