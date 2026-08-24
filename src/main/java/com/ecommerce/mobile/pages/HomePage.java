package com.ecommerce.mobile.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page 2: Home screen showing the product catalog (list of products).
 *
 * NOTE ON LOCATORS: placeholders below - update with Appium Inspector.
 */
public class HomePage extends BasePage {

    private static final Logger LOGGER = LogManager.getLogger(HomePage.class);


    @AndroidFindBy(id = "com.sec.android.app.popupcalculator:id/calc_keypad_btn_08")
    private WebElement NumberBox;

    @AndroidFindBy(id = "com.sec.android.app.popupcalculator:id/calc_keypad_btn_add")
    private WebElement PlusBox;

    @AndroidFindBy(id = "com.sec.android.app.popupcalculator:id/calc_keypad_btn_equal")
    private WebElement EqualBox;

    @AndroidFindBy(id = "com.sec.android.app.popupcalculator:id/calc_edt_formula")
    private WebElement AnswerBox;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/et_search")
    private WebElement searchBox;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/rv_product_list")
    private WebElement productList;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/tv_product_name")
    private List<WebElement> productNames;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/btn_sort_by_price")
    private WebElement sortByPriceButton;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/iv_cart_icon")
    private WebElement cartIcon;

    public boolean isProductListDisplayed() {
        return isDisplayed(productList) && !productNames.isEmpty();
    }

    public void searchForProduct(String productName) {
        LOGGER.info("Searching for product: {}", productName);
        enterText(searchBox, productName);
        hideKeyboard();
    }

    public void sortProductsByPrice() {
        tap(sortByPriceButton);
    }

    public void openProductByName(String productName) {
        LOGGER.info("Opening product details for: {}", productName);
        for (WebElement product : productNames) {
            if (getText(product).equalsIgnoreCase(productName)) {
                tap(product);
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
        tap(cartIcon);
    }
}
