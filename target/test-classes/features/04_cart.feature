@cart
Feature: Cart page
  As a shopper
  I want to review and adjust the items in my cart
  So that I only buy what I actually want

  Background:
    Given I am logged in as a registered user
    And I have added the product "Wireless Headphones" to the cart
    And I open the cart

  @smoke
  Scenario: Added product appears in the cart
    Then "Wireless Headphones" should be visible in the cart

  @regression
  Scenario: Update the quantity of an item in the cart
    When I increase the quantity of the first item in the cart
    Then the quantity of the first item should increase by 1

  @regression
  Scenario: Remove an item from the cart
    When I remove the first item from the cart
    Then the cart should be empty
