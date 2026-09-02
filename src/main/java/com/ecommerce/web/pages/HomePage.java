package com.ecommerce.web.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.time.Duration;
import java.util.List;

/**
 * Home page showing the product catalog (the web counterpart of the
 * mobile HomePage, same app/flow).
 *
 * NOTE ON LOCATORS: every @FindBy value below is a placeholder - update
 * with the real id/css selector from the actual site.
 */
public class HomePage extends BaseWebPage {

    private static final Logger LOGGER = LogManager.getLogger(HomePage.class);

    @FindBy(xpath = "(//div[contains(@class,'flex items-center')]//input)[1]")
    private WebElement searchBox;

    @FindBy(xpath = "//div[@id='header-middle-bar']//img[@id='header-search-btn']")
    private WebElement searchbtnicon;

    @FindBy(xpath = "(//img[contains(@class,'object-contain w-full')])[1]")
    private WebElement searchproductName;

    @FindBy(xpath = "//img[contains(@class,'object-contain w-full')]")
    private List<WebElement> searchProductNameResults;

    @FindBy(xpath = "//span[normalize-space()='Start Bargaining']")
    private WebElement StartBargainbtn;

    @FindBy(xpath = "//input[@id='bargain-offer-price']")
    private WebElement BargainTxtBox;

    @FindBy(xpath = "//span[normalize-space(text())='Offer Your Price']")
    private WebElement offerPricebtn;

    @FindBy(xpath = "//span[normalize-space(text())='Accept the offer']")
    private WebElement AcceptOfferbtn;

    @FindBy(xpath = "//span[@id='bargain-buy-now-text-span']")
    private WebElement BuyNowbtn;

    @FindBy(xpath = "//span[@id='checkout-pay-now-desktop-online-text']")
    private WebElement PayNowbtn;

    @FindBy(xpath = "//span[contains(text(),\"Netbanking\")]")
    private WebElement NetBankingmenu;

    @FindBy(css = "iframe.razorpay-checkout-frame")
    private WebElement razorpayFrame;

    @FindBy(xpath = "//span[contains(text(),\"Bank of Baroda\")]")
    private WebElement BOBNetBankingmenu;

    @FindBy(xpath = "//span[contains(text(),\"Canara Bank\")]")
    private WebElement CanaraNetBankingmenu;

    @FindBy(xpath = "//span[contains(text(),\"Punjab National Bank - Retail Banking\")]")
    private WebElement PNBNetBankingmenu;

    @FindBy(xpath = "//span[contains(text(),\"IDBI\")]")
    private WebElement IDBINetBankingmenu;

    @FindBy(xpath = "//button[@class='success']")
    private WebElement Acceptbtn;

    @FindBy(xpath = "//*[contains(text(),'Order placed!')]")
    public WebElement Orderplacedlbl;

    @FindBy(xpath = "(//img[@alt='profile'])[1]")
    private WebElement profileIconmenu;

    @FindBy(xpath = "//button[normalize-space()='Add New Address']")
    private WebElement AddAddressbtn;

    @FindBy(xpath = "//input[@id='add-address-modal-address1-input']")
    private WebElement Address1Txtbox;

    @FindBy(xpath = "//input[@id='add-address-modal-address2-input']")
    private WebElement Address2Txtbox;

    @FindBy(xpath = "//h3[normalize-space(text())='Personal details']")
    private WebElement PersonalDetailsMenu;

    @FindBy(xpath = "//h3[normalize-space(text())='Addresses']")
    private WebElement AddressesMenu;

    @FindBy(xpath = "//button[@id='location-desktop-menu-btn']")
    private WebElement LocationMenu;

    @FindBy(xpath = "//input[@id='location-desktop-search-input']")
    private WebElement LocationTxtbox;

    @FindBy(xpath = "//div[@id='location-desktop-suggestion-item-0']")
    private WebElement LocationTargetTxtbox;

    @FindBy(xpath = "//button[normalize-space(text())='Logout']")
    private WebElement LogoutButton;

    @FindBy(xpath = "//p[normalize-space(text())='Logout successfully']")
    public WebElement LogoutSuccesslbl;


    public void searchForProduct(String productName) throws InterruptedException {
        goSleep(7000);
        enterText(searchBox, productName);
        goSleep(5000);
        //Actions actions = new Actions(driver);
        //actions.sendKeys(Keys.ENTER);
        //goSleep(5000);
        clickDuration(searchbtnicon,10);
        LOGGER.info("Searching for product: {}", productName);
    }

    public void checkAvailableProducts() throws InterruptedException {
        goSleep(4000);
        clickDuration(searchproductName,10);
        LOGGER.info("User selecting the products from search results");
    }

    public void selectTargetProduct(int option) throws InterruptedException {
        goSleep(4000);
        searchProductNameResults.get(option).click();
    }

