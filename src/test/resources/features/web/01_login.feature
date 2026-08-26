@login
Feature: Login
  As a customer
  I want to log into the Gajab website
  So that I can start bargain and shop the products

  @smoke @regression @web
  Scenario: Login into the web application
    Given the login screen will be displayed
    When I enterted Mobile number
    Then I should enter OTP


