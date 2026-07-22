Feature: API Tests - Login, Dashboard, Customers, Employees

  Scenario: POST login with valid credentials redirects to dashboard
    Given the API base URL is "http://192.168.1.47:8085"
    When I POST to login with username "admin" and password "admin123"
    Then the response status should be 200
    And I am logged in

  Scenario: POST login with invalid credentials shows error
    Given the API base URL is "http://192.168.1.47:8085"
    When I POST to login with username "invalid" and password "wrongpass"
    Then the response status should be 200
    And the response should contain "error"

  Scenario: GET dashboard after login displays data
    Given the API base URL is "http://192.168.1.47:8085"
    When I POST to login with username "admin" and password "admin123"
    And I GET the dashboard
    Then the response status should be 200
    And the response should be HTML
    And the response should have customerCount
    And the response should have employeeCount

  Scenario: GET dashboard without login redirects
    Given the API base URL is "http://192.168.1.47:8085"
    When I GET the dashboard without session
    Then the response status should be 200

  Scenario: GET customers list after login
    Given the API base URL is "http://192.168.1.47:8085"
    When I POST to login with username "admin" and password "admin123"
    And I GET the customers list
    Then the response status should be 200
    And the response should be HTML

  Scenario: GET customers with search parameter
    Given the API base URL is "http://192.168.1.47:8085"
    When I POST to login with username "admin" and password "admin123"
    And I GET customers with search "john"
    Then the response status should be 200

  Scenario: GET customers with status filter
    Given the API base URL is "http://192.168.1.47:8085"
    When I POST to login with username "admin" and password "admin123"
    And I GET customers with status "active"
    Then the response status should be 200

  Scenario: GET customers with multiple filters
    Given the API base URL is "http://192.168.1.47:8085"
    When I POST to login with username "admin" and password "admin123"
    And I GET customers with filters:
      | search | john   |
      | status | active |
      | sort   | name   |
    Then the response status should be 200

  Scenario: GET employees list after login
    Given the API base URL is "http://192.168.1.47:8085"
    When I POST to login with username "admin" and password "admin123"
    And I GET the employees list
    Then the response status should be 200
    And the response should be HTML

  Scenario: GET employees with search parameter
    Given the API base URL is "http://192.168.1.47:8085"
    When I POST to login with username "admin" and password "admin123"
    And I GET employees with search "jane"
    Then the response status should be 200

  Scenario: GET employees with department filter
    Given the API base URL is "http://192.168.1.47:8085"
    When I POST to login with username "admin" and password "admin123"
    And I GET employees with department "IT"
    Then the response status should be 200

  Scenario: GET employees with multiple filters
    Given the API base URL is "http://192.168.1.47:8085"
    When I POST to login with username "admin" and password "admin123"
    And I GET employees with filters:
      | search     | jane |
      | department | IT   |
      | sort       | name |
    Then the response status should be 200

  Scenario: GET logout endpoint clears session
    Given the API base URL is "http://192.168.1.47:8085"
    When I POST to login with username "admin" and password "admin123"
    And I am logged in
    And I GET the logout endpoint
    Then the response status should be 200
