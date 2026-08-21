package com.ecommerce.mobile.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page 4: Cart screen (items added, quantities, subtotal, checkout button).
 *
 * NOTE ON LOCATORS: placeholders below - update with Appium Inspector.
 */
public class CartPage extends BasePage {

    private static final Logger LOGGER = LogManager.getLogger(CartPage.class);

    @AndroidFindBy(id = "com.example.ecommerceapp:id/rv_cart_items")
    private List<WebElement> cartItems;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/tv_cart_item_name")
    private List<WebElement> cartItemNames;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/tv_cart_item_quantity")
    private WebElement firstItemQuantity;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/btn_increase_quantity")
    private WebElement increaseQuantityButton;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/btn_decrease_quantity")
    private WebElement decreaseQuantityButton;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/btn_remove_item")
    private WebElement removeItemButton;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/tv_cart_empty_message")
    private WebElement emptyCartMessage;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/btn_proceed_to_checkout")
    private WebElement proceedToCheckoutButton;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/tv_checkout_blocked_message")
    private WebElement checkoutBlockedMessage;

    public boolean isProductInCart(String productName) {
        return cartItemNames.stream().anyMatch(item -> getText(item).equalsIgnoreCase(productName));
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public int getFirstItemQuantity() {
        return Integer.parseInt(getText(firstItemQuantity).trim());
    }

    public void increaseFirstItemQuantity() {
        tap(increaseQuantityButton);
    }

    public void decreaseFirstItemQuantity() {
        tap(decreaseQuantityButton);
    }

    public void removeFirstItem() {
        LOGGER.info("Removing first item from cart");
        tap(removeItemButton);
    }

    public boolean isCartEmpty() {
        return isDisplayed(emptyCartMessage);
    }

    public void proceedToCheckout() {
        tap(proceedToCheckoutButton);
    }

    public boolean isCheckoutBlockedMessageDisplayed() {
        return isDisplayed(checkoutBlockedMessage);
    }
}
