package com.ecommerce.web.stepdefinitions;

import com.ecommerce.web.pages.HomePage;
import com.ecommerce.web.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * Step definitions for src/test/resources/features/web/01_login.feature.
 *
 * TEST DATA NOTE: replace VALID_EMAIL / VALID_PASSWORD with a real test
 * account for the site once it's known - same convention as the mobile
 * LoginSteps.
 */
public class LoginSteps {

    private static final String VALID_EMAIL = "piccosofttest@gmail.com";
    private static final String VALID_PASSWORD = "Password123!";

    private static final String INVALID_EMAIL = "invalid@example.com";
    private static final String INVALID_PASSWORD = "wrongPassword";

    private static final String VALID_NUMBER="9876543211";

    private final LoginPage loginPage = new LoginPage();
    private final HomePage homePage = new HomePage();

    @Given("the login screen will be displayed")
    public void the_login_screen_will_be_displayed() throws InterruptedException {
        loginPage.clickCloseTourpopup();
    }

    @When("I enterted Mobile number")
    public void I_enterted_Mobile_number() throws InterruptedException {
        loginPage.performLogin(VALID_NUMBER);
    }

    @When("I log in with an invalid email and password")
    public void i_log_in_with_an_invalid_email_and_password() {
        loginPage.login(INVALID_EMAIL, INVALID_PASSWORD);
    }


    @Then("I should enter OTP")
    public void I_should_enter_OTP() throws InterruptedException {
        loginPage.EnterOTP();
    }

    @Given("I am logged in as a registered user")
    public void i_am_logged_in_as_a_registered_user() {
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        Assert.assertTrue(homePage.isProductListDisplayed(), "Login did not lead to the Home page.");
    }
}
