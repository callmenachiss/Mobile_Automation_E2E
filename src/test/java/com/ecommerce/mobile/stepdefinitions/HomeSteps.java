package com.ecommerce.mobile.stepdefinitions;

import com.ecommerce.mobile.pages.HomePage;
import com.ecommerce.mobile.pages.LoginPage;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;
import static org.testng.Assert.assertTrue;

/**
 * Step definitions for src/test/resources/features/mobile/02_home_product_listing.feature.
 * A couple of these steps ("I open the product", "I open the cart") are
 * reused as Background/Given steps by other feature files too.
 */
public class HomeSteps {

    private final HomePage homePage = new HomePage();
    private final LoginPage loginpage = new LoginPage();

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
            "Toys",
            "Jewellery"
    );

    public String getRandomProductName() {
        Random random = new Random();
        return PRODUCT_NAMES.get(
                random.nextInt(PRODUCT_NAMES.size())
        );
    }

    @Then("a list of products should be displayed")
    public void a_list_of_products_should_be_displayed() {
        assertTrue(homePage.isProductListDisplayed(), "Product list was not displayed on the Home page.");
    }

    @Then("I logout from the Application")
    public void I_logout_from_the_Application() throws InterruptedException {
        //homePage.clickBargainingButton();
        //homePage.dismissBargainingPopup();
        homePage.clickProfileAvatar();
        homePage.clickPersonalDetails();
        homePage.clickLogout();
        assertTrue(loginpage.isGetStartedLabelDisplayed(), "Get Started was not displayed on the splash page.");
    }

    @When("I search the product in mobile")
    public void i_search_for_the_product() {
        String ProductName = getRandomProductName();
        System.out.println("Searching for the product " + ProductName);
        homePage.searchForProduct(ProductName);
    }

    @When("I search the product in mobile {string}")
    public void i_search_for_the_product(String productName) {
        homePage.searchForProduct(productName);
    }

    @When("I search the invalid pin code in mobile")
    public void i_search_invalid_pin_code_in_mobile() throws InterruptedException {
        homePage.searchinvalidPincode("525535");
    }


    @When("I search the valid pin code in mobile")
    public void i_search_valid_pin_code_in_mobile() throws InterruptedException {
        homePage.searchvalidPincode("625535");
    }

    @Then("I verify the results for invalid pin code")
    public void I_verify_the_results_for_invalid_pin_code() throws InterruptedException {
        boolean isErrorDisplayed = isdisplayed(homePage.NoAddressFoundlbl);
        Assert.assertTrue(
                isErrorDisplayed,
                "Expected error message was not displayed: " +
                        "No address found. Please enter a valid location."
        );
    }

    public boolean isdisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Then("I verify the results for valid pin code")
    public void I_verify_the_results_for_valid_pin_code() throws InterruptedException {
        homePage.verifyStatusforvalidpincode();
    }

    @Then("I update Address in profile page")
    public void I_update_Address_in_profile_page() throws InterruptedException {
        homePage.AddAddressintoProfile();
    }

    @Then("I delete Address in profile page")
    public void I_delete_Address_in_profile_page() throws InterruptedException {
        homePage.deleteAddressFromProfile();
    }

    @Then("I click on product to proceed purchase")
    public void I_click_on_product_to_proceed_purchase() throws InterruptedException {
        //homePage.clickFirstProduct();
        //homePage.getFirstProductText();
        //String name = homePage.firstProducttextlbl.getAttribute("content-desc");
        //String name = homePage.productCards.get(0).getAttribute("content-desc");
        //Assert.assertTrue(name.contains(productName), "'" + productName + "' was not visible in the search results.");
        //homePage.clickStartBargaining();
        //homePage.clickOfferYourPrice();
        //homePage.clickAcceptOffer();
        //homePage.clickBuyNow();

        String name = homePage.getFirstProductText();
        homePage.clickFirstProduct();
        homePage.clickStartBargaining();
        homePage.clickOfferYourPrice();
        homePage.clickAcceptOffer();
        homePage.clickBuyNow();
    }

    @Then("I click on product to start bargain to proceed purchase")
    public void I_click_on_product_to_start_bargain_to_proceed_purchase() throws InterruptedException {
        String name = homePage.getFirstProductText();
        homePage.clickFirstProduct();
        homePage.clickStartBargaining();
        homePage.bargainFirstattempt("20");
        homePage.clickOfferYourPrice();
        homePage.clickBargainMorebutton();
        homePage.bargainFirstattempt("95");
        homePage.clickOfferYourPrice();
        homePage.clickAcceptOffer();
        homePage.clickBuyNow();
    }

    @Then("I click on product to start multiple bargains to proceed purchase")
    public void I_click_on_product_to_start_multiple_bargains_to_proceed_purchase() throws InterruptedException {
        String name = homePage.getFirstProductText();
        homePage.clickFirstProduct();
        homePage.clickStartBargaining();
        homePage.bargainFirstattempt("20");
        homePage.clickOfferYourPrice();
        homePage.clickBargainMorebutton();
        homePage.bargainFirstattempt("95");
        homePage.clickOfferYourPrice();
        homePage.clickBargainlastChancebutton();
        homePage.clickOfferYourPrice();
        //homePage.clickAcceptOffer();
        homePage.clickBuyNow();
    }

    @Then("I start bargain to proceed purchase")
    public void I_start_bargain_to_proceed_purchase() throws InterruptedException {
        try{
            homePage.clickStartBargaining();
            homePage.clickOfferYourPrice();
            homePage.clickAcceptOffer();
            homePage.clickBuyNow();
        }catch (Exception e){
            System.out.println(e);
        }
        //homePage.clickStartBargaining();
        //homePage.clickOfferYourPrice();
        //homePage.clickAcceptOffer();
        try {homePage.clickBUYNow();}catch (Exception e){System.out.println(e);}
    }


    @Then("I proceed with payment to complete the order")
    public void iProceedWithPaymentToCompleteTheOrder() throws InterruptedException {
        homePage.clickPay();
    }

    @Then("I proceed with COD payment to complete the order")
    public void iProceedWithCodPaymentToCompleteTheOrder() throws InterruptedException {
        homePage.clickCOD();
    }

    @Then("{string} should be visible in the search results")
    public void product_should_be_visible_in_the_search_results(String productName) {
        assertTrue(homePage.isProductVisible(productName),
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
        assertTrue(homePage.isProductListDisplayed(), "Product list was not displayed after sorting.");
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
