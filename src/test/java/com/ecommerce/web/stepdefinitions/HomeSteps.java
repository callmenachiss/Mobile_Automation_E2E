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
    public void i_search_for_the_product(String productName) {
        homePage.searchForProduct(productName);
    }

    @Then("{string} should be visible in the search results")
    public void product_should_be_visible_in_the_search_results(String productName) {
        Assert.assertTrue(homePage.isProductVisible(productName),
                "'" + productName + "' was not visible in the search results.");
    }

    @When("I open the product {string}")
    public void i_open_the_product(String productName) {
        homePage.openProductByName(productName);
    }


    @Then("I should logout into the application")
    public void iShouldLogoutIntoTheApplication() throws InterruptedException {
        homePage.performLogout();
    }
}
