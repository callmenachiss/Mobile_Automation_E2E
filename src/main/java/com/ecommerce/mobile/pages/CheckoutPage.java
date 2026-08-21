package com.ecommerce.mobile.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

/**
 * Page 5: Checkout screen (shipping address, payment method, place order).
 *
 * NOTE ON LOCATORS: placeholders below - update with Appium Inspector.
 */
public class CheckoutPage extends BasePage {

    private static final Logger LOGGER = LogManager.getLogger(CheckoutPage.class);

    @AndroidFindBy(id = "com.example.ecommerceapp:id/et_shipping_address")
    private WebElement shippingAddressInput;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/btn_save_address")
    private WebElement saveAddressButton;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/tv_address_saved_confirmation")
    private WebElement addressSavedConfirmation;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/rb_payment_cash_on_delivery")
    private WebElement cashOnDeliveryOption;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/btn_place_order")
    private WebElement placeOrderButton;

    public void enterShippingAddress(String address) {
        LOGGER.info("Entering shipping address");
        enterText(shippingAddressInput, address);
        hideKeyboard();
        tap(saveAddressButton);
    }

    public boolean isAddressSavedConfirmationDisplayed() {
        return isDisplayed(addressSavedConfirmation);
    }

    public void selectCashOnDeliveryPayment() {
        tap(cashOnDeliveryOption);
    }

    public void placeOrder() {
        LOGGER.info("Placing order");
        tap(placeOrderButton);
    }
}
