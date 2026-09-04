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

    @FindBy(xpath = "//a[@id='home-wp11-curated-view-more-link']")
    private WebElement FactoryToYourCartMenulnk;

    @FindBy(xpath = "//img[contains(@class,'object-contain w-full')]")
    private List<WebElement> searchProductNameResults;

    @FindBy(xpath = "(//div[@id='home-maybe-animate-wrapper'])[1]")
    private WebElement MostBargainList;

    @FindBy(xpath = "//span[normalize-space()='Start Bargaining']")
    private WebElement StartBargainbtn;

    @FindBy(xpath = "//button[@id='checkout-update-address-btn']")
    private WebElement UpdateAddressbtn;

    @FindBy(xpath = "//span[@id='add-address-modal-submit-update-text']")
    private WebElement updateAddressbtn;

    @FindBy(xpath = "//a[normalize-space()='Edit']")
    private WebElement AddressEditbtn;


    @FindBy(xpath = "//input[@id='bargain-offer-price']")
    private WebElement BargainTxtBox;

    @FindBy(xpath = "//span[normalize-space(text())='Offer Your Price']")
    private WebElement offerPricebtn;

    @FindBy(xpath = "//span[@id='bargain-again-btn-text']")
    private WebElement BargainMorebtn;

    @FindBy(xpath = "//span[normalize-space(text())='Accept the offer']")
    private WebElement AcceptOfferbtn;

    @FindBy(xpath = "//span[@id='bargain-buy-now-text-span']")
    private WebElement BuyNowbtn;

    @FindBy(xpath = "//span[contains(text(),'Buy Now')]")
    private WebElement buyNowbtn;

    @FindBy(xpath = "//span[@id='checkout-pay-now-desktop-online-text']")
    private WebElement PayNowbtn;

    @FindBy(xpath = "//span[@id='checkout-payment-cod-label']")
    private WebElement CODbtn;

    private By sortingOption(String option) {
        return By.xpath("//*[contains(text(),'" + option + "')]");
    }

    @FindBy(xpath = "//span[@id='checkout-pay-now-desktop-cod-symbol']")
    private WebElement CODPayNowbtn;

    @FindBy(xpath = "//span[contains(text(),\"Netbanking\")]")
    private WebElement NetBankingmenu;


    @FindBy(xpath = "//img[@id='brand-filter-accordion-close-icon']")
    private WebElement CollapseMenu;

    @FindBy(xpath = "//h4[@id='price-filter-website-title']")
    private WebElement PriceMenulbl;

    @FindBy(xpath = "//span[@id='price-filter-website-checkbox-text-1']")
    private WebElement Price1Menu;

    @FindBy(xpath = "//span[@id='price-filter-website-checkbox-text-2']")
    private WebElement Price2Menu;

    @FindBy(xpath = "//span[@id='price-filter-website-checkbox-text-3']")
    private WebElement Price3Menu;

    @FindBy(xpath = "//span[@id='price-filter-website-checkbox-text-4']")
    private WebElement Price4Menu;

    @FindBy(xpath = "//a[contains(text(),'Home & Kitchen')]")
    private WebElement HomeandKitchenMenu;

    @FindBy(xpath = "//a[contains(text(),'Toys & Games')]")
    private WebElement ToysandGamesMenu;

    @FindBy(xpath = "//a[contains(text(),'Fashion Accessories')]")
    private WebElement FashionAccessoriesMenu;

    @FindBy(xpath = "//*[contains(text(),'Kitchen & Dining')]")
    private WebElement KitchenandDiningMenu;

    @FindBy(xpath = "//a[.//p[normalize-space()='Puzzles']]")
    private WebElement PuzzlesMenu;

    @FindBy(xpath = "//a[.//p[normalize-space()='Games']]")
    private WebElement GamesMenu;

    @FindBy(xpath = "(//*[contains(text(),'Watches')])[2]")
    private WebElement WatchesMenu;

    @FindBy(xpath = "//*[contains(text(),'Relevance')]")
    private WebElement RelevanceMenu;

    @FindBy(xpath = "//*[contains(text(),'New Arrivals')]")
    private WebElement NewArrivalsMenu;

    @FindBy(css = "iframe.razorpay-checkout-frame")
    private WebElement razorpayFrame;

    @FindBy(xpath = "//span[contains(text(),\"Bank of Baroda\")]")
    private WebElement BOBNetBankingmenu;

    @FindBy(xpath = "//span[contains(text(),\"Canara Bank\")]")
    private WebElement CanaraNetBankingmenu;

    @FindBy(id = "brand-filter-pagination-actions")
    private WebElement MoreMenu;

    @FindBy(xpath = "//span[contains(text(),\"Punjab National Bank - Retail Banking\")]")
    private WebElement PNBNetBankingmenu;

    @FindBy(xpath = "//span[contains(text(),\"IDBI\")]")
    private WebElement IDBINetBankingmenu;

    @FindBy(xpath = "//button[@class='success']")
    private WebElement Acceptbtn;

    @FindBy(id = "brand-filter-item-name-14")
    private WebElement SeraBasketMenu;

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

    @FindBy(xpath = "//span[@id='add-address-modal-type-home-text']")
    private WebElement HomeRadiobox;

    @FindBy(xpath = "//img[@id='add-address-modal-billing-uncheck']")
    private WebElement Defaultbox1;

    @FindBy(xpath = "//span[@id='add-address-modal-billing-text']")
    private WebElement Defaultbox2;

    @FindBy(xpath = "//button[@id='add-address-modal-submit-btn']")
    private WebElement SubmitBtn;

    @FindBy(xpath = "//input[@id='add-address-modal-pincode-input']")
    private WebElement pinCodeValue;

    @FindBy(xpath = "//span[@id='social-share-label']")
    private WebElement ShareMenu;

    @FindBy(xpath = "//h3[normalize-space(text())='Personal details']")
    private WebElement PersonalDetailsMenu;

    @FindBy(xpath = "//h3[normalize-space(text())='Addresses']")
    private WebElement AddressesMenu;

    @FindBy(xpath = "//span[@id='social-share-label']")
    private WebElement shareMenu;

    @FindBy(xpath = "//p[@id='social-share-copy-link-text']")
    private WebElement copiedLinkText;

    @FindBy(xpath = "//button[contains(text(),'Delete')]")
    private WebElement DeleteAddressesBtn;

    @FindBy(xpath = "//span[contains(text(),'Delete')]")
    private WebElement deleteAddressesBtn;

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

    @FindBy(xpath = "//h2[normalize-space(text())='Just Bargained']")
    public WebElement JustBargainlbl;

    @FindBy(xpath = "//h2[normalize-space(text())='Gajab Deal Of The Day']")
    public WebElement DealOfTheDayLbl;

    @FindBy(xpath = "//a[@id='home-wp1-view-more']")
    public WebElement JustBargainProductMenulnk;

    @FindBy(xpath = "//img[@id='home-wp3-product-img']")
    public WebElement DealOfTheDayMenulnk;

    @FindBy(xpath = "//img[@id='header-logo-img']")
    public WebElement Gajablogo;



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

    public void setPincode(String pinCode) throws InterruptedException {
        goSleep(3000);
        waitUntilVisible(LocationMenu);
        Actions actions = new Actions(driver);
        actions.moveToElement(LocationMenu).perform();
        goSleep(2000);
        click(LocationTxtbox);
        goSleep(2000);
        enterText(LocationTxtbox, pinCode);
        goSleep(2000);
        click(LocationTargetTxtbox);
        LOGGER.info("User selected the pin code value");
    }

    public void checkAvailableProducts() throws InterruptedException {
        goSleep(4000);
        clickDuration(searchproductName,10);
        LOGGER.info("User clicked the available products");
    }

    public void selectTargetProduct(int option) throws InterruptedException {
        goSleep(4000);
        searchProductNameResults.get(option).click();
        LOGGER.info("User selecting the products from search results");
    }

    public void NavigateToFactoryToYourCartSection() throws InterruptedException {
        goSleep(6000);
        scrollDown(2000);
        goSleep(4000);
        scrollIntoView(FactoryToYourCartMenulnk);
        goSleep(2000);
        click(FactoryToYourCartMenulnk);
        goSleep(4000);
    }

    public void verifyProductDetails() throws InterruptedException {
        goSleep(5000);
        scrollUp(200);
        goSleep(3000);
        Actions actions = new Actions(driver);
        actions.moveToElement(ShareMenu).perform();
        goSleep(2000);
        click(copiedLinkText);
        String sharedLink = getClipboardText();
        LOGGER.info("Product details copied and wrapped as link to share info through email");
        LOGGER.info("Product details shared link {}",sharedLink);
    }

    public void verifyAddressDetails() throws InterruptedException {
        goSleep(2000);
        click(UpdateAddressbtn);
        goSleep(2000);
        click(AddressEditbtn);
        goSleep(3000);
        click(pinCodeValue);
        String value=pinCodeValue.getText();
        System.out.println(value);
        LOGGER.info("User is able to see the pin code {}",value);
        goSleep(2000);
        click(updateAddressbtn);
        LOGGER.info("User selected the address details");
    }

    public void NavigatetoAddressesMenu() throws InterruptedException {
        goSleep(2000);
        clickDuration(profileIconmenu,10);
        goSleep(1000);
        LOGGER.info("User  clicked on profile menu");
        click(AddressesMenu);
        LOGGER.info("User navigating to the address menu");
    }

    public void deleteAddressDetails()throws InterruptedException{
        goSleep(2000);
        click(DeleteAddressesBtn);
        goSleep(2000);
        click(deleteAddressesBtn);
        goSleep(2000);
        LOGGER.info("User delete the address details");
    }

    public void NavigateToMostBargainedProductsMenu() throws InterruptedException {
        goSleep(5000);
        scrollIntoView(JustBargainlbl);
        goSleep(1000);
        isDisplayed(JustBargainlbl);
        LOGGER.info("User navigated to most bargained products menu");
        goSleep(1000);
        click(JustBargainProductMenulnk);
        goSleep(3000);
        scrollDown(2000);
        goSleep(3000);
        scrollDown(2500);
        LOGGER.info("User is able to see the most bargained products menu");
    }

    public void NavigateToDealofDayMenu() throws InterruptedException {
        goSleep(2000);
        click(Gajablogo);
        goSleep(5000);
        scrollIntoView(DealOfTheDayLbl);
        goSleep(1000);
        isDisplayed(DealOfTheDayLbl);
        LOGGER.info("User navigated to Deal Of The Day products menu");
        goSleep(1000);
        click(DealOfTheDayMenulnk);
        goSleep(3000);
        scrollDown(2000);
        goSleep(3000);
        scrollDown(2500);
        LOGGER.info("User is able to see the Deal of the day products menu");
    }

    public void SelectMostBargainedProducts() throws InterruptedException {
        goSleep(3000);
        scrollIntoView(JustBargainlbl);
        goSleep(2000);
        click(MostBargainList);
        goSleep(2000);
        LOGGER.info("User selected product from most bargained products menu");
    }

    public void SelectDealOfTheDayProducts() throws InterruptedException {
        goSleep(3000);
        scrollIntoView(DealOfTheDayLbl);
        goSleep(2000);
        click(DealOfTheDayMenulnk);
        goSleep(2000);
        LOGGER.info("User selected product from deal of the day menu");
    }

    public void NavigateToHomePageMenu() throws InterruptedException {
        goSleep(2000);
        click(Gajablogo);
        goSleep(2000);
        String url = getCurrentUrl();
        String title = getPageTitle();
        LOGGER.info("Current URL: {}", url);
        LOGGER.info("Page Title: {}", title);
    }

    public void addAddressIntoMenu() throws InterruptedException {
        goSleep(1000);
        click(AddAddressbtn);
        LOGGER.info("User navigated to Addresses Menu page");
        goSleep(1000);
        String addr1=randomAddress("test");
        String addr2=randomAddress("hello");
        goSleep(1000);
        enterText(Address1Txtbox,addr1);
        goSleep(1000);
        enterText(Address2Txtbox,addr2);
        goSleep(1000);
        click(HomeRadiobox);
        goSleep(1000);
        click(Defaultbox1);
        goSleep(1000);
        //click(Defaultbox2);
        //goSleep(1000);
        click(SubmitBtn);
        LOGGER.info("User successfully added address");
    }


    public void startBargainprocess() throws InterruptedException {
        goSleep(5000);
        try{
            if(StartBargainbtn.isDisplayed()){
                goSleep(4000);
                click(StartBargainbtn);
                LOGGER.info("User started bargain process");
                goSleep(2000);
                click(offerPricebtn);
                goSleep(2000);
                click(AcceptOfferbtn);
                LOGGER.info("User accepted the offer");
                goSleep(5000);
                click(BuyNowbtn);
                LOGGER.info("User clicked on Buy Now button");
            }
        } catch (Exception e) {click(buyNowbtn);
        }
    }

    public void startFirstBargainprocess() throws InterruptedException {
        goSleep(6000);
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

    public void startMultipleAttemptBargainprocess() throws InterruptedException {
        goSleep(4000);
        click(StartBargainbtn);
        LOGGER.info("User started the bargain process");
        goSleep(5000);
        enterText(BargainTxtBox,"30");
        click(offerPricebtn);
        LOGGER.info("User quoted first attempt bargain price");
        goSleep(2000);
        click(BargainMorebtn);
        goSleep(2000);
        enterText(BargainTxtBox,"95");
        click(offerPricebtn);
        LOGGER.info("User quoted second attempt bargain price");
        goSleep(2000);
        click(BargainMorebtn);
        goSleep(2000);
        click(offerPricebtn);
        //goSleep(2000);
        //enterText(BargainTxtBox,"95");
        //goSleep(2000);
        //click(AcceptOfferbtn);
        LOGGER.info("user accepted the offer");
        goSleep(5000);
        click(BuyNowbtn);
        LOGGER.info("User clicked on Buy now button");
    }

    public void clickPayNowButton() throws InterruptedException{
        goSleep(6000);
        scrollIntoView(PayNowbtn);
        click(PayNowbtn);
        LOGGER.info("User clicked on pay now button");
        goSleep(3000);
    }

    public void clickBuyNowButton() throws InterruptedException{
        goSleep(3000);
        scrollIntoView(BuyNowbtn);
        click(BuyNowbtn);
    }

    public void clickCODButton() throws InterruptedException{
        goSleep(6000);
        scrollIntoView(CODbtn);
        goSleep(1000);
        click(CODbtn);
        LOGGER.info("COD");
        goSleep(6000);
        click(CODPayNowbtn);
        LOGGER.info("User clicked on pay now button COD Order");
    }

    public void NavigateToHomeAndKitchenMenu() throws InterruptedException {
        goSleep(7000);
        mouseOver(HomeandKitchenMenu);
        goSleep(3000);
        click(KitchenandDiningMenu);
        LOGGER.info("User navigated to Home and Kitchen Menu");
        scrollToMiddleSlowly();
    }
    public void NavigateToToysAndGamesMenu() throws InterruptedException {
        goSleep(3000);
        click(Gajablogo);
        goSleep(4000);
        mouseOver(ToysandGamesMenu);
        goSleep(3000);
        Actions actions = new Actions(driver);
        actions.moveToElement(PuzzlesMenu).perform();
        click(PuzzlesMenu);
        LOGGER.info("User navigated to Toys and Games Menu");
        scrollToMiddleSlowly();
    }

    public void setPriceRangeFilters() throws InterruptedException {
        goSleep(3000);
        click(CollapseMenu);
        goSleep(1000);
        isDisplayed(PriceMenulbl);
        click(Price1Menu);
        goSleep(3000);
        scrollDown(200);
        click(Price1Menu);
        click(Price2Menu);
        goSleep(3000);
        //ScrollToMiddleSlowly();
        click(Price2Menu);
        click(Price3Menu);
        goSleep(3000);
        //ScrollToMiddleSlowly();
        click(Price3Menu);
        click(Price4Menu);
        goSleep(3000);
        //ScrollToMiddleSlowly();
        click(Price4Menu);
        LOGGER.info("Price range filters applied in product list page");
    }

    public void NavigateToysGamesMenu() throws InterruptedException {
        goSleep(6000);
        click(Gajablogo);
        goSleep(4000);
        mouseOver(ToysandGamesMenu);
        goSleep(3000);
        Actions actions = new Actions(driver);
        actions.moveToElement(GamesMenu).perform();
        click(GamesMenu);
        LOGGER.info("User is navigated to Toys and Games Menu");
        scrollToMiddleSlowly();
    }

    public void NavigateToFashionAndAccessoryMenu() throws InterruptedException {
        goSleep(3000);
        click(Gajablogo);
        goSleep(4000);
        mouseOver(FashionAccessoriesMenu);
        goSleep(3000);
        click(WatchesMenu);
        LOGGER.info("User navigated to Fashion Accessory Menu");
        scrollToMiddleSlowly();
    }

    public void setSeraBasketMenuForHomeAndKitchenMenu() throws InterruptedException {
        goSleep(2000);
        click(MoreMenu);
        goSleep(4000);
        click(SeraBasketMenu);
        scrollDown(200);
        goSleep(2000);
        LOGGER.info("Sera Basket filter applied in product list page");
    }

    public void setSeraBasketMenuForToysAndGamesMenu() throws InterruptedException {
        goSleep(5000);
        click(SeraBasketMenu);
        scrollDown(200);
        goSleep(2000);
        LOGGER.info("Sera Basket filter applied to Toys and Games page");
    }
    public void setFilersForHomeAndKitchenMenu() throws InterruptedException {
        goSleep(5000);
        //click(RelevanceMenu);
        selectByText("Relevance");
        goSleep(4000);
        click(NewArrivalsMenu);
        scrollDown(600);
        goSleep(4000);
        scrollDown(600);
        selectByText("New Arrivals");
        goSleep(4000);
        selectByText("Price (High to Low)");
        goSleep(4000);
        scrollDown(600);
        selectByText("Price (High to Low)");
        goSleep(4000);
        selectByText("Price (Low to High)");
        goSleep(4000);
        scrollDown(600);
        LOGGER.info("User Applied filters successfully in Home and Kitchen Menu");
    }

    public void selectCanaraNetBankingMenu() throws InterruptedException {
        goSleep(4000);
        switchToRazorpayFrame(razorpayFrame);
        //switchToChildWindow();
        click(NetBankingmenu);
        LOGGER.info("User clicked on the net banking menu");
        goSleep(5000);
        click(CanaraNetBankingmenu);
        LOGGER.info("User selecting Canara net banking menu");
        goSleep(5000);
        switchToChildWindow();
        goSleep(5000);
        click(Acceptbtn);
        goSleep(7000);
        //switchToDefaultContent();
        LOGGER.info("User completed the Payment");
        goSleep(3000);
        switchToParentWindow();
        goSleep(7000);
    }

    public void selectToysBrandMenu() throws InterruptedException {
        goSleep(2000);
        brandOption("calyxia");
        scrollDown(200);
        goSleep(2000);
        brandOption("Unique International");
        scrollDown(200);
        goSleep(2000);
        brandOption("primewell");
        scrollDown(200);
        goSleep(2000);
        LOGGER.info("User applied filters for Brand menu");
    }

    public void selectBrandMenu() throws InterruptedException {
        goSleep(2000);
        brandOption("Alpha Enterprises");
        scrollDown(200);
        goSleep(2000);
        brandOption("AMELLA E-KITCHEN");
        scrollDown(200);
        goSleep(2000);
        brandOption("MAKBRO");
        scrollDown(200);
        goSleep(2000);
        click(MoreMenu);
        goSleep(4000);
        try {click(SeraBasketMenu);} catch (Exception e) {}
        scrollDown(200);
        goSleep(2000);
        LOGGER.info("User selected Brand menu");
    }

    public void SelectBrandMenu() throws InterruptedException {
        goSleep(2000);
        brandOption("calyxia");
        scrollDown(100);
        goSleep(2000);
        brandOption("Generic");
        scrollDown(50);
        goSleep(2000);
        brandOption("Unique International");
        scrollDown(200);
        goSleep(2000);
        LOGGER.info("User applied Brand filters");
    }


    public void selectIDBINetBankingMenu() throws InterruptedException {
        goSleep(4000);
        switchToRazorpayFrame(razorpayFrame);
        //switchToChildWindow();
        click(NetBankingmenu);
        LOGGER.info("User clicked on Net banking menu");
        goSleep(5000);
        click(IDBINetBankingmenu);
        LOGGER.info("User selecting IDBI net banking menu");
        goSleep(5000);
        switchToChildWindow();
        goSleep(5000);
        click(Acceptbtn);
        goSleep(7000);
        //switchToDefaultContent();
        LOGGER.info("User Completed the payment");
        goSleep(3000);
        switchToParentWindow();
        goSleep(7000);
    }

    public void selectPNBNetBankingMenu() throws InterruptedException {
        goSleep(4000);
        switchToRazorpayFrame(razorpayFrame);
        click(NetBankingmenu);
        LOGGER.info("User clicked net banking menu");
        goSleep(5000);
        click(PNBNetBankingmenu);
        LOGGER.info("User selecting PNB net banking menu");
        goSleep(5000);
        switchToChildWindow();
        goSleep(5000);
        click(Acceptbtn);
        goSleep(7000);
        //switchToDefaultContent();
        LOGGER.info("user completed the payment");
        goSleep(3000);
        switchToParentWindow();
        goSleep(7000);
    }

    public void selectBOBNetBankingMenu() throws InterruptedException {
        goSleep(4000);
        switchToRazorpayFrame(razorpayFrame);
        click(NetBankingmenu);
        LOGGER.info("User clicked on net banking menu");
        goSleep(5000);
        click(BOBNetBankingmenu);
        LOGGER.info("User selecting BOB net banking menu");
        goSleep(5000);
        switchToChildWindow();
        goSleep(5000);
        click(Acceptbtn);
        goSleep(7000);
        LOGGER.info("User completed the payment");
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

    public void verifyLiveOrders() throws InterruptedException {
        click(Gajablogo);
        goSleep(1000);
        scrollDown(800);
        goSleep(14000);
        LOGGER.info("User is able to purchased products in live orders section");
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
