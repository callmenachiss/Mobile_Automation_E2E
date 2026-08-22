@home
Feature: Home page - Product listing
  As a shopper
  I want to browse, search and sort products on the Home page
  So that I can find the product I want to buy

  Background:
    Given I am logged in as a registered user

  @smoke
  Scenario: Product list is displayed on the Home page
    Then a list of products should be displayed

  @regression
  Scenario: Search for a product by name
    When I search for the product "Wireless Headphones"
    Then "Wireless Headphones" should be visible in the search results

  @regression
  Scenario: Sort products by price
    When I sort the products by price
    Then the product list should still be displayed

  @smoke @regression
  Scenario: Open a product from the list
    When I open the product "Wireless Headphones"
    Then the product details page should be displayed