    public void startBargainprocess() throws InterruptedException {
        goSleep(4000);
        click(StartBargainbtn);
        LOGGER.info("User started the bargain process");
        goSleep(2000);
        click(offerPricebtn);
        goSleep(2000);
        click(AcceptOfferbtn);
        LOGGER.info("User accepting offer");
        goSleep(5000);
        click(BuyNowbtn);
        LOGGER.info("User clicked on buy now button");
    }

    public void startFirstBargainprocess() throws InterruptedException {
        goSleep(4000);
        click(StartBargainbtn);
        LOGGER.info("User started the bargain process");
        goSleep(3000);
        enterText(BargainTxtBox,"30");
        click(offerPricebtn);
        goSleep(2000);
        click(AcceptOfferbtn);
        LOGGER.info("User accepting offer");
        goSleep(5000);
        click(BuyNowbtn);
        LOGGER.info("User clicked on buy now button");
    }

    public void clickPayNowButton() throws InterruptedException{
        goSleep(3000);
        click(PayNowbtn);
        LOGGER.info("User clicked on pay now button");
        goSleep(3000);
    }

    public void selectBOBNetBankingMenu() throws InterruptedException {
        goSleep(3000);
        switchToRazorpayFrame(razorpayFrame);
        //switchToChildWindow();
        click(NetBankingmenu);
        LOGGER.info("User clicked on net banking menu");
        goSleep(2000);
        click(BOBNetBankingmenu);
        LOGGER.info("User selecting BOB net banking menu");
        goSleep(2000);
        click(Acceptbtn);
        goSleep(2000);
        switchToDefaultContent();
        //switchToParentWindow();
        LOGGER.info("User completed the payment");
    }

    public void selectCanaraNetBankingMenu() throws InterruptedException {
        goSleep(3000);
        switchToRazorpayFrame(razorpayFrame);
        //switchToChildWindow();
        click(NetBankingmenu);
        LOGGER.info("User clicked on the net banking menu");
        goSleep(2000);
        click(CanaraNetBankingmenu);
        LOGGER.info("User selecting Canara net banking menu");
        goSleep(2000);
        switchToChildWindow();
        goSleep(2000);
        click(Acceptbtn);
        goSleep(7000);
        //switchToDefaultContent();
        LOGGER.info("User completed the Payment");
        goSleep(3000);
        switchToParentWindow();
        goSleep(7000);
    }

    public void selectIDBINetBankingMenu() throws InterruptedException {
        goSleep(3000);
        switchToRazorpayFrame(razorpayFrame);
        //switchToChildWindow();
        click(NetBankingmenu);
        LOGGER.info("User clicked on Net banking menu");
        goSleep(2000);
        click(IDBINetBankingmenu);
        LOGGER.info("User selecting IDBI net banking menu");
        goSleep(2000);
        switchToChildWindow();
        goSleep(2000);
        click(Acceptbtn);
        goSleep(7000);
        //switchToDefaultContent();
        LOGGER.info("User Completed the payment");
        goSleep(3000);
        switchToParentWindow();
        goSleep(7000);
    }

    public void selectPNBNetBankingMenu() throws InterruptedException {
        goSleep(3000);
        switchToRazorpayFrame(razorpayFrame);
        click(NetBankingmenu);
        LOGGER.info("User clicked net banking menu");
        goSleep(2000);
        click(PNBNetBankingmenu);
        LOGGER.info("User selecting PNB net banking menu");
        goSleep(2000);
        switchToChildWindow();
        goSleep(2000);
        click(Acceptbtn);
        goSleep(7000);
        //switchToDefaultContent();
        LOGGER.info("user completed the payment");
        goSleep(3000);
        switchToParentWindow();
        goSleep(7000);
    }

    public void verifyPurchaseProductDetails(){
        String currentUrl = driver.getCurrentUrl();
        LOGGER.info(driver.getCurrentUrl());
        URI uri = URI.create(currentUrl);
        String path = uri.getPath();
        String OrderId = path.substring(path.lastIndexOf('/') + 1);
        LOGGER.info("Order Id: " + OrderId);
        LOGGER.info(driver.getTitle());
        isDisplayed(Orderplacedlbl);
        LOGGER.info("User is able to see product details");
    }


    protected void waitForOverlayToDisappear(int timeoutSeconds) {

        WebDriverWait customWait =
                new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        try {
            customWait.until(
                    ExpectedConditions.invisibilityOfElementLocated(
                            By.cssSelector("div.fixed.inset-0")
                    )
            );
        } catch (TimeoutException e) {
            LOGGER.warn("Overlay is still present after {} seconds", timeoutSeconds);
        }
    }
    public void performLogout() throws InterruptedException {
        goSleep(3000);
        LOGGER.info("User is performing logout flow");
        waitForOverlayToDisappear(20);
        clickDuration(profileIconmenu,10);
        goSleep(1000);
        LOGGER.info("User is clicked on profile menu");
        goSleep(1000);
        click(PersonalDetailsMenu);
        LOGGER.info("User is clicked on personal details menu");
        goSleep(1000);
        click(LogoutButton);
        LOGGER.info("User logout successfully");
        isDisplayed(LogoutSuccesslbl);
    }





}
