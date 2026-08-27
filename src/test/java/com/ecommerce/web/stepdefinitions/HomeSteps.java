package com.ecommerce.web.stepdefinitions;

import com.ecommerce.web.pages.HomePage;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * Step definitions for src/test/resources/features/web/02_home_product_listing.feature.
 */
public class HomeSteps {

    private final HomePage homePage = new HomePage();



    @When("I search for the product {string}")
    public void i_search_for_the_product(String productName) throws InterruptedException {
        homePage.searchForProduct(productName);

    }


    @Then("products should be available in search results")
    public void product_should_be_available_in_the_search_results() throws InterruptedException {
        homePage.checkAvailableProducts();
    }

    @Then("I start bargain the products")
    public void I_start_bargain_the_products() throws InterruptedException {
        homePage.startBargainprocess();
    }




    @Then("I should logout into the application")
    public void iShouldLogoutIntoTheApplication() throws InterruptedException {
        homePage.performLogout();
    }
}
