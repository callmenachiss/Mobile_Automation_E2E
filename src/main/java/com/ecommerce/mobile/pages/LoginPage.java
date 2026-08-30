package com.ecommerce.mobile.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.util.Random;

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

    //locators for gajab application
    @AndroidFindBy(accessibility = "welcome_get_started_button")
    private WebElement getStartedButton;

    @AndroidFindBy(accessibility = "Become a Gajab Member\nLogin with Mobile Number")
    private WebElement memberLoginLabel;

    @AndroidFindBy(accessibility = "By continuing, you confirm that you are above 18 years of age, and you agree to Gajab's ")
    private WebElement ageConfirmationLabel;

    //@AndroidFindBy(xpath = "//android.widget.EditText")

    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id=\"login_mobile_number_field\"]")
    private WebElement phoneNumberField;

    //@AndroidFindBy(xpath = "//android.widget.CheckBox")

    @AndroidFindBy(accessibility = "login_terms_checkbox")
    private WebElement checkbox;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, 'Fatafat apna account set')]")
    private WebElement accountSetupMessage;

    @AndroidFindBy(xpath = "//android.widget.EditText")
    private WebElement otpField;

    @AndroidFindBy(accessibility = "English\nFor those who like it simple")
    private WebElement englishOption;

    @AndroidFindBy(accessibility = "login_next_button")
    private WebElement nextButton;

    @AndroidFindBy(accessibility = "profile_next_button")
    private WebElement NextButton;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, \"Let's setup your account quickly.\")]")
    private WebElement accountSetupHeader;

    @AndroidFindBy(className = "android.widget.EditText")
    private WebElement fullNameField;

    @AndroidFindBy(accessibility = "profile_gender_1_button")
    private WebElement femaleOption;

    //methods implementation for loginpage of gajab app

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

    public void clickCheckbox() {
        tap(checkbox);
        LOGGER.info("User clicked checkbox");
    }

    public boolean isAccountSetupHeaderDisplayed() {
        return isDisplayed(accountSetupHeader);
    }

    public void clickNext() {
        tap(nextButton);
        LOGGER.info("User clicked on Next button in signup/login page");
    }

    public void clickNextbtn() throws InterruptedException {
        sleep(1000);
        tap(NextButton);
        LOGGER.info("User clicked on Next button in Account setup page");
    }

    private String generateRandomNumber(int length) {
        Random random = new Random();
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length) - 1;
        return String.valueOf(
                min + random.nextInt(max - min + 1)
        );
    }

    public void selectFemale() throws InterruptedException {
        sleep(1000);
        tap(femaleOption);
        LOGGER.info("User clicked female menu");
    }

    public String enterRandomName() throws InterruptedException {
        String randomName = generateRandomName();
        String name = "autoUsers" + randomName;
        LOGGER.info("randomNumber = {}", randomName);
        LOGGER.info("User entered name: {}", name);
        driver.navigate().back();
        sleep(2000);
        //hideKeyboard();
        enterText(fullNameField, name);
        sleep(2000);
        return name;
    }

    private String generateRandomName() {
        String[] names = {
                "Ganika",
                "Divya",
                "Sureshi",
                "Rameshi",
                "Maheshwari",
                "Karthika",
                "Meena",
                "Januma",
                "Oviya",
                "Trisha",
                "Aruna",
                "Vijaya",
                "Ajaya",
                "Pragaya",
                "Nayanthara",
                "Nathiya",
                "Mamitha",
                "Sherya"
        };

        Random random = new Random();
        return names[random.nextInt(names.length)];
    }

    public void handlePermissionPopupIfPresent() {
        handleOptionalLocationPermission();
    }

    public void enterOtp(String TEST_OTP) throws InterruptedException {
        sleep(2000);
        driver.navigate().back();
        //hideKeyboard();
        sleep(2000);
        enterText(otpField, TEST_OTP);
        sleep(2000);
        LOGGER.info("User entered OTP: {}", TEST_OTP);
    }

    public void clickEnglishOption() {
        tap(englishOption);
        LOGGER.info("User  clicked english option");
    }

    public void clickGetStartedButton() {
        LOGGER.info("SignUp flow Execution started");
        tap(getStartedButton);
        LOGGER.info("User clicked on Get Started button");
    }
    public boolean isGetStartedLabelDisplayed() {
        LOGGER.info("User is able to see get started label displayed");
        return isDisplayed(getStartedButton);
    }
    public boolean isMemberLoginLabelDisplayed() {
        LOGGER.info("User is able to see member login label displayed");
        return isDisplayed(memberLoginLabel);
    }

    public boolean isAccountSetupMessageDisplayed() {
        LOGGER.info("User is able to see account setup message displayed");
        return isDisplayed(accountSetupMessage);
    }

    public boolean isAgeConfirmationLabelDisplayed() {
        LOGGER.info("User  is able to see age confirmation label displayed");
        return isDisplayed(ageConfirmationLabel);

    }

    private String generatePhoneNumber() {
        Random random = new Random();
        long number = 1000000000L + (long) (random.nextDouble() * 9000000000L);
        return String.valueOf(number);
    }

    public void enterRandomPhoneNumber() {
        String phoneNumber = generatePhoneNumber();
        LOGGER.info("User entered phone number: {}", phoneNumber);
        enterText(phoneNumberField, phoneNumber);
    }

    public void enterValidPhoneNUmber(String phoneNumber){
        enterText(phoneNumberField, phoneNumber);
        LOGGER.info("User entered the existing phone number: {}", phoneNumber);
    }


    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessageText() {
        return getText(errorMessage);
    }
}
