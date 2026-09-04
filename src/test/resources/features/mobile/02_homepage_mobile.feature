@home
Feature: Home page - Product listing
  As a Customer
  I want to browse, search and sort products on the Home page
  So that I can find the product and start bargains which I want to buy

  @regression
  Scenario: Purchase product workflow
    Given verify existing session
    When I search the product in mobile
    Then I click on product to proceed purchase
    Then I proceed with payment to complete the order
    Then I do BOB internet banking for payment to place the order

  @regression
  Scenario: Bargain the product workflow
    When I search the product in mobile
    Then I click on product to start bargain to proceed purchase
    Then I proceed with payment to complete the order
    Then I do PNB internet banking for payment to place the order

  @regression
  Scenario: Share product details through email flow
    When I search the product in mobile
    Then I click on the product from the list
    Then I share the product details through email

  @regression
  Scenario: verify most bargained products in Trending section flow
    When I navigate to trending page of the application
    Then I verify anyone trending products in the list
    And I verify one more products in trending section

  @regression
  Scenario: Pin code selection workflow
    When I search the invalid pin code in mobile
    When I search the valid pin code in mobile
    Then I update Address in profile page
    Then I delete Address in profile page




