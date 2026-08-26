@home
Feature: Home page - Product listing
  As a Customer
  I want to browse, search and sort products on the Home page
  So that I can find and buy the product I want

  Background:
    Given I am logged in as a registered user

  @smoke @regression @home
  Scenario: Search for a product
    When I search for the product "Wireless Headphones"
    Then "Wireless Headphones" should be visible in the search results

  @regression @home
  Scenario: Sort products by price
    When I sort the products by price
    Then the product list should still be displayed
