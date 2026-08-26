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
public class CalculatorPage extends BasePage {

    private static final Logger LOGGER = LogManager.getLogger(CalculatorPage.class);


    @AndroidFindBy(id = "com.sec.android.app.popupcalculator:id/calc_keypad_btn_08")
    private WebElement NumberBox;

    @AndroidFindBy(id = "com.sec.android.app.popupcalculator:id/calc_keypad_btn_add")
    private WebElement PlusBox;

    @AndroidFindBy(id = "com.sec.android.app.popupcalculator:id/calc_keypad_btn_equal")
    private WebElement EqualBox;

    @AndroidFindBy(id = "com.sec.android.app.popupcalculator:id/calc_edt_formula")
    private WebElement AnswerBox;


    public String SumofNum() {
        LOGGER.info("started execution");
        tap(NumberBox);
        tap(PlusBox);
        tap(NumberBox);
        tap(EqualBox);
        String resultText=AnswerBox.getText();
        LOGGER.info("Results are: {}",resultText );
        // Extract only the numeric result
        String result = resultText.replaceAll("[^0-9.-]", "");
        LOGGER.info("Clean calculator result: {}", result);
        return result;
    }


}
