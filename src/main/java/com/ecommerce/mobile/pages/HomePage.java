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

    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[4]")
    private WebElement profileAvatar;

    @AndroidFindBy(accessibility = "Personal details")
    private WebElement personalDetailsButton;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='END SESSION\nLogout']")
    private WebElement logoutButton;

    @AndroidFindBy(className = "android.widget.EditText")
    private WebElement inputField;

    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.widget.ImageView[3]")
    private WebElement Menulabel;

    @AndroidFindBy(xpath = "(//android.widget.ScrollView//android.widget.ImageView)[1]")
    private WebElement firstProductCard;

    @AndroidFindBy(xpath = "(//android.widget.ScrollView//android.widget.ImageView)[1]")
    public WebElement firstProducttextlbl;

    @AndroidFindBy(accessibility = "Start Bargaining")
    private WebElement startBargainingButton;

    @AndroidFindBy(accessibility = "Offer Your Price")
    private WebElement offerYourPriceButton;

    @AndroidFindBy(accessibility = "Accept the offer")
    private WebElement acceptOfferButton;

    @AndroidFindBy(accessibility = "Buy Now")
    private WebElement buyNowButton;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, 'Pay ₹')]")
    private WebElement payButton;



    public void clickBargainingButton() throws InterruptedException {
        sleep();
        tap(bargainingButton);
    }

    public void clickAcceptOffer() {
        tap(acceptOfferButton);
        LOGGER.info("User accepting the offer");
    }

    public void clickBuyNow() {
        tap(buyNowButton);
        LOGGER.info("User clicking the buy now button");
    }

    public void clickPay() {
        tap(payButton);
        LOGGER.info("User clicking pay button");
    }

    public void clickStartBargaining() throws InterruptedException {
        sleep();
        tap(startBargainingButton);
        LOGGER.info("User clicked start Bargaining button");
    }

    public void clickOfferYourPrice() {
        tap(offerYourPriceButton);
        LOGGER.info("User clicked offer price button");
    }

    public void clickLogout() {
        tap(logoutButton);
        LOGGER.info("User logged out");
    }

    public void clickFirstProduct() {
        tap(firstProductCard);
        LOGGER.info("User clicked on first product card");
    }

    public void getFirstProductText() {
        waitUntilVisible(firstProducttextlbl);
        String text = firstProducttextlbl.getAttribute("content-desc");
        LOGGER.info("First product text: {}", text);
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



    public boolean isProductListDisplayed() {
        return isDisplayed(productList) && !productNames.isEmpty();
    }

    public void searchForProduct(String productName) {
        tap(Menulabel);
        enterText(inputField, productName);
        clickDoneOnKeyboard();
        LOGGER.info("Searching for product: {}", productName);

        //enterText(searchBox, productName);
        //hideKeyboard();
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
