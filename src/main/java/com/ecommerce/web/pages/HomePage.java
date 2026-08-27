package com.ecommerce.web.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

/**
 * Home page showing the product catalog (the web counterpart of the
 * mobile HomePage, same app/flow).
 *
 * NOTE ON LOCATORS: every @FindBy value below is a placeholder - update
 * with the real id/css selector from the actual site.
 */
public class HomePage extends BaseWebPage {

    private static final Logger LOGGER = LogManager.getLogger(HomePage.class);

    @FindBy(id = "et_search")
    private WebElement searchBox;

    @FindBy(id = "rv_product_list")
    private WebElement productList;

    @FindBy(className = "tv_product_name")
    private List<WebElement> productNames;

    @FindBy(id = "btn_sort_by_price")
    private WebElement sortByPriceButton;

    @FindBy(id = "iv_cart_icon")
    private WebElement cartIcon;

    @FindBy(xpath = "(//img[@alt='profile'])[1]")
    private WebElement profileIconmenu;

    @FindBy(xpath = "//h3[normalize-space(text())='Personal details']")
    private WebElement PersonalDetailsMenu;

    @FindBy(xpath = "//button[normalize-space(text())='Logout']")
    private WebElement LogoutButton;

    @FindBy(xpath = "//p[normalize-space(text())='Logout successfully']")
    public WebElement LogoutSuccesslbl;


    public void searchForProduct(String productName) {
        enterText(searchBox, productName);
        searchBox.submit();
        LOGGER.info("Searching for product: {}", productName);
    }


    protected void waitForOverlayToDisappear(int timeoutSeconds) {

        WebDriverWait customWait =
                new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        try {
            customWait.until(
                    ExpectedConditions.invisibilityOfElementLocated(
                            By.cssSelector("div.fixed.inset-0")
                    )
            );
        } catch (TimeoutException e) {
            LOGGER.warn("Overlay is still present after {} seconds", timeoutSeconds);
        }
    }
    public void performLogout() throws InterruptedException {
        goSleep(3000);
        LOGGER.info("User is performing logout flow");
        waitForOverlayToDisappear(20);
        clickDuration(profileIconmenu,10);
        goSleep(1000);
        LOGGER.info("User is clicked on profile menu");
        goSleep(1000);
        click(PersonalDetailsMenu);
        LOGGER.info("User is clicked on personal details menu");
        goSleep(1000);
        click(LogoutButton);
        LOGGER.info("User logout successfully");
        isDisplayed(LogoutSuccesslbl);
    }

    public void sortProductsByPrice() {
        click(sortByPriceButton);
    }

    public void openProductByName(String productName) {
        LOGGER.info("Opening product details for: {}", productName);
        for (WebElement product : productNames) {
            if (getText(product).equalsIgnoreCase(productName)) {
                click(product);
                return;
            }
        }
        throw new RuntimeException("Product '" + productName + "' was not found on the Home page.");
    }

    public int getDisplayedProductCount() {
        return productNames.size();
    }

    public boolean isProductVisible(String productName) {
        return productNames.stream().anyMatch(product -> getText(product).equalsIgnoreCase(productName));
    }

    public void openCart() {
        click(cartIcon);
    }
}
