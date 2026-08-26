@login
Feature: Login
  As a customer
  I want to log into the Ecommerce website
  So that I can start shopping with my own account

  @smoke @regression
  Scenario: Login with valid credentials
    Given the login screen should be displayed
    When I log in with valid credentials
    Then I should land on the Home page

  @regression
  Scenario: Login with invalid credentials
    Given the login screen should be displayed
    When I log in with an invalid email and password
    Then an error message should be displayed
