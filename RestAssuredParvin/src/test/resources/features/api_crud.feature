Feature: Web Application - Login and Navigation
  Background:
    Given the application URL is "http://192.168.1.47:8085"

  Scenario: Successful login with valid credentials
    Given I am on the login page
    When I enter username "admin"
    And I enter password "admin123"
    And I click the login button
    Then I should be redirected to the dashboard

  Scenario: Failed login with invalid credentials
    Given I am on the login page
    When I enter username "invalid"
    And I enter password "wrongpass"
    And I click the login button
    Then I should see an error message

  Scenario: Logout functionality
    Given I am logged in with username "admin" and password "admin123"
    When I click the logout button
    Then I should be redirected to the login page

  Scenario: View dashboard after login
    Given I am logged in with username "admin" and password "admin123"
    Then I should see customer count
    And I should see employee count

  Scenario: Access customers page requires login
    Given I am on the login page
    When I try to access the customers page directly
    Then I should be redirected to the login page

  Scenario: View customers list
    Given I am logged in with username "admin" and password "admin123"
    When I navigate to the customers page
    Then I should see the customers list

  Scenario: Search customers
    Given I am logged in with username "admin" and password "admin123"
    When I navigate to the customers page
    And I search for a customer
    Then the customer list should be filtered

  Scenario: View employees list
    Given I am logged in with username "admin" and password "admin123"
    When I navigate to the employees page
    Then I should see the employees list

  Scenario: Filter employees by department
    Given I am logged in with username "admin" and password "admin123"
    When I navigate to the employees page
    And I filter by department
    Then the employee list should be filtered

