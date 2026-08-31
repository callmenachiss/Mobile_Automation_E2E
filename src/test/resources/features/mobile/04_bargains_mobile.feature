@bargain
Feature: Bargain - Product checkout page
  As a Customer
  I want to search and checkout products on the Home page
  So that I can do different level of bargains

  Background:
    #Given I verify existing login

  @regression
  Scenario: Verify the first Bargain attempt of the product between seller and customer
    When I search the product in mobile "Jewellery"
    Then I click on product to start bargain to proceed purchase
    Then I proceed with payment to complete the order
    Then I do BOB internet banking for payment to place the order


  @regression
  Scenario: Verify the multiple Bargain attempt of the product between seller and customer
    When I search the product in mobile "Brush"
    Then I click on product to start multiple bargains to proceed purchase
    Then I proceed with payment to complete the order
    Then I do PNB internet banking for payment to place the order

  @regression
  Scenario: Checkout and purchase product through COD workflow
    When I search the product in mobile "Evil Eye Rakhi"
    Then I click on product to start bargain to proceed purchase
    Then I proceed with COD payment to complete the order
    Then I do IDBI internet banking for payment to place the order



    




