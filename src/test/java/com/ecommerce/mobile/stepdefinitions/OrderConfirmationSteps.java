package com.ecommerce.mobile.stepdefinitions;

import com.ecommerce.mobile.pages.OrderConfirmationPage;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

/**
 * Step definitions for src/test/resources/features/06_order_confirmation.feature.
 */
public class OrderConfirmationSteps {

    private static final Logger LOGGER = LogManager.getLogger(OrderConfirmationSteps.class);

    private final OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage();

    @Then("the order confirmation page should display a success message and an order number")
    public void the_order_confirmation_page_should_display_a_success_message_and_an_order_number() {
        Assert.assertTrue(orderConfirmationPage.isOrderConfirmationDisplayed(),
                "Order confirmation message/order number was not displayed.");
        LOGGER.info("Order placed successfully. Order number: {}", orderConfirmationPage.getOrderNumber());
    }
}
