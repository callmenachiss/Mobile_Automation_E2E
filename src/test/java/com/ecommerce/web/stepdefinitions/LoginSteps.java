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
        loginPage.refreshPage();
    }

    @When("I entered Mobile number")
    public void I_entered_Mobile_number() throws InterruptedException {
        loginPage.performLogin(VALID_NUMBER);
    }

    @When("I entered Mobile number {string}")
    public void I_entered_Mobile_number(String phoneNumber) throws InterruptedException {
        loginPage.performLogin(phoneNumber);
    }

    @When("I entered new Mobile number")
    public void I_entered_new_Mobile_number() throws InterruptedException {
        loginPage.performLoginforNewUser();
    }

    @Then("I should enter OTP")
    public void I_should_enter_OTP() throws InterruptedException {
        loginPage.EnterOTP();
    }



    @Then("I should complete account setup popup")
    public void iShouldCompleteAccountSetupPopup() throws InterruptedException {
        loginPage.enterDetailsforAccount();
    }
}
