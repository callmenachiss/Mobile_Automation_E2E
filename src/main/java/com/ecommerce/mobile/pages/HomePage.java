package com.ecommerce.mobile.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page 2: Home screen showing the product catalog (list of products).
 *
 * NOTE ON LOCATORS: placeholders below - update with Appium Inspector.
 */
public class HomePage extends BasePage {

    private static final Logger LOGGER = LogManager.getLogger(HomePage.class);


    @AndroidFindBy(id = "com.sec.android.app.popupcalculator:id/calc_keypad_btn_08")
    private WebElement NumberBox;

    @AndroidFindBy(id = "com.sec.android.app.popupcalculator:id/calc_keypad_btn_add")
    private WebElement PlusBox;

    @AndroidFindBy(id = "com.sec.android.app.popupcalculator:id/calc_keypad_btn_equal")
    private WebElement EqualBox;

    @AndroidFindBy(id = "com.sec.android.app.popupcalculator:id/calc_edt_formula")
    private WebElement AnswerBox;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/et_search")
    private WebElement searchBox;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/rv_product_list")
    private WebElement productList;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/tv_product_name")
    private List<WebElement> productNames;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/btn_sort_by_price")
    private WebElement sortByPriceButton;

    @AndroidFindBy(id = "com.example.ecommerceapp:id/iv_cart_icon")
    private WebElement cartIcon;

    //locators for gajab application
    @AndroidFindBy(accessibility = "Start Bargaining Today!")
    private WebElement bargainingButton;

    @AndroidFindBy(accessibility = "Home & Kitchen")
    private WebElement homeKitchenMenu;

    @AndroidFindBy(accessibility = "home_profile_avatar_button")
    private WebElement profileAvatar;

    @AndroidFindBy(accessibility = "Personal details")
    private WebElement personalDetailsButton;

    @AndroidFindBy(accessibility = "END SESSION\nLogout")
    private WebElement logoutButton;

    @AndroidFindBy(className = "android.widget.EditText")
    private WebElement inputField;

    //@AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.widget.ImageView[3]")

    @AndroidFindBy(xpath = "//android.view.View[@resource-id=\"home_search_bar\"]")
    private WebElement Menulabel;

    @AndroidFindBy(xpath = "(//android.widget.ScrollView//android.widget.ImageView)[1]")
    private WebElement firstProductCard;

    @AndroidFindBy(xpath = "//android.view.View[starts-with(@content-desc, 'child_category_product_card_')]")
    public List<WebElement> productCards;

    @AndroidFindBy(xpath = "(//android.widget.ScrollView//android.widget.ImageView)[1]")
    public WebElement firstProducttextlbl;

    @AndroidFindBy(accessibility = "pdp_commonsheet_bargain_button")
    private WebElement startBargainingButton;

    @AndroidFindBy(accessibility = "pdp_bargains_offer_your_price_button")
    private WebElement offerYourPriceButton;

