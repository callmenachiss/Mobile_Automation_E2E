@login
Feature: Login
  As a customer
  I want to log into the Gajab website
  So that I can start bargain and shop the products

  Background:
    Given the login screen will be displayed

  @smoke @regression
  Scenario: Signup into the web application
    When I entered new Mobile number
    Then I should enter OTP
    Then I should complete account setup popup
    Then I should logout into the application

  @smoke @regression
  Scenario: Login into the web application
    When I entered Mobile number
    Then I should enter OTP
    Then I should logout into the application


