package com.ecommerce.web.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Login page for the Ecommerce website (the web counterpart of the mobile
 * LoginPage, same app/flow).
 *
 * NOTE ON LOCATORS: every @FindBy value below is a placeholder. Open the
 * site in the browser DevTools and replace each one with the real id/css
 * selector from the actual site.
 */
public class LoginPage extends BaseWebPage {

    private static final Logger LOGGER = LogManager.getLogger(LoginPage.class);

    @FindBy(id = "et_email")
    private WebElement emailInput;

    @FindBy(id = "et_password")
    private WebElement passwordInput;

    @FindBy(id = "btn_login")
    private WebElement loginButton;

    @FindBy(id = "tv_error_message")
    private WebElement errorMessage;

    @FindBy(id = "iv_app_logo")
    private WebElement appLogo;

    public boolean isLoginScreenDisplayed() {
        return isDisplayed(appLogo) && isDisplayed(loginButton);
    }

    public void login(String email, String password) {
        LOGGER.info("Logging in with email: {}", email);
        enterText(emailInput, email);
        enterText(passwordInput, password);
        click(loginButton);
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessageText() {
        return getText(errorMessage);
    }
}
