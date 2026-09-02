@login
Feature: Login
  As a Gajab member
  I want to log into the Gajab app
  So that I can start shopping with my own account

  @smoke @regression
  Scenario: Signup into the Application for new user
    #Given I verify existing login
    When I log in with mobile number
    Then I should enter OTP received in my mobile device
    Then I logout from the Application

  @smoke @regression
  Scenario: Login with valid credentials
    When I log in with existing mobile number "8585858585"
    Then I should enter OTP received in my existing mobile device
    #Then I logout from the Application