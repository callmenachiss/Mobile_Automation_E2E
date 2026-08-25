@login
Feature: Login
  As a Gajab member
  I want to log into the Gajab app
  So that I can start shopping with my own account

  @smoke @regression @gajab @login
  Scenario: Login with valid credentials
    When I log in with mobile number
    Then I should enter OTP received in my mobile device
    Then I logout from the Application


