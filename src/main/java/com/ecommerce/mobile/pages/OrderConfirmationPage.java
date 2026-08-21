package com.ecommerce.mobile.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

/**
 * Page 6: Order Confirmation screen shown after an order is placed
 * successfully (order number, success message, "Back to Home" button).
 *
 * NOTE ON LOCATORS: placeholders below - update with Appium Inspector.
 */
public class OrderConfirmationPage extends BasePage {

    private static final Logger LOGGER = LogManager.getLogger(OrderConfirmationPage.class);

    @AndroidFindBy(id = "com.example.ecommerceapp:id/tv_order_success_message")
    private WebElement orderSuccessMessage;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/tv_order_number")
    private WebElement orderNumber;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/btn_back_to_home")
    private WebElement backToHomeButton;

    public boolean isOrderConfirmationDisplayed() {
        return isDisplayed(orderSuccessMessage) && isDisplayed(orderNumber);
    }

    public String getOrderNumber() {
        return getText(orderNumber);
    }

    public void backToHome() {
        LOGGER.info("Navigating back to Home page");
        tap(backToHomeButton);
    }
}
