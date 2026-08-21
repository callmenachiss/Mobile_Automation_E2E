package com.ecommerce.mobile.stepdefinitions;

import com.ecommerce.mobile.pages.HomePage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * Step definitions for src/test/resources/features/02_home_product_listing.feature.
 * A couple of these steps ("I open the product", "I open the cart") are
 * reused as Background/Given steps by other feature files too.
 */
public class HomeSteps {

    private final HomePage homePage = new HomePage();

    @Then("a list of products should be displayed")
    public void a_list_of_products_should_be_displayed() {
        Assert.assertTrue(homePage.isProductListDisplayed(), "Product list was not displayed on the Home page.");
    }

    @When("I search for the product {string}")
    public void i_search_for_the_product(String productName) {
        homePage.searchForProduct(productName);
    }

    @Then("{string} should be visible in the search results")
    public void product_should_be_visible_in_the_search_results(String productName) {
        Assert.assertTrue(homePage.isProductVisible(productName),
                "'" + productName + "' was not visible in the search results.");
    }

    @When("I sort the products by price")
    public void i_sort_the_products_by_price() {
        homePage.sortProductsByPrice();
    }

    @Then("the product list should still be displayed")
    public void the_product_list_should_still_be_displayed() {
        // Basic check that sorting did not break the page. Once real
        // locators are wired up, this can be extended to read each
        // product's price and assert they are in ascending order.
        Assert.assertTrue(homePage.isProductListDisplayed(), "Product list was not displayed after sorting.");
    }

    @When("I open the product {string}")
    public void i_open_the_product(String productName) {
        homePage.openProductByName(productName);
    }

    @When("I open the cart")
    public void i_open_the_cart() {
        homePage.openCart();
    }
}
