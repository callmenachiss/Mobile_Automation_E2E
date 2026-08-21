package com.ecommerce.mobile.stepdefinitions;

import com.ecommerce.mobile.pages.HomePage;
import com.ecommerce.mobile.pages.ProductDetailsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * Step definitions for src/test/resources/features/03_product_details.feature.
 * "I have added the product ... to the cart" is reused as a Given step by
 * the Cart, Checkout and Order Confirmation feature files.
 */
public class ProductDetailsSteps {

    private final HomePage homePage = new HomePage();
    private final ProductDetailsPage productDetailsPage = new ProductDetailsPage();

    @Then("the product details page should be displayed")
    public void the_product_details_page_should_be_displayed() {
        Assert.assertTrue(productDetailsPage.areProductDetailsDisplayed(), "Product details page was not displayed.");
    }

    @Then("the product name, price, image and description should be displayed")
    public void the_product_details_should_be_displayed() {
        Assert.assertTrue(productDetailsPage.areProductDetailsDisplayed(), "Product details were not fully displayed.");
    }

    @When("I add the product to the cart")
    public void i_add_the_product_to_the_cart() {
        productDetailsPage.addToCart();
    }

    @Then("a confirmation message should be displayed")
    public void a_confirmation_message_should_be_displayed() {
        Assert.assertTrue(productDetailsPage.isAddToCartConfirmationDisplayed(),
                "Add-to-cart confirmation message was not displayed.");
    }

    @When("I increase the product quantity by {int}")
    public void i_increase_the_product_quantity_by(int times) {
        productDetailsPage.increaseQuantity(times);
    }

    @Then("the selected quantity should be {int}")
    public void the_selected_quantity_should_be(int expectedQuantity) {
        Assert.assertEquals(productDetailsPage.getSelectedQuantity(), expectedQuantity,
                "Selected quantity did not match the expected value.");
    }

    @Given("I have added the product {string} to the cart")
    public void i_have_added_the_product_to_the_cart(String productName) {
        homePage.openProductByName(productName);
        productDetailsPage.addToCart();
    }
}
