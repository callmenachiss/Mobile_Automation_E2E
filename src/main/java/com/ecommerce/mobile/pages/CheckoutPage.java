package com.ecommerce.mobile.pages;

import com.ecommerce.mobile.config.DriverManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Page 2: Home screen showing the product catalog (list of products).
 *
 * NOTE ON LOCATORS: placeholders below - update with Appium Inspector.
 */
public class CheckoutPage extends BasePage {

    private static final Logger LOGGER = LogManager.getLogger(CheckoutPage.class);


    @AndroidFindBy(accessibility= "home_category_1_item")
    private WebElement HomeandKitchenMenu;

    @AndroidFindBy(accessibility= "home_category_2_item")
    private WebElement GiftingFestiveEssentialsMenu;

    @AndroidFindBy(accessibility= "home_category_4_item")
    private WebElement FashionandAccessoriesMenu;

    @AndroidFindBy(accessibility= "home_category_5_item")
    private WebElement StationeryMenu;

    @AndroidFindBy(accessibility= "home_category_3_item")
    private WebElement ToysandGamesMenu;

    @AndroidFindBy(accessibility= "child_category_item_2_button")
    private WebElement CategoryUnderHomeandKitchenMenu;

    @AndroidFindBy(accessibility= "product_template1_product_2_card")
    private WebElement SpecificProductInMenu;

    @AndroidFindBy(accessibility= "child_category_item_87_button")
    private WebElement CategoryUnderGiftingFestiveEssentialsMenu;

    @AndroidFindBy(accessibility= "child_category_item_19_button")
    private WebElement CategoryUnderToysandGamesMenu;

    @AndroidFindBy(accessibility= "child_category_filter_button")
    private WebElement filtersMenu;

    @AndroidFindBy(accessibility= "category_filter_tab_brands_button")
    private WebElement BrandfilterMenu;

    @AndroidFindBy(accessibility= "category_filter_tab_pricerange_button")
    private WebElement pricingMenu;

    @AndroidFindBy(accessibility= "the end value is 1356.0")
    private WebElement endPricingMenu;

    @AndroidFindBy(accessibility= "category_filter_item_0_button")
    private WebElement filtersSubMenu;

    @AndroidFindBy(accessibility= "category_filter_item_3_button")
    private WebElement SeraBasketSubMenu;

    @AndroidFindBy(accessibility= "category_filter_apply_button")
    private WebElement filtersApplyBtn;

    @AndroidFindBy(accessibility= "child_category_sort_button")
    private WebElement relevanceMenu;

    @AndroidFindBy(accessibility= "category_sort_option_new_arrivals_button")
    private WebElement NewArrivalsMenu;

    @AndroidFindBy(accessibility= "category_sort_option_price_low_to_high_button")
    private WebElement LowToHighMenu;

    @AndroidFindBy(accessibility= "category_sort_option_price_high_to_low_button")
    private WebElement HighToLowMenu;

    @AndroidFindBy(accessibility= "category_sort_option_ratings_button")
    private WebElement RatingsSortMenu;

    @AndroidFindBy(accessibility= "trending_view_all_button")
    private WebElement viewAllMenu;

    @AndroidFindBy(xpath = "//android.widget.ScrollView/android.view.View")
    private List<WebElement> trendingProducts;

    @AndroidFindBy(xpath= "//android.widget.ImageView[@content-desc=\"LIVE ORDERS\"]")
    private WebElement LiveOrderslbl;

    @AndroidFindBy(accessibility= "pdp_share_button")
    private WebElement shareMenu;

    @AndroidFindBy(xpath= "//android.widget.TextView[@resource-id=\"com.android.intentresolver:id/text1\" and @text=\"Gmail\"]")
    private WebElement gmailMenu;

    @AndroidFindBy(xpath= "//android.view.ViewGroup[@resource-id=\"com.google.android.gm:id/peoplekit_autocomplete_chip_group\"]/android.widget.EditText")
    private WebElement toEMail;

    @AndroidFindBy(xpath= "//android.widget.TextView[@resource-id=\"com.google.android.gm:id/peoplekit_listview_contact_name\"]")
    private WebElement SenderMailMenu;

    @AndroidFindBy(xpath= "//android.widget.EditText[@resource-id=\"com.google.android.gm:id/subject\"]")
    private WebElement subjectEmail;

    @AndroidFindBy(accessibility= "Send")
    private WebElement sendButton;

