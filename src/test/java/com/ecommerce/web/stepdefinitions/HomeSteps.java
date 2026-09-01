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

    @Then("I click on pay now button")
    public void I_click_on_pay_now_button() throws InterruptedException {
        homePage.clickPayNowButton();
    }

    @Then("I selected BOB net banking for payment flow")
    public void I_selected_BOB_net_banking_for_payment_flow() throws InterruptedException {
        homePage.selectBOBNetBankingMenu();
    }


    @Then("I verify purchased product details")
    public void I_verify_purchased_product_details() throws InterruptedException {
        homePage.verifyPurchaseProductDetails();
        Assert.assertEquals(homePage.Orderplacedlbl.getText(),"Order placed!");
    }

    @Then("I should logout into the application")
    public void iShouldLogoutIntoTheApplication() throws InterruptedException {
        homePage.performLogout();
    }
}
