package com.ecommerce.web.pages;

import com.ecommerce.web.config.ConfigReader;
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
import java.util.Random;
import java.util.Set;

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
    private String parentWindow;
    public static final String[] MALE_NAMES = {
            "Arun",
            "Vijay",
            "Rahul",
            "Karthik",
            "Suresh",
            "Pradeep",
            "Rohit",
            "Aravind",
            "Manoj",
            "Dinesh",
            "Sanjay",
            "Naveen",
            "Ajay",
            "Vignesh",
            "Surya",
            "Ashwin",
            "Gokul",
            "Harish",
            "Mohan",
            "Ramesh"
    };


    public void switchToChildWindow() {
        parentWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }

    public void switchToParentWindow() {
        driver.switchTo().window(parentWindow);
    }


    public void goSleep(long num) throws InterruptedException {
        Thread.sleep(num);
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

    protected void waitForElementDuration(WebElement element, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void clickDuration(WebElement element, int times) {
        WebDriverWait customWait =
                new WebDriverWait(driver, Duration.ofSeconds(times));
        customWait.until(ExpectedConditions.visibilityOf(element));
        scrollIntoView(element);
        customWait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public String generateMobileNumber() {
        Random random = new Random();
        int firstDigit = 6 + random.nextInt(4); // 6, 7, 8, or 9
        StringBuilder mobileNumber = new StringBuilder();
        mobileNumber.append(firstDigit);
        for (int i = 1; i < 10; i++) {
            mobileNumber.append(random.nextInt(10));
        }
        return mobileNumber.toString();
    }

    /**
     * Scrolls the element into view. Handy for long product lists, the web
     * equivalent of the mobile BasePage.scrollToText().
     */
    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }
}
