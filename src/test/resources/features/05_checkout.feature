@checkout
Feature: Checkout page
  As a shopper
  I want to enter my address, choose a payment method and place my order
  So that I can complete my purchase

  Background:
    Given I am logged in as a registered user

  @smoke @regression
  Scenario: Complete checkout with address and payment
    Given I have added the product "Wireless Headphones" to the cart
    And I open the cart
    And I proceed to checkout
    When I enter a shipping address
    And I select Cash on Delivery as the payment method
    And I place the order
    Then the order confirmation page should be displayed

  @regression
  Scenario: Shipping address is saved during checkout
    Given I have added the product "Wireless Headphones" to the cart
    And I open the cart
    And I proceed to checkout
    When I enter a shipping address
    Then the shipping address should be saved

  @regression
  Scenario: Checkout is blocked when the cart is empty
    Given I open the cart
    When I try to proceed to checkout with an empty cart
    Then a message should tell me the cart is empty
