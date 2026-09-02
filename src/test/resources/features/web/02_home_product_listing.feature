@home
Feature: Home page - Product listing
  As a Customer
  I want to browse, search and sort products on the Home page
  So that I can find and buy the product I want

  Background:
    Given the login screen will be displayed
    When I entered Mobile number "8484848484"
    Then I should enter OTP

  @smoke @regression @web
  Scenario: Search and purchase product workflow
    When I search for the product "Cosmetics"
    Then products should be available in search results
    Then I start bargain the products
    Then I click on pay now button
    And I selected PNB net banking for payment flow
    Then I verify purchased product details
    Then I should logout into the application

  @smoke @regression
  Scenario: Search and Bargain the product workflow
    When I search for the product "Decor"
    Then Target products should be available in search results
    Then I start first bargain the products
    Then I click on pay now button
    And I selected BOB net banking for payment flow
    Then I verify purchased product details
    Then I should logout into the application
