package com.ecommerce.mobile.pages;

import com.ecommerce.mobile.config.ConfigReader;
import com.ecommerce.mobile.config.DriverManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Parent class for every Page Object. It gives each page a small, plain
 * English "vocabulary" (tap, enterText, isDisplayed...) so step definitions
 * and page classes stay easy to read for anyone, technical or not.
 */
public class BasePage {

    private static final Logger LOGGER = LogManager.getLogger(BasePage.class);

    protected final AndroidDriver driver;
    protected final WebDriverWait wait;

    protected BasePage() {
        this.driver = DriverManager.getDriver();
        int waitSeconds = ConfigReader.getInt("explicit.wait.seconds");
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(waitSeconds)), this);
    }

    protected void waitUntilVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void tap(WebElement element) {
        waitUntilVisible(element);
        element.click();
    }

    protected void enterText(WebElement element, String text) {
        waitUntilVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        waitUntilVisible(element);
        return element.getText();
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            LOGGER.debug("Element not displayed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Scrolls the current screen until an element containing the given text
     * becomes visible. Handy for long product lists.
     */
    protected void scrollToText(String text) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))"
                        + ".scrollIntoView(new UiSelector().textContains(\"" + text + "\"));"));
    }

    protected void hideKeyboard() {
        try {
            if (driver.isKeyboardShown()) {
                driver.hideKeyboard();
            }
        } catch (Exception e) {
            LOGGER.debug("No keyboard to hide: {}", e.getMessage());
        }
    }
}
