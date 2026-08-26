package com.ecommerce.web.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public boolean isProductListDisplayed() {
        return isDisplayed(productList) && !productNames.isEmpty();
    }

    public void searchForProduct(String productName) {
        enterText(searchBox, productName);
        searchBox.submit();
        LOGGER.info("Searching for product: {}", productName);
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
