package com.ecommerce.mobile.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

/**
 * Page 1: Login screen (first screen shown when the app opens).
 *
 * NOTE ON LOCATORS: every @AndroidFindBy value below is a placeholder.
 * Open the app in Appium Inspector and replace each one with the real
 * resource-id/accessibility id from YOUR app. See README.md ->
 * "Finding element locators with Appium Inspector".
 */
public class LoginPage extends BasePage {

    private static final Logger LOGGER = LogManager.getLogger(LoginPage.class);

    @AndroidFindBy(id = "com.example.ecommerceapp:id/et_email")
    private WebElement emailInput;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/et_password")
    private WebElement passwordInput;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/btn_login")
    private WebElement loginButton;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/tv_error_message")
    private WebElement errorMessage;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/iv_app_logo")
    private WebElement appLogo;

    public boolean isLoginScreenDisplayed() {
        return isDisplayed(appLogo) && isDisplayed(loginButton);
    }

    public void login(String email, String password) {
        LOGGER.info("Logging in with email: {}", email);
        enterText(emailInput, email);
        enterText(passwordInput, password);
        hideKeyboard();
        tap(loginButton);
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessageText() {
        return getText(errorMessage);
    }
}
