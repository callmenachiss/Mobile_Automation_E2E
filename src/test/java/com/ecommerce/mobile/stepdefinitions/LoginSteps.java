package com.ecommerce.mobile.stepdefinitions;

import com.ecommerce.mobile.pages.HomePage;
import com.ecommerce.mobile.pages.LoginPage;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.logging.Level;

/**
 * Step definitions for src/test/resources/features/mobile/01_login.feature.
 *
 * TEST DATA NOTE: replace VALID_EMAIL / VALID_PASSWORD with a real test
 * account for your app. Keeping test data here (instead of scattered in
 * feature files) makes it easy for anyone to find and update.
 */
public class LoginSteps {


    private static final String VALID_PASSWORD = "Password123!";
    private static final String INVALID_PASSWORD = "wrongPassword";

    private static final String VALID_EMAIL = "piccosofttest@gmail.com";
    private static final String VALID_PHONENUMBER = "9876543211";

    private static final String INVALID_EMAIL = "invalid@example.com";
    private static final String INVALID_PHONENUMBER = "3794892927";

    private static final String TEST_OTP = "123456";

    private final LoginPage loginPage = new LoginPage();
    private final HomePage homePage = new HomePage();

    @Then("the login screen should be displayed")
    public void the_login_screen_should_be_displayed() {
        Assert.assertTrue(loginPage.isLoginScreenDisplayed(), "Login screen was not displayed.");
    }

    @Given("I verify existing login")
    public void I_verify_existing_login() {
        Assert.assertTrue(homePage.isMenuDisplayed(), "Existing Login was not deducted.");
        homePage.clickProfileAvatar();
        homePage.clickPersonalDetails();
        homePage.clickLogout();
        Assert.assertTrue(loginPage.isGetStartedLabelDisplayed(), "Get Started was not displayed on the splash page.");
    }


    @When("I log in with mobile number")
    public void i_log_in_with_mobile_number() throws InterruptedException {
        loginPage.clickGetStartedButton();
        //Assert.assertTrue(loginPage.isMemberLoginLabelDisplayed(), "Gajab Member login label is not displayed");
        //Assert.assertTrue(loginPage.isAgeConfirmationLabelDisplayed(), "Age confirmation message is not displayed");
        loginPage.enterRandomPhoneNumber();
        loginPage.clickCheckbox();
        loginPage.clickNext();
    }

    @When("I log in with existing mobile number")
    public void i_log_in_with_existing_mobile_number() throws InterruptedException {
        loginPage.clickGetStartedButton();
        //Assert.assertTrue(loginPage.isMemberLoginLabelDisplayed(), "Gajab Member login label is not displayed");
        //Assert.assertTrue(loginPage.isAgeConfirmationLabelDisplayed(), "Age confirmation message is not displayed");
        loginPage.enterValidPhoneNUmber(VALID_PHONENUMBER);
        loginPage.clickCheckbox();
        loginPage.clickNext();
    }

    @Then("I should enter OTP received in my mobile device")
    public void i_should_enter_OTP_received_in_my_mobile_device() throws InterruptedException {
        Assert.assertTrue(loginPage.isAccountSetupMessageDisplayed(), "Account setup message is not displayed");
        loginPage.enterOtp(TEST_OTP);
        Assert.assertTrue(loginPage.isAccountSetupHeaderDisplayed(), "Account setup screen is not displayed");
        loginPage.enterRandomName();
        loginPage.selectFemale();
        loginPage.clickNext();
        //loginPage.clickEnglishOption();
        //loginPage.handleOptionalLocationPermission();
    }

    @Then("I should enter OTP received in my existing mobile device")
    public void i_should_enter_OTP_received_in_my_existing_mobile_device() throws InterruptedException {
        //Assert.assertTrue(loginPage.isAccountSetupMessageDisplayed(), "Account setup message is not displayed");
        loginPage.enterOtp(TEST_OTP);
        //Assert.assertTrue(homePage.isHomePageDisplayed(), "User not landed in HomePage");
    }

    @When("I log in with an invalid email and password")
    public void i_log_in_with_an_invalid_email_and_password() {
        loginPage.login(INVALID_EMAIL, INVALID_PASSWORD);
    }

    @Then("an error message should be displayed")
    public void an_error_message_should_be_displayed() {
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message was not displayed for invalid login.");
    }


    @Given("I am logged in as a registered user")
    public void i_am_logged_in_as_a_registered_user() {
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        Assert.assertTrue(homePage.isProductListDisplayed(), "Login did not lead to the Home page.");
    }


}
