@order-confirmation
Feature: Order Confirmation page
  As a shopper
  I want a clear confirmation after placing an order
  So that I know my purchase was successful

  Background:
    Given I am logged in as a registered user
    And I have added the product "Wireless Headphones" to the cart
    And I open the cart
    And I proceed to checkout
    And I enter a shipping address
    And I select Cash on Delivery as the payment method

  @smoke @regression
  Scenario: Order confirmation is displayed after placing an order
    When I place the order
    Then the order confirmation page should display a success message and an order number
