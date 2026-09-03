@home
Feature: Home page - Product listing
  As a Customer
  I want to browse, search and sort products on the Home page
  So that I can find and buy the product I want

  Background:
    Given the login screen will be displayed
    When I entered Mobile number "8973029876"
    Then I should enter OTP

  @regression
  Scenario: Search and purchase product workflow
    When I search for the product
    Then products should be available in search results
    Then I start bargain the products
    Then I click on pay now button
    And I selected PNB net banking for payment flow
    Then I verify purchased product details
    Then I should logout into the application

  @regression
  Scenario: Search and Bargain the product workflow
    When I search for the product
    Then Target products should be available in search results
    Then I start first bargain the products
    Then I click on pay now button
    And I selected BOB net banking for payment flow
    Then I verify purchased product details
    Then I should logout into the application

  @regression
  Scenario: Share product details to email from web application
    When I search for the product
    Then Target products should be available in search results
    Then I validate the product link details to share info through email
    Then I should logout into the application

  @regression
  Scenario: verify most bargained products in Trending section flow
    When I navigate to most bargained products page
    Then I comback to homepage and again verify most bargained products menu
    Then I start first bargain the products
    Then I click on pay now button
    And I selected IDBI net banking for payment flow
    Then I verify purchased product details
    Then I should logout into the application

  @regression
  Scenario: Pin code selection workflow in web
    When I search the pin code in location menubar "600064"
    Then I navigate to Addresses Menu page
    And I add new address to the profile
    When I search for the product
    Then Target products should be available in search results
    Then I start bargain the products
    And I verify address details for this order
    Then I delete the Address in profile page
    Then I should logout into the application

