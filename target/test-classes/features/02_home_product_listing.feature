@home
Feature: Home page - Product listing
  As a Customer
  I want to browse, search and sort products on the Home page
  So that I can find the product and start bargains which I want to buy

  Background:
    #When I log in with existing mobile number
    #Then I should enter OTP received in my existing mobile device


  @regression @home
  Scenario: Purchase product workflow
    Given I search for the product
    Then I click on product to proceed purchase
    Then I proceed with payment to complete the order