    @AndroidFindBy(accessibility = "Bargain More")
    private WebElement BargainMoreButton;

    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id=\"pdp_bargains_price_input\"]")
    private WebElement BargainPriceBox;

    @AndroidFindBy(accessibility = "pdp_bargains_accept_offer_button")
    private WebElement acceptOfferButton;

    @AndroidFindBy(accessibility = "pdp_deal_buy_now_button")
    private WebElement buyNowButton;

    @AndroidFindBy(accessibility= "cart_payment_online_button")
    private WebElement payOnlineButton;

    //@AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, 'Pay ₹')]")

    @AndroidFindBy(accessibility= "cart_checkout_action_button")
    private WebElement payButton;



    public void clickBargainingButton() {
        tap(bargainingButton);
    }

    public void clickAcceptOffer()  throws InterruptedException{
        sleep(7000);
        tap(acceptOfferButton);
        LOGGER.info("User accepting the offer");
    }

    public void clickBuyNow() {
        tap(buyNowButton);
        LOGGER.info("User clicking the buy now button");
    }

    public void clickPay() throws InterruptedException {
        sleep(1000);
        tap(payOnlineButton);
        sleep(1000);
        tap(payButton);
        LOGGER.info("User clicking pay button");
    }

    public void clickStartBargaining() throws InterruptedException {
        sleep(2000);
        tap(startBargainingButton);
        LOGGER.info("User clicked start Bargaining button");
    }

    public void bargainFirstattempt(String price) throws InterruptedException {
        sleep(5000);
        tap(BargainPriceBox);
        enterText(BargainPriceBox,price);
        sleep(2000);
        driver.navigate().back();
        sleep(4000);
        LOGGER.info("User quoted first bargain price for the product");
    }

    public void clickBargainMorebutton() throws InterruptedException {
        sleep(1000);
        tap(BargainMoreButton);
        LOGGER.info("User clicked on bargain more button to try 1 more attempt on bargain process");
    }

    public void clickOfferYourPrice() throws InterruptedException {
        sleep(2000);
        tap(offerYourPriceButton);
        LOGGER.info("User clicked offer price button");
    }

    public void clickLogout() {
        tap(logoutButton);
        LOGGER.info("User logged out");
    }

    public void clickFirstProduct() {
        //tap(firstProductCard);
        //productCards.get(0).click();
        //LOGGER.info("User clicked on first product from search results");
        WebElement firstProduct = productCards.get(2);
        waitUntilVisible(firstProduct);
        firstProduct.click();
        LOGGER.info("User clicked on first product from search results");
    }

    public String getFirstProductText() {
        //waitUntilVisible(firstProducttextlbl);
        //String text = firstProducttextlbl.getAttribute("content-desc");
        //LOGGER.info("First product text: {}", text);

        WebElement firstProduct = productCards.get(2);
        waitUntilVisible(firstProduct);
        String text = firstProduct.getAttribute("content-desc");
        LOGGER.info("First product text: {}", text);
        return text;
    }

    public String getTextElement(WebElement element) {
        waitUntilVisible(element);
        return element.getText();
    }

    public void clickProfileAvatar() {
        tap(profileAvatar);
        LOGGER.info("User clicked profile avatar");
    }

    public void clickPersonalDetails() {
        tap(personalDetailsButton);
        LOGGER.info("User  clicked personal details");
    }

    public boolean isHomePageDisplayed() {
        LOGGER.info("User is able to see menu items");
        LOGGER.info("User landed in HomePage");
        return isDisplayed(homeKitchenMenu);
    }


    public boolean isMenuDisplayed() {
        LOGGER.info("User in HomePage");
        LOGGER.info("Existing login deducted.. performing logout and login again");
        return isDisplayed(homeKitchenMenu);
    }

    public boolean isLoginExists() {
        LOGGER.info("Existing login deducted.. performing logout and login again");
        return isDisplayed(profileAvatar);
    }



    public boolean isProductListDisplayed() {
        return isDisplayed(productList) && !productNames.isEmpty();
    }

    public void searchForProduct(String productName) {
        tap(Menulabel);
        //String productName="Wireless Headphones";
        enterText(inputField,productName);
        clickDoneOnKeyboard();
        LOGGER.info("Searching for product: {}", productName);
    }

    public void sortProductsByPrice() {
        tap(sortByPriceButton);
    }

    public void openProductByName(String productName) {
        LOGGER.info("Opening product details for: {}", productName);
        for (WebElement product : productNames) {
            if (getText(product).equalsIgnoreCase(productName)) {
                tap(product);
                return;
            }
        }
        throw new RuntimeException("Product '" + productName + "' was not found on the Home page.");
    }





    public int getDisplayedProductCount() {
        return productNames.size();
    }

    public boolean isProductVisible(String productName) {
        return productNames.stream().anyMatch(product -> getText(product).equalsIgnoreCase(productName));
    }

    public void openCart() {
        tap(cartIcon);
    }
}
