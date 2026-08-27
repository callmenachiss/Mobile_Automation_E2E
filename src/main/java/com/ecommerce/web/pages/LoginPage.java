package com.ecommerce.web.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

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

    @FindBy(name = "fullName")
    private WebElement fullNameInput;

    @FindBy(xpath = "//span[normalize-space(text())='Male']")
    private WebElement Maleradiobtn;

    @FindBy(xpath = "//button[normalize-space(text())='Next']")
    private WebElement Nextbtn;







    public void login(String email, String password) {
        LOGGER.info("Logging in with email: {}", email);
        enterText(emailInput, email);
        enterText(passwordInput, password);
        clickSafely(loginButton);
        LOGGER.info("User clicked Login button");
    }

    public String generateMaleName() {
        Random random = new Random();
        return MALE_NAMES[random.nextInt(MALE_NAMES.length)];
    }

    public void enterDetailsforAccount() throws InterruptedException {
        String UserName=generateMaleName()+"Automation";
        LOGGER.info("User fill up the account setup page for {}", UserName);
        Thread.sleep(2000);
        enterText(fullNameInput, UserName);
        Thread.sleep(1000);
        click(Maleradiobtn);
        LOGGER.info("User selected male option in gender menu");
        Thread.sleep(1000);
        click(Nextbtn);
        Thread.sleep(2000);
        LOGGER.info("Account setup done for {}", UserName);
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
        LOGGER.info("Starting web login flow");
        closeTourPopupIfPresent();
    }

    public void performLogin(String number) throws InterruptedException {
        clickSafely(loginSignupButton);
        LOGGER.info("User clicked Login / Sign up");
        enterTextSafely(telephoneInput, number);
        LOGGER.info("User entered telephone number");
        sleep(2000);
        clickCheckbox();
        sleep(2000);
    }

    public void performLoginforNewUser() throws InterruptedException {
        String newPhoneNumber=generateMobileNumber();
        clickSafely(loginSignupButton);
        LOGGER.info("User clicked Login / Sign up button");
        enterTextSafely(telephoneInput, newPhoneNumber);
        LOGGER.info("User entered telephone number {}", newPhoneNumber);
        sleep(2000);
        clickCheckbox();
        sleep(2000);
    }

    public void EnterOTP() throws InterruptedException {
        clickSafely(otpButton);
        LOGGER.info("User clicked Request OTP");
        sleep(3000);
        enterOtp(testOtp);
        LOGGER.info("Login / Sign up success");
    }


    /*private void closeTourPopupIfPresent() {
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
    }*/

    private void closeTourPopupIfPresent() {
        try {
            clickDuration(closeButton, 5);
            LOGGER.info("User clicked X icon to close tour popup");
        } catch (TimeoutException e) {
            LOGGER.info("Tour popup is not present.");
        } catch (Exception e) {
            LOGGER.warn("Unable to close tour popup: {}", e.getMessage());
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