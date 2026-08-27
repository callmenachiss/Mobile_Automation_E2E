@home
Feature: Home page - Product listing
  As a Customer
  I want to browse, search and sort products on the Home page
  So that I can find and buy the product I want

  Background:
    Given the login screen will be displayed
    When I entered Mobile number
    Then I should enter OTP

  @smoke @regression @home
  Scenario: Search for a product
    When I search for the product "Wireless Headphones"
    Then products should be available in search results
    Then I start bargain the products

