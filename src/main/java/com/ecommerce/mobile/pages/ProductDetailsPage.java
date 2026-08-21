package com.ecommerce.mobile.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

/**
 * Page 3: Product Details screen (name, price, description, image,
 * quantity selector, Add to Cart button).
 *
 * NOTE ON LOCATORS: placeholders below - update with Appium Inspector.
 */
public class ProductDetailsPage extends BasePage {

    private static final Logger LOGGER = LogManager.getLogger(ProductDetailsPage.class);

    @AndroidFindBy(id = "com.example.ecommerceapp:id/tv_product_name")
    private WebElement productName;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/tv_product_price")
    private WebElement productPrice;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/tv_product_description")
    private WebElement productDescription;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/iv_product_image")
    private WebElement productImage;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/btn_increase_quantity")
    private WebElement increaseQuantityButton;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/btn_decrease_quantity")
    private WebElement decreaseQuantityButton;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/tv_quantity")
    private WebElement quantityLabel;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/btn_add_to_cart")
    private WebElement addToCartButton;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/tv_add_to_cart_confirmation")
    private WebElement addToCartConfirmation;

    public boolean areProductDetailsDisplayed() {
        return isDisplayed(productName) && isDisplayed(productPrice)
                && isDisplayed(productDescription) && isDisplayed(productImage);
    }

    public String getProductName() {
        return getText(productName);
    }

    public String getProductPrice() {
        return getText(productPrice);
    }

    public void increaseQuantity(int times) {
        for (int i = 0; i < times; i++) {
            tap(increaseQuantityButton);
        }
    }

    public void decreaseQuantity(int times) {
        for (int i = 0; i < times; i++) {
            tap(decreaseQuantityButton);
        }
    }

    public int getSelectedQuantity() {
        return Integer.parseInt(getText(quantityLabel).trim());
    }

    public void addToCart() {
        LOGGER.info("Adding product to cart: {}", getProductName());
        tap(addToCartButton);
    }

    public boolean isAddToCartConfirmationDisplayed() {
        return isDisplayed(addToCartConfirmation);
    }
}
