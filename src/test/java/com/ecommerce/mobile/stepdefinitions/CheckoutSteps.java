package com.ecommerce.mobile.stepdefinitions;

import com.ecommerce.mobile.pages.CheckoutPage;
import com.ecommerce.mobile.pages.OrderConfirmationPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * Step definitions for src/test/resources/features/05_checkout.feature.
 */
public class CheckoutSteps {

    private static final String TEST_SHIPPING_ADDRESS =
            "221B Baker Street, Flat 2, London, NW1 6XE";

    private final CheckoutPage checkoutPage = new CheckoutPage();
    private final OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage();

    @When("I enter a shipping address")
    public void i_enter_a_shipping_address() {
        checkoutPage.enterShippingAddress(TEST_SHIPPING_ADDRESS);
    }

    @Then("the shipping address should be saved")
    public void the_shipping_address_should_be_saved() {
        Assert.assertTrue(checkoutPage.isAddressSavedConfirmationDisplayed(), "Shipping address save confirmation was not displayed.");
    }

    @When("I select Cash on Delivery as the payment method")
    public void i_select_cash_on_delivery_as_the_payment_method() {
        checkoutPage.selectCashOnDeliveryPayment();
    }

    @When("I place the order")
    public void i_place_the_order() {
        checkoutPage.placeOrder();
    }

    @Then("the order confirmation page should be displayed")
    public void the_order_confirmation_page_should_be_displayed() {
        Assert.assertTrue(orderConfirmationPage.isOrderConfirmationDisplayed(), "Order confirmation page was not displayed.");
    }
}