    @AndroidFindBy(accessibility= "dashboard_categories_tab")
    private WebElement categoryMenu;

    @AndroidFindBy(accessibility= "dashboard_bazaar_tab")
    private WebElement bazaarMenu;

    @AndroidFindBy(accessibility= "dashboard_bargains_tab")
    private WebElement bargainMenu;


    @AndroidFindBy(accessibility= "gajab_deal_product_card")
    private WebElement gajabdealCard;

    @AndroidFindBy(accessibility= "gajab_deal_block_1_button")
    private WebElement JustBargainCard;


    public void clickCategoryMenu() throws InterruptedException {
        sleep(2000);
        tap(categoryMenu);
        swipeDown();
        LOGGER.info("User navigated to the category menu");
    }


    public void clickGiftingFestiveEssentialsMenu() throws InterruptedException {
        sleep(2000);
        tap(GiftingFestiveEssentialsMenu);
        LOGGER.info("User navigated to the Gifting and Festive Essentials Menu");
    }

    public void clickStationeryMenu() throws InterruptedException {
        sleep(2000);
        tap(FashionandAccessoriesMenu);
        sleep(2000);
        tap(StationeryMenu);
        LOGGER.info("User navigated to the Stationery Menu");
    }

    public void clickToysandGamesMenu() throws InterruptedException {
        sleep(2000);
        tap(ToysandGamesMenu);
        LOGGER.info("User navigated to the Toys and Games Menu");
    }

    public void clickSubGiftingFestiveEssentialsMenu() throws InterruptedException {
        sleep(2000);
        tap(CategoryUnderGiftingFestiveEssentialsMenu);
        LOGGER.info("User navigated to sub section of Gifting and Festive Essentials Menu");
    }

    public void clickSubToysandGamesMenu() throws InterruptedException {
        sleep(2000);
        tap(CategoryUnderToysandGamesMenu);
        LOGGER.info("User navigated to sub section of Toys and Games Menu");
    }

    public void clickHomeandKitchenMenu() throws InterruptedException {
        sleep(2000);
        tap(HomeandKitchenMenu);
        LOGGER.info("User navigated to the Home and Kitchen Menu");
    }

    public void clickSubHomeandKitchenMenu() throws InterruptedException {
        sleep(2000);
        tap(CategoryUnderHomeandKitchenMenu);
        LOGGER.info("User navigated to sub section of Home and Kitchen Menu");
    }

    public void clickSpecificProducts() throws InterruptedException {
        sleep(2000);
        swipeDown();
        //scrollToAccessibilityId("product_template1_product_2_card");
        sleep(2000);
        tap(SpecificProductInMenu);
        swipeDown();
        LOGGER.info("User navigated to the specific product in menu");
    }

    public void setFiltersAndverifyproductsBasedOnCategory() throws InterruptedException {
        sleep(2000);
        validateLowToHighMenu();
        validateHighToLowMenu();
        validateNewArrivalsMenu();
        validateRatingsMenu();
        driver.navigate().back();
    }

    public void setSeraBrandFilters() throws InterruptedException {
        sleep(2000);
        validateFiltersMenu();
        driver.navigate().back();
    }

    public void ApplySeraBrandFilter() throws InterruptedException {
        sleep(2000);
        tap(filtersMenu);
        LOGGER.info("User navigated to Filters Menu");
        sleep(1000);
        tap(SeraBasketSubMenu);
        sleep(1000);
        tap(filtersApplyBtn);
        LOGGER.info("Sera's basket Filter applied in product list page");
        sleep(1000);
        swipeDown();
        LOGGER.info("User is able to view products based on filters");
        driver.navigate().back();
    }

    public void setPriceRangeFilters() throws InterruptedException {
        sleep(3000);
        tap(pricingMenu);
        sleep(1000);
        tap(endPricingMenu);
        sleep(1000);
        tap(filtersApplyBtn);
        LOGGER.info("Price range Filters applied in product list page");
        swipeDown();
        sleep(2000);
    }

    public void validateFiltersMenu() throws InterruptedException {
        sleep(2000);
        tap(filtersMenu);
        LOGGER.info("User navigated to the Filters Menu");
        sleep(1000);
        tap(filtersSubMenu);
        sleep(1000);
        tap(SeraBasketSubMenu);
        sleep(1000);
        tap(filtersApplyBtn);
        LOGGER.info("Filters applied in product list page");
        sleep(1000);
        swipeDown();
        LOGGER.info("User is able to see products based on filters");
    }

