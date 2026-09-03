package com.ecommerce.web.stepdefinitions;

import com.ecommerce.web.pages.HomePage;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Step definitions for src/test/resources/features/web/02_home_product_listing.feature.
 */
public class HomeSteps {

    private final HomePage homePage = new HomePage();

    public static final List<String> PRODUCT_NAMES = Arrays.asList(
            "Shirt",
            "Shoes",
            "Watch",
            "Bag",
            "Mobile",
            "Decor",
            "Cosmetics",
            "Mascara",
            "Brush",
            "Toys"
    );

    public String getRandomProductName() {
        Random random = new Random();
        return PRODUCT_NAMES.get(
                random.nextInt(PRODUCT_NAMES.size())
        );
    }

    public int generateRandomNumber() {
        return new Random().nextInt(5) + 1;
    }

    @When("I search for the product {string}")
    public void i_search_for_the_product(String productName) throws InterruptedException {
        homePage.searchForProduct(productName);
    }

    @When("I search for the product")
    public void i_search_for_the_product() throws InterruptedException {
        String ProductName = getRandomProductName();
        System.out.println("Searching for the product " + ProductName);
        homePage.searchForProduct(ProductName);
    }

    @When("I search the pin code in location menubar {string}")
    public void I_search_the_pin_code_in_location_menubar(String pinCode) throws InterruptedException {
        homePage.setPincode(pinCode);
    }

    @And("I add new address to the profile")
    public void I_add_new_address_to_the_profile() throws InterruptedException {
        homePage.addAddressIntoMenu();
    }


    @Then("products should be available in search results")
    public void product_should_be_available_in_the_search_results() throws InterruptedException {
        //homePage.checkAvailableProducts();
        int productNumber = generateRandomNumber();
        homePage.selectTargetProduct(productNumber);
    }


    @Then("I navigate to Addresses Menu page")
    public void I_navigate_to_Addresses_Menu_page() throws InterruptedException {
        homePage.NavigatetoAddressesMenu();
    }

    @Then("I verify address details for this order")
    public void I_verify_address_details_for_this_order() throws InterruptedException {
        homePage.verifyAddressDetails();
        homePage.NavigatetoAddressesMenu();
    }

    @Then("I delete the Address in profile page")
    public void I_delete_the_Address_in_profile_page() throws InterruptedException {
        homePage.deleteAddressDetails();
    }

    @Then("I comback to homepage and again verify most bargained products menu")
    public void I_comback_to_homepage_and_again_verify_most_bargained_products_menu() throws InterruptedException {
       homePage.NavigateToHomePageMenu();
       homePage.SelectMostBargainedProducts();
    }


    @When("I navigate to most bargained products page")
    public void I_navigate_to_most_bargained_products_page() throws InterruptedException {
        homePage.NavigateToMostBargainedProductsMenu();
    }

    @Then("I validate the product link details to share info through email")
    public void I_validate_the_product_link_details_to_share_info_through_email() throws InterruptedException {
        homePage.verifyProductDetails();
    }

    @Then("Target products should be available in search results")
    public void Target_product_should_be_available_in_the_search_results() throws InterruptedException {
        int productNumber = generateRandomNumber();
        homePage.selectTargetProduct(productNumber);
    }

    @Then("I start bargain the products")
    public void I_start_bargain_the_products() throws InterruptedException {
        homePage.startBargainprocess();
    }

    @Then("I start first bargain the products")
    public void I_start_first_bargain_the_products() throws InterruptedException {
        homePage.startFirstBargainprocess();
    }

    @Then("I click on pay now button")
    public void I_click_on_pay_now_button() throws InterruptedException {
        homePage.clickPayNowButton();
    }

    @Then("I selected BOB net banking for payment flow")
    public void I_selected_BOB_net_banking_for_payment_flow() throws InterruptedException {
        homePage.selectBOBNetBankingMenu();
    }

    @Then("I selected Canara net banking for payment flow")
    public void I_selected_Canara_net_banking_for_payment_flow() throws InterruptedException {
        homePage.selectCanaraNetBankingMenu();
    }

    @Then("I selected IDBI net banking for payment flow")
    public void I_selected_IDBI_net_banking_for_payment_flow() throws InterruptedException {
        homePage.selectIDBINetBankingMenu();
    }

    @Then("I selected PNB net banking for payment flow")
    public void I_selected_PNB_net_banking_for_payment_flow() throws InterruptedException {
        homePage.selectPNBNetBankingMenu();
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
