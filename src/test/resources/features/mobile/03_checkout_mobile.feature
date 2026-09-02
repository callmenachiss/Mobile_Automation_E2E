@checkout
Feature: Checkout page - Product details page
  As a Customer
  I want to search and checkout products on the Home page
  So that I can start bargains and purchase the products

  @regression
  Scenario: verify session based deal of the day product in display
    Given I verify existing login
    When I log in with existing mobile number "8484848484"
    Then I should enter OTP received in my existing mobile device
    When I click on Category menu
    Then I comeback to Bazaar page to verify session based Gajab deal
    Then I click on Bargain menu
    Then I comeback to Bazaar page to verify session based Gajab deal

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

  #@regression
  #Scenario: verify product filtering based on price range flow
    #When I navigate to Home and Kitchen Menu
    #Then I verify products based on price range filter
    #Then I navigate to Stationery Menu
    #Then I verify products based on price range filter
    #Then I navigate to Toys and Games Menu
    #Then I verify products based on price range filter

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








