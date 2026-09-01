@checkout
Feature: Checkout page - Product details page
  As a Customer
  I want to search and checkout products on the Home page
  So that I can start bargains and purchase the products

  @regression
  Scenario: verify session based deal of the day product in display
    When I click on Category menu
    Then I comeback to Bazaar page to verify session based Gajab deal
    Then I click on Bargain menu
    Then I comeback to Bazaar page to verify session based Gajab deal

  @regression
  Scenario: Share product details through email flow
    When I search the product in mobile "Gifts"
    Then I click on the product from the list
    Then I share the product details through email

  @regression
  Scenario: verify most bargained products in Trending section flow
    When I navigate to trending page of the application
    Then I verify anyone trending products in the list
    And I verify one more products in trending section

  @regression
  Scenario: verify the latest live order flow
    When I search the product in mobile "Cosmetics"
    Then I click on product to start bargain to proceed purchase
    Then I proceed with payment to complete the order
    Then I do IDBI internet banking for payment to place the order
    And I verify the placed orders in the live order section

  @regression
  Scenario: verify the filters and sorting functionality
    When I navigate to Home and Kitchen Menu
    Then I setup filters in this product list page
    And I will apply sorting items in product list page

  @regression
  Scenario: verify the product details in all specific category flow
    When I navigate to Home and Kitchen Menu
    Then I verify products based on this category
    Then I navigate to Gifting and Festive Essentials Menu
    Then I verify products based on this category

  @regression
  Scenario: verify filtering products using brand Sera's basket flow
    When I navigate to Home and Kitchen Menu
    Then I verify products based on brand Sera's basket filter
    Then I navigate to Gifting and Festive Essentials Menu
    Then I verify products based on brand Sera's basket filter
    Then I navigate to Toys and Games Menu
    Then I verify products using brand Sera's basket filter
    Then I navigate to Stationery Menu
    Then I verify products using brand Sera's basket filter

  @regression @test
  Scenario: verify product filtering based on price range flow
    When I navigate to Home and Kitchen Menu
    Then I verify products based on price range filter
    Then I navigate to Stationery Menu
    Then I verify products based on price range filter
    Then I navigate to Toys and Games Menu
    Then I verify products based on price range filter

  @regression
  Scenario: verify specific product checkout flow
    When I select and click specific product in homepage
    Then I start bargain to proceed purchase
    Then I proceed with payment to complete the order
    Then I do IDBI internet banking for payment to place the order





