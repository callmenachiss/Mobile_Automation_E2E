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

    @FindBy(xpath = "//span[normalize-space()='Start Bargaining']")
    private WebElement StartBargainbtn;

    @FindBy(xpath = "//span[normalize-space(text())='Offer Your Price']")
    private WebElement offerPricebtn;

    @FindBy(xpath = "//span[normalize-space(text())='Accept the offer']")
    private WebElement AcceptOfferbtn;

    @FindBy(xpath = "//span[contains(text(),\"Buy Now\")]")
    private WebElement BuyNowbtn;

    @FindBy(xpath = "//span[@id='checkout-pay-now-desktop-online-text']")
    private WebElement PayNowbtn;

    @FindBy(xpath = "//span[contains(text(),\"Netbanking\")]")
    private WebElement NetBankingmenu;

    @FindBy(xpath = "//span[contains(text(),\"Bank of Baroda\")]")
    private WebElement BOBNetBankingmenu;

    @FindBy(xpath = "//button[@class='success']")
    private WebElement Acceptbtn;

    @FindBy(xpath = "//h6[normalize-space(text())='Order placed!']")
    public WebElement Orderplacedlbl;

    @FindBy(xpath = "(//img[@alt='profile'])[1]")
    private WebElement profileIconmenu;

    @FindBy(xpath = "//h3[normalize-space(text())='Personal details']")
    private WebElement PersonalDetailsMenu;

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

    public void clickPayNowButton() throws InterruptedException{
        goSleep(3000);
        click(PayNowbtn);
        LOGGER.info("User clicked on pay now button");
        goSleep(3000);
    }

    public void selectBOBNetBankingMenu() throws InterruptedException {
        goSleep(2000);
        click(NetBankingmenu);
        LOGGER.info("User clicked on net banking menu");
        click(BOBNetBankingmenu);
        LOGGER.info("User clicked on BOB net banking menu");
        switchToChildWindow();
        click(Acceptbtn);
        switchToParentWindow();
        LOGGER.info("User completed the payment");
    }


    public void verifyPurchaseProductDetails(){
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