    public void validateLowToHighMenu() throws InterruptedException {
        sleep(2000);
        tap(relevanceMenu);
        sleep(2000);
        tap(LowToHighMenu);
        LOGGER.info("User is able to see the products from low to high price order");
        sleep(1000);
        swipeDown();
        LOGGER.info("User is able to see the products based on filters");
    }

    public void validateHighToLowMenu() throws InterruptedException {
        sleep(2000);
        tap(relevanceMenu);
        sleep(2000);
        tap(HighToLowMenu);
        LOGGER.info("User is able to see the products from high to low price order");
        sleep(1000);
        swipeDown();
        LOGGER.info("User is able to see the products based on filter");
    }

    public void validateNewArrivalsMenu() throws InterruptedException {
        sleep(2000);
        tap(relevanceMenu);
        sleep(2000);
        tap(NewArrivalsMenu);
        LOGGER.info("User is able to see the New Arrival products");
        sleep(1000);
        swipeDown();
        LOGGER.info("User is able to see the product based on filters");
    }

    public void validateRatingsMenu() throws InterruptedException {
        sleep(2000);
        tap(relevanceMenu);
        sleep(2000);
        tap(RatingsSortMenu);
        LOGGER.info("User is able to see the products based on ratings");
        sleep(1000);
        swipeDown();
        LOGGER.info("User is able to products based on ratings filters");
    }

    public void clickBargainsMenu() throws InterruptedException {
        sleep(2000);
        tap(bargainMenu);
        swipeDown();
        LOGGER.info("User navigated to the Bargain menu");
    }

    public void clickShareMenu() throws InterruptedException {
        sleep(2000);
        tap(shareMenu);
        sleep(2000);
        LOGGER.info("User clicked on share button");
    }

    public void clickTrendingmenu()throws InterruptedException{
        sleep(2000);
        tap(viewAllMenu);
        LOGGER.info("User navigated to the trending menu");
    }
    public void validateAnyoneTrendingProduct() throws InterruptedException {
        sleep(3000);
        swipeDown();
        sleep(2000);
        clickProductsInTrendingMenu(2);
        LOGGER.info("User is able to see trending product details");
    }

    public void validateAnotherTrendingProduct() throws InterruptedException {
        sleep(3000);
        driver.navigate().back();
        sleep(2000);
        swipeDown();
        sleep(2000);
        clickProductsInTrendingMenu(5);
        LOGGER.info("User is able to access trending product details");
    }

    public void navigateLiveOrdersSection() throws InterruptedException {
        sleep(2000);
        scrollToAccessibilityId("trending_view_all_button");
    }

    public void verifyLiveOrdersSection() throws InterruptedException {
        sleep(2000);
        isDisplayed(LiveOrderslbl);
        sleep(5000);
    }

    public void clickProductsInTrendingMenu(int i) throws InterruptedException {
        waitUntilVisible(trendingProducts.get(i));
        trendingProducts.get(i).click();
        LOGGER.info("Clicked on products in Trending page");
    }

    public void clickGmailMenu() throws InterruptedException {
        sleep(2000);
        tap(gmailMenu);
        sleep(2000);
        LOGGER.info("User clicked on Gmail app in list");
    }

    public void sentEmailCustomer() throws InterruptedException {
        sleep(4000);
        enterText(toEMail, "piccosofttest@gmail.com");
        sleep(2000);
        driver.navigate().back();
        sleep(2000);
        tap(SenderMailMenu);
        sleep(1000);
        enterText(subjectEmail, "Product details for your orders");
        sleep(2000);
        tap(sendButton);
        LOGGER.info("Email sent to customer for product details");
    }

    public void clickBazaarMenu() throws InterruptedException {
        sleep(2000);
        tap(bazaarMenu);
        LOGGER.info("User navigated to the bazaar menu");
    }


    public void verifyGajabdealforSession() throws InterruptedException{
        sleep(2000);
        isDisplayed(gajabdealCard);
        sleep(2000);
        tap(gajabdealCard);
        sleep(2000);
        driver.navigate().back();
        LOGGER.info("User is able to see the Gajab deals for the active session");
    }

    public void verifyJustBargaindealforSession() throws InterruptedException{
        sleep(2000);
        isDisplayed(JustBargainCard);
        sleep(2000);
        tap(JustBargainCard);
        sleep(2000);
        driver.navigate().back();
        LOGGER.info("User is able to see Just bargain deals for the active session");
    }






}
