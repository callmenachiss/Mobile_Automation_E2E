@checkout
Feature: Checkout page - Product details page
  As a Customer
  I want to search and checkout products on the Home page in web application
  So that I can start bargains and purchase the products

  Background:
    Given the login screen will be displayed
    When I entered Mobile number "9876543211"
    Then I should enter OTP

  @regression
  Scenario: verify session based deal of the day product in display
    When I navigate to most bargained products page
    Then I comeback to homepage and again verify most bargained products menu
    Then I navigate to deal of the day products page
    Then I comeback to homepage and again verify deal of the day products menu
    Then I should logout into the application

  @regression
  Scenario: verify the latest live order flow
    When I search for the product
    Then products should be available in search results
    Then I start bargain the products
    Then I click on pay now button
    And I selected PNB net banking for payment flow
    Then I verify purchased product details
    And I verify our orders in live orders section
    Then I should logout into the application

  @regression
  Scenario: verify the filters and sorting functionality
    When I navigate to Home and Kitchen Menu in web application
    Then I setup filters in this product list page in web application
    And I setup brand filters in this product list page in web application
    Then I navigate to Toys and Games Menu in web application
    Then I setup filters in this product list page in web application
    And I setup brand filter in this product list page in web application
    Then I should logout into the application


  @regression
  Scenario: verify the product details in all specific category flow
    When I navigate to Home and Kitchen Menu in web application
    Then I setup filters in this product list page in web application
    Then I navigate to Toys and Games Menu in web application
    Then I setup filters in this product list page in web application
    Then I navigate to Fashion Accessories Menu in web application
    Then I setup filters in this product list page in web application
    Then I should logout into the application

  @regression
  Scenario: verify filtering products using brand Sera's basket flow
    When I navigate to Home and Kitchen Menu in web application
    Then I setup Sera Basket filters for Home and Kitchen page
    And I setup brand filters in this product list page in web application
    Then I navigate to Toys Games Menu in web application
    Then I setup Sera Basket filters for Toys and Games Page
    And I setup brand filters for Toys and Games Page
    Then I should logout into the application

  @regression
  Scenario: verify the product filtering based on price range section workflow
    When I navigate to Home and Kitchen Menu in web application
    Then I verify the products based on price range filter
    Then I navigate to Toys Games Menu in web application
    Then I verify the products based on price range filter
    Then I navigate to Fashion Accessories Menu in web application
    Then I verify the products based on price range filter
    Then I should logout into the application




