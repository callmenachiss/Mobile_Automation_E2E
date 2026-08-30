package com.ecommerce.mobile.stepdefinitions;

import com.ecommerce.mobile.pages.CheckoutPage;
import com.ecommerce.mobile.pages.HomePage;
import com.ecommerce.mobile.pages.LoginPage;
import com.ecommerce.mobile.pages.PaymentPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Step definitions for src/test/resources/features/mobile/02_home_product_listing.feature.
 * A couple of these steps ("I open the product", "I open the cart") are
 * reused as Background/Given steps by other feature files too.
 */
public class PaymentSteps {

    private final HomePage homePage = new HomePage();
    private final CheckoutPage checkoutPage = new CheckoutPage();
    private final PaymentPage paymentPage = new PaymentPage();
    private final LoginPage loginpage = new LoginPage();


    @Then("I do BOB internet banking for payment to place the order")
    public void I_do_BOB_internet_banking_for_payment_to_place_the_order() throws InterruptedException {
        paymentPage.clickNetBankingMenu();
        paymentPage.clickBOBPaymentMenu();
        paymentPage.verifyPaymentArea();
        paymentPage.clickSuccessBtn();
    }

    @Then("I do PNB internet banking for payment to place the order")
    public void I_do_PNB_internet_banking_for_payment_to_place_the_order() throws InterruptedException {
        paymentPage.clickNetBankingMenu();
        paymentPage.clickPNBPaymentMenu();
        paymentPage.verifyPaymentArea();
        paymentPage.clickSuccessBtn();
    }

    @Then("I do IDBI internet banking for payment to place the order")
    public void I_do_IDBI_internet_banking_for_payment_to_place_the_order() throws InterruptedException {
        paymentPage.clickNetBankingMenu();
        paymentPage.clickIDBIPaymentMenu();
        paymentPage.verifyPaymentArea();
        paymentPage.clickSuccessBtn();
    }




}
