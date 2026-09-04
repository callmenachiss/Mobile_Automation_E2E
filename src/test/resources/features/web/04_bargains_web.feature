@bargain
Feature: Bargain - Product checkout page
  As a Customer
  I want to search and checkout products on the Home page in web application
  So that I can do different level of bargains

  Background:
    Given the login screen will be displayed
    When I entered Mobile number "8973029876"
    Then I should enter OTP

  @regression
  Scenario: Verify the first Bargain attempt flow of the product between seller and customer
    When I search for the product
    Then products should be available in search results
    Then I start first bargain the products
    Then I click on pay now button
    And I selected Canara net banking for payment flow
    Then I verify purchased product details
    Then I should logout into the application


  @regression
  Scenario: Verify the multiple Bargain attempt flow of the product between seller and customer
    When I search for the product
    Then products should be available in search results
    Then I start multiple bargain attempts for the products
    Then I click on pay now button
    And I selected PNB net banking for payment flow
    Then I verify purchased product details
    Then I should logout into the application

  @regression
  Scenario: Checkout and purchase the product through COD workflow in web application
    When I search for the product "Jewel Junction Woman & Girls Dainty Earrings"
    Then Target product should be available in search results
    Then I start multiple bargain attempts for the products
    Then I selected COD payment type for this purchase
    And I selected BOB net banking for payment flow
    Then I verify purchased product details
    Then I should logout into the application

  @regression
  Scenario: verify the specific product checkout workflow
    When I click on specific product in homepage
    Then I start first bargain the products
    Then I click on pay now button
    And I selected IDBI net banking for payment flow
    Then I verify purchased product details
    Then I should logout into the application





    




