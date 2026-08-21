package com.ecommerce.mobile.stepdefinitions;

import com.ecommerce.mobile.pages.CartPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * Step definitions for src/test/resources/features/04_cart.feature.
 */
public class CartSteps {

    private final CartPage cartPage = new CartPage();
    private int quantityBeforeUpdate;

    @Then("{string} should be visible in the cart")
    public void product_should_be_visible_in_the_cart(String productName) {
        Assert.assertTrue(cartPage.isProductInCart(productName),
                "'" + productName + "' was not visible in the cart.");
    }

    @When("I increase the quantity of the first item in the cart")
    public void i_increase_the_quantity_of_the_first_item_in_the_cart() {
        quantityBeforeUpdate = cartPage.getFirstItemQuantity();
        cartPage.increaseFirstItemQuantity();
    }

    @Then("the quantity of the first item should increase by 1")
    public void the_quantity_of_the_first_item_should_increase_by_1() {
        int quantityAfterUpdate = cartPage.getFirstItemQuantity();
        Assert.assertEquals(quantityAfterUpdate, quantityBeforeUpdate + 1,
                "Cart item quantity did not increase by 1.");
    }

    @When("I remove the first item from the cart")
    public void i_remove_the_first_item_from_the_cart() {
        cartPage.removeFirstItem();
    }

    @Then("the cart should be empty")
    public void the_cart_should_be_empty() {
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart was not empty after removing the item.");
    }

    @When("I try to proceed to checkout with an empty cart")
    public void i_try_to_proceed_to_checkout_with_an_empty_cart() {
        cartPage.proceedToCheckout();
    }

    @Then("a message should tell me the cart is empty")
    public void a_message_should_tell_me_the_cart_is_empty() {
        Assert.assertTrue(cartPage.isCheckoutBlockedMessageDisplayed(),
                "Empty-cart message was not displayed when trying to checkout.");
    }

    @When("I proceed to checkout")
    public void i_proceed_to_checkout() {
        cartPage.proceedToCheckout();
    }
}
