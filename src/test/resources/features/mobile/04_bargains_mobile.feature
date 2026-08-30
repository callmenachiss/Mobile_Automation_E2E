@bargain
Feature: Bargain - Product checkout page
  As a Customer
  I want to search and checkout products on the Home page
  So that I can do different level of bargains

  Background:
    #Given I verify existing login

  @regression
  Scenario: verify session based deal of the day product in display
    When I click on Category menu
    Then I comeback to Bazaar page to verify session based Gajab deal
    Then I click on Bargain menu
    Then I comeback to Bazaar page to verify session based Gajab deal



    




