package com.ecommerce.mobile.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

/**
 * Page 2: Home screen showing the product catalog (list of products).
 *
 * NOTE ON LOCATORS: placeholders below - update with Appium Inspector.
 */
public class PaymentPage extends BasePage {

    private static final Logger LOGGER = LogManager.getLogger(PaymentPage.class);

    @AndroidFindBy(xpath= "//android.widget.TextView[@text=\"Netbanking\"]")
    private WebElement NetBankingMenu;

    @AndroidFindBy(xpath= "//android.widget.Button[@text=\"BOB BOB\"]")
    private WebElement BOBPaymentMenu;

    @AndroidFindBy(xpath= "//android.widget.Button[@text=\"Canara Bank Canara Bank\"]")
    private WebElement CanaraPaymentMenu;

    @AndroidFindBy(xpath= "//android.widget.Button[@text=\"PNB PNB\"]")
    private WebElement PNBPaymentMenu;

    @AndroidFindBy(xpath= "//android.widget.Button[@text=\"IDBI IDBI\"]")
    private WebElement IDBIPaymentMenu;

    @AndroidFindBy(xpath= "//android.widget.TextView[@text=\"Welcome to Razorpay Software Private Ltd Bank\"]")
    private WebElement Paymentlbl;

    @AndroidFindBy(xpath= "//android.widget.Button[@text=\"Success\"]")
    private WebElement Successbtn;

    @AndroidFindBy(xpath= "//android.widget.Button[@text=\"Failure\"]")
    private WebElement Failurebtn;

    @AndroidFindBy(accessibility= "order_confirmed_close_button")
    private WebElement orderConfirmedXButton;




    public void clickNetBankingMenu() throws InterruptedException {
        sleep(4000);
        tap(NetBankingMenu);
        LOGGER.info("User navigated to the Net-Banking menu");
    }

    public void clickBOBPaymentMenu() throws InterruptedException {
        sleep(4000);
        tap(BOBPaymentMenu);
        LOGGER.info("User clicked on BOB option");
    }

    public void clickCanaraPaymentMenu() throws InterruptedException {
        sleep(3000);
        tap(CanaraPaymentMenu);
        LOGGER.info("User clicked on Canara option");
    }

    public void clickPNBPaymentMenu() throws InterruptedException {
        sleep(3000);
        tap(PNBPaymentMenu);
        LOGGER.info("User clicked on PNB option");
    }

    public void clickIDBIPaymentMenu() throws InterruptedException {
        sleep(3000);
        tap(IDBIPaymentMenu);
        LOGGER.info("User clicked on IDBI option");
    }

    public void verifyPaymentArea() throws InterruptedException {
        sleep(2000);
        isDisplayed(Paymentlbl);
        LOGGER.info("User is accepting net banking session");
    }

    public void clickSuccessBtn() throws InterruptedException {
        sleep(2000);
        tap(Successbtn);
        LOGGER.info("User completed the payment");
        sleep(2000);
        tap(orderConfirmedXButton);
        sleep(2000);
    }












}
