package com.ecommerce.web.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class LoginPage extends BaseWebPage {

    private static final Logger LOGGER = LogManager.getLogger(LoginPage.class);
    private final String testOtp = "123456";

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

    @FindBy(css = "img[alt='Close']")
    private WebElement closeButton;

    @FindBy(xpath = "//*[contains(normalize-space(.),'Start Bargaining')]")
    private WebElement startBargainingTourImage;

    @FindBy(xpath = "//div[normalize-space(text())='Log in / Sign up']")
    private WebElement loginSignupButton;

    @FindBy(xpath = "//input[@type='tel']")
    private WebElement telephoneInput;

    @FindBy(xpath = "//input[@type='checkbox']")
    private WebElement checkboxButton;

    @FindBy(xpath = "//button[contains(normalize-space(.),'Request OTP')]")
    private WebElement otpButton;

    @FindBy(xpath = "//p[contains(normalize-space(.),'OTP sent to your mobile number')]")
    public WebElement otpToastMessageLbl;

    @FindBy(xpath = "//input[@inputmode='numeric']")
    private List<WebElement> otpFields;



    public void login(String email, String password) {
        LOGGER.info("Logging in with email: {}", email);

        enterText(emailInput, email);
        enterText(passwordInput, password);
        clickSafely(loginButton);

        LOGGER.info("User clicked Login button");
    }
    public void enterOtp(String otp) {
        if (otp == null || otp.length() != 6) {
            throw new IllegalArgumentException("OTP must contain exactly 6 digits");
        }

        wait.until(ExpectedConditions.numberOfElementsToBe(
                By.xpath("//input[@inputmode='numeric']"),
                6
        ));

        List<WebElement> fields = driver.findElements(
                By.xpath("//input[@inputmode='numeric']")
        );

        LOGGER.info("OTP fields found: {}", fields.size());

        for (int i = 0; i < otp.length(); i++) {
            fields.get(i).sendKeys(String.valueOf(otp.charAt(i)));
        }

        LOGGER.info("OTP entered successfully");
    }


    public void clickCloseTourpopup() throws InterruptedException {
        LOGGER.info("Starting mobile login flow");
        closeTourPopupIfPresent();
    }

    public void performLogin(String number) throws InterruptedException {
        clickSafely(loginSignupButton);
        LOGGER.info("User clicked Login / Sign up");
        enterTextSafely(telephoneInput, number);
        LOGGER.info("User entered telephone number");
        Thread.sleep(2000);
        clickCheckbox();
        Thread.sleep(2000);
    }

    public void EnterOTP() throws InterruptedException {
        clickSafely(otpButton);
        LOGGER.info("User clicked Request OTP");
        Thread.sleep(3000);
        enterOtp(testOtp);
    }


    private void closeTourPopupIfPresent() {
        try {
            wait.until(driver -> {
                try {
                    return closeButton.isDisplayed();
                } catch (Exception e) {
                    return false;
                }
            });

            clickSafely(closeButton);
            LOGGER.info("User clicked X icon to close tour popup");

        } catch (Exception e) {
            LOGGER.info("Tour popup is not present.");
        }
    }


    private void enterTextSafely(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));

            scrollIntoView(element);

            element.clear();
            element.sendKeys(text);

        } catch (ElementNotInteractableException e) {
            LOGGER.warn("Element not interactable. Retrying after scrolling.");

            scrollIntoView(element);

            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));

            element.clear();
            element.sendKeys(text);
        }
    }


    private void clickSafely(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            scrollIntoView(element);
            wait.until(ExpectedConditions.elementToBeClickable(element));

            element.click();

        } catch (ElementClickInterceptedException e) {
            LOGGER.warn("Click intercepted. Retrying after scrolling.");

            scrollIntoView(element);

            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();

        } catch (ElementNotInteractableException e) {
            LOGGER.warn("Element not interactable. Retrying after scrolling.");

            scrollIntoView(element);

            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        }
    }


    private void clickCheckbox() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@type='checkbox']")
            ));

            scrollIntoView(checkboxButton);

            if (!checkboxButton.isSelected()) {
                try {
                    checkboxButton.click();

                } catch (ElementNotInteractableException e) {
                    LOGGER.warn(
                            "Checkbox is hidden with opacity-0. Using JavaScript click."
                    );

                    executeJavaScript(
                            "arguments[0].click();",
                            checkboxButton
                    );
                }
            }

            LOGGER.info(
                    "Checkbox selected: {}",
                    checkboxButton.isSelected()
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to select checkbox.",
                    e
            );
        }
    }
}