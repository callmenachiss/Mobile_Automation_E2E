package com.ecommerce.web.pages;

import com.ecommerce.mobile.config.ConfigReader;
import com.ecommerce.web.config.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Parent class for every web Page Object. Mirrors the mobile BasePage's
 * small, plain-English vocabulary (click, enterText, isDisplayed...) so
 * step definitions and page classes stay easy to read, whether the page
 * lives under mobile.pages or web.pages.
 */
public class BaseWebPage {

    private static final Logger LOGGER = LogManager.getLogger(BaseWebPage.class);

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BaseWebPage() {
        this.driver = WebDriverManager.getDriver();
        int waitSeconds = ConfigReader.getInt("explicit.wait.seconds");
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
        PageFactory.initElements(driver, this);
    }


    protected void executeJavaScript(String script, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script, element);
    }

    protected void waitUntilVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void click(WebElement element) {
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
     * Scrolls the element into view. Handy for long product lists, the web
     * equivalent of the mobile BasePage.scrollToText().
     */
    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }
}
