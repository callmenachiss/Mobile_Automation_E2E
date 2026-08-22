@product-details
Feature: Product Details page
  As a shopper
  I want to see full product information and add it to my cart
  So that I can decide whether to buy it

  Background:
    Given I am logged in as a registered user
    And I open the product "Wireless Headphones"

  @smoke
  Scenario: Product details are displayed correctly
    Then the product name, price, image and description should be displayed

  @smoke @regression
  Scenario: Add a product to the cart
    When I add the product to the cart
    Then a confirmation message should be displayed

  @regression
  Scenario: Increase the product quantity before adding to cart
    When I increase the product quantity by 2
    Then the selected quantity should be 3
