package com.ecommerce.mobile.stepdefinitions;

import com.ecommerce.mobile.pages.HomePage;
import com.ecommerce.mobile.pages.LoginPage;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * Step definitions for src/test/resources/features/01_login.feature.
 *
 * TEST DATA NOTE: replace VALID_EMAIL / VALID_PASSWORD with a real test
 * account for your app. Keeping test data here (instead of scattered in
 * feature files) makes it easy for anyone to find and update.
 */
public class LoginSteps {

    private static final String VALID_EMAIL = "standarduser@example.com";
    private static final String VALID_PASSWORD = "Password123!";
    private static final String INVALID_EMAIL = "invalid@example.com";
    private static final String INVALID_PASSWORD = "wrongPassword";

    private final LoginPage loginPage = new LoginPage();
    private final HomePage homePage = new HomePage();

    @Then("the login screen should be displayed")
    public void the_login_screen_should_be_displayed() {
        Assert.assertTrue(loginPage.isLoginScreenDisplayed(), "Login screen was not displayed.");
    }


    @When("I log in with a valid email and password")
    public void i_log_in_with_a_valid_email_and_password() {
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
    }

    @Then("I should be taken to the Home page")
    public void i_should_be_taken_to_the_home_page() {
        Assert.assertTrue(homePage.isProductListDisplayed(), "Home page was not displayed after login.");
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
