package com.ecommerce.mobile.stepdefinitions;

import com.ecommerce.mobile.pages.CheckoutPage;
import com.ecommerce.mobile.pages.HomePage;
import com.ecommerce.mobile.pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.testng.Assert.assertTrue;

/**
 * Step definitions for src/test/resources/features/mobile/02_home_product_listing.feature.
 * A couple of these steps ("I open the product", "I open the cart") are
 * reused as Background/Given steps by other feature files too.
 */
public class CheckoutSteps {

    private final HomePage homePage = new HomePage();
    private final CheckoutPage checkoutPage = new CheckoutPage();
    private final LoginPage loginpage = new LoginPage();

    @When("I click on Category menu")
    public void I_click_on_Category_menu() throws InterruptedException {
        checkoutPage.clickCategoryMenu();
    }

    @Then("I click on Bargain menu")
    public void I_click_on_Bargain_menu() throws InterruptedException {
        checkoutPage.clickBargainsMenu();
    }

    @Then("I comeback to Bazaar page to verify session based Gajab deal")
    public void I_comeback_to_Bazaar_page_to_verify_session_based_Gajab_deal() throws InterruptedException {
        checkoutPage.clickBazaarMenu();
        checkoutPage.verifyGajabdealforSession();
        checkoutPage.verifyJustBargaindealforSession();
    }

    @Then("I click on the product from the list")
    public void I_click_on_the_product_from_the_list() throws InterruptedException {
        String name = homePage.getFirstProductText();
        homePage.clickFirstProduct();
    }


    @Then("I share the product details through email")
    public void I_share_the_product_details_through_email() throws InterruptedException {
        checkoutPage.clickShareMenu();
        checkoutPage.clickGmailMenu();
        checkoutPage.sentEmailCustomer();
    }

    @When("I navigate to trending page of the application")
    public void I_navigate_to_trending_page_of_the_application() throws InterruptedException {
        checkoutPage.clickTrendingmenu();
    }

    @When("I navigate to Home and Kitchen Menu")
    public void I_navigate_to_Home_and_Kitchen_Menu() throws InterruptedException {
        checkoutPage.clickHomeandKitchenMenu();
        checkoutPage.clickSubHomeandKitchenMenu();
    }


    @When("I select and click specific product in homepage")
    public void I_select_and_click_specific_product_in_homepage() throws InterruptedException {
       checkoutPage.clickSpecificProducts();
    }

    @Then("I navigate to Gifting and Festive Essentials Menu")
    public void I_navigate_to_Gifting_and_Festive_Essentials_Menu() throws InterruptedException {
        checkoutPage.clickGiftingFestiveEssentialsMenu();
        //checkoutPage.clickSubGiftingFestiveEssentialsMenu();
    }

    @Then("I navigate to Stationery Menu")
    public void I_navigate_to_Stationerys_Menu() throws InterruptedException {
        checkoutPage.clickStationeryMenu();
    }

    @Then("I navigate to Toys and Games Menu")
    public void I_navigate_to_Toys_and_Games_Menu() throws InterruptedException {
        checkoutPage.clickToysandGamesMenu();
        checkoutPage.clickSubToysandGamesMenu();
    }

    @Then("I verify products based on this category")
    public void I_verify_products_based_on_this_category() throws InterruptedException {
        checkoutPage.setFiltersAndverifyproductsBasedOnCategory();
    }

    @Then("I verify products based on price range filter")
    public void I_verify_products_based_on_price_range_filter() throws InterruptedException {
        checkoutPage.setPriceRangeFilters();
    }


    @Then("I verify products based on brand Sera's basket filter")
    public void I_verify_products_based_on_brand_Sera_basket_filter() throws InterruptedException {
        checkoutPage.setSeraBrandFilters();
    }


    @Then("I verify products using brand Sera's basket filter")
    public void I_verify_products_using_brand_Sera_basket_filter() throws InterruptedException {
        checkoutPage.ApplySeraBrandFilter();
    }

    @Then("I setup filters in this product list page")
    public void I_setup_filters_in_this_product_list_page() throws InterruptedException {
        checkoutPage.validateFiltersMenu();
    }

    @And("I will apply sorting items in product list page")
    public void I_will_apply_sorting_items_in_product_list_page() throws InterruptedException {
        checkoutPage.validateLowToHighMenu();
        checkoutPage.validateHighToLowMenu();
        checkoutPage.validateNewArrivalsMenu();
        checkoutPage.validateRatingsMenu();
    }

    @Then("I verify anyone trending products in the list")
    public void I_verify_anyone_trending_products_in_the_list() throws InterruptedException {
        checkoutPage.validateAnyoneTrendingProduct();
    }

    @And("I verify one more products in trending section")
    public void I_verify_one_more_products_in_trending_section() throws InterruptedException {
        checkoutPage.validateAnotherTrendingProduct();
    }

    @And("I verify the placed orders in the live order section")
    public void I_verify_the_placed_orders_in_the_live_order_section() throws InterruptedException {
        checkoutPage.navigateLiveOrdersSection();
        checkoutPage.verifyLiveOrdersSection();
    }





}
