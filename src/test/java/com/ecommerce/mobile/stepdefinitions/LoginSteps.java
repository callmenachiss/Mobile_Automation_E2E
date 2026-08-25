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
 * Step definitions for src/test/resources/features/01_login.feature.
 *
 * TEST DATA NOTE: replace VALID_EMAIL / VALID_PASSWORD with a real test
 * account for your app. Keeping test data here (instead of scattered in
 * feature files) makes it easy for anyone to find and update.
 */
public class LoginSteps {


    private static final String VALID_PASSWORD = "Password123!";
    private static final String INVALID_PASSWORD = "wrongPassword";

    private static final String VALID_EMAIL = "piccosofttest@gmail.com";
    private static final String VALID_PHONENUMBER = "8973029876";

    private static final String INVALID_EMAIL = "invalid@example.com";
    private static final String INVALID_PHONENUMBER = "3794892927";

    private static final String TEST_OTP = "123456";

    private final LoginPage loginPage = new LoginPage();
    private final HomePage homePage = new HomePage();

    @Then("the login screen should be displayed")
    public void the_login_screen_should_be_displayed() {
        Assert.assertTrue(loginPage.isLoginScreenDisplayed(), "Login screen was not displayed.");
    }


    @When("I log in with mobile number")
    public void i_log_in_with_mobile_number() throws InterruptedException {
        loginPage.clickGetStartedButton();
        Assert.assertTrue(loginPage.isMemberLoginLabelDisplayed(), "Gajab Member login label is not displayed");
        Assert.assertTrue(loginPage.isAgeConfirmationLabelDisplayed(), "Age confirmation message is not displayed");
        loginPage.enterRandomPhoneNumber();
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

    @When("I log in with an invalid email and password")
    public void i_log_in_with_an_invalid_email_and_password() {
        loginPage.login(INVALID_EMAIL, INVALID_PASSWORD);
    }

    @Then("an error message should be displayed")
    public void an_error_message_should_be_displayed() {
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message was not displayed for invalid login.");
    }

    /**
     * Shared step reused as a Background in most other feature files,
     * so every feature can start from a known, logged-in state.
     */
    @Given("I am logged in as a registered user")
    public void i_am_logged_in_as_a_registered_user() {
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        Assert.assertTrue(homePage.isProductListDisplayed(), "Login did not lead to the Home page.");
    }

    // =====================================================================
    // STARTER EXAMPLE - "Login successful" scenario.
    // These two steps are intentionally left unimplemented (PendingException)
    // for you to fill in. You already have loginPage / homePage available
    // above, and VALID_EMAIL / VALID_PASSWORD as test data - e.g.:
    //   loginPage.login(VALID_EMAIL, VALID_PASSWORD);
    //   Assert.assertTrue(homePage.isProductListDisplayed(), "...");
    // =====================================================================

    @When("I submit the login form with valid credentials")
    public void i_submit_the_login_form_with_valid_credentials() {
        // TODO: implement this step
        throw new PendingException();
    }

    @Then("I should see the Home page with my account logged in")
    public void i_should_see_the_home_page_with_my_account_logged_in() {
        // TODO: implement this step
        throw new PendingException();
    }
}
