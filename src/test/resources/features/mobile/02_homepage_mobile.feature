@home
Feature: Home page - Product listing
  As a Customer
  I want to browse, search and sort products on the Home page
  So that I can find the product and start bargains which I want to buy

  Background:
    #Given I verify existing login
    #When I log in with existing mobile number
    #Then I should enter OTP received in my existing mobile device


  @regression
  Scenario: Purchase product workflow
    When I search the product in mobile "wireless neckband"
    Then I click on product to proceed purchase
    Then I proceed with payment to complete the order
    Then I do BOB internet banking for payment to place the order

  @regression
  Scenario: Bargain the product workflow
    When I search the product in mobile "Decor"
    Then I click on product to start bargain to proceed purchase
    Then I proceed with payment to complete the order
    Then I do PNB internet banking for payment to place the order

  @regression
  Scenario: Pin code selection workflow
    When I search the invalid pin code in mobile
    When I search the valid pin code in mobile
    Then I update Address in profile page
    Then I delete Address in profile page



