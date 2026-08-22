@login
Feature: Login
  As a shopper
  I want to log into the Ecommerce app
  So that I can start shopping with my own account

  @smoke
  Scenario: Login screen is displayed when the app opens
    Then the login screen should be displayed

  @smoke @regression
  Scenario: Login with valid credentials
    When I log in with a valid email and password
    Then I should be taken to the Home page

  @regression
  Scenario: Login with invalid credentials shows an error
    When I log in with an invalid email and password
    Then an error message should be displayed

  # This scenario is a starter example - its step definitions in LoginSteps.java
  # are left as TODO stubs for you to fill in yourself.
  @smoke @regression
  Scenario: Login successful
    When I submit the login form with valid credentials
    Then I should see the Home page with my account logged in
