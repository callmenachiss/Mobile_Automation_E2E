package com.ecommerce.mobile.stepdefinitions;

import com.ecommerce.mobile.pages.CalculatorPage;
import io.cucumber.java.en.When;
import org.testng.Assert;



public class CalculatorSteps {


    private final CalculatorPage calculatorPage = new CalculatorPage();

    @When("I clicked on two inputs from customer")
    public void I_clicked_on_two_inputs_from_customer() throws InterruptedException {
        String result = calculatorPage.SumofNum();
        Assert.assertEquals(result, "16", "Calculator result is incorrect");
    }


}
