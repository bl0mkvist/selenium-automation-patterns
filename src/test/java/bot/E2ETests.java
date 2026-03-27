package bot;

import bot.core.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class E2ETests extends TestBase {
    private final String sauceLabsBackpackAddSelector = "#add-to-cart-sauce-labs-backpack";
    private final String cartButtonSelector = "a.shopping_cart_link";
    private final String checkoutButtonSelector = "#checkout";
    private final String firstNameTextFieldSelector = "#first-name";
    private final String lastNameTextFieldSelector = "#last-name";
    private final String postalCodeTextFieldSelector = "#postal-code";
    private final String continueButtonSelector = "input[type='submit'][data-test='continue']";
    private final String priceTextFieldValueSelector = ".inventory_item_price";
    private final String taxTextFieldValueSelector = ".summary_tax_label";
    private final String finishButtonSelector = "#finish";
    protected final String checkOutCompletedURL = "https://www.saucedemo.com/checkout-complete.html";

    @Test
    @DisplayName("E2E buying process check with amount calculation")
    public void verifying_e2e_scenario_Buying_one_item() {

        bot.validLogin();
        bot.waitForPresenceOfElementLocated(sauceLabsBackpackAddSelector);
        bot.click(sauceLabsBackpackAddSelector);
        bot.click(cartButtonSelector);
        bot.click(checkoutButtonSelector);
        bot.sendKeys(firstNameTextFieldSelector, "FisrtName");
        bot.sendKeys(lastNameTextFieldSelector, "LastName");
        bot.sendKeys(postalCodeTextFieldSelector, "12345");
        bot.click(continueButtonSelector);

        double actualValueDouble = bot.parseStringToDouble(priceTextFieldValueSelector);
        double taxValueDouble = bot.parseStringToDouble(taxTextFieldValueSelector);

        double orderSumDouble = actualValueDouble + taxValueDouble;
        String orderSumString = String.valueOf(orderSumDouble);

        Assertions.assertEquals("32.39", orderSumString,
                "Amount of Order Value + Tax value does not match");

        bot.click(finishButtonSelector);

        Assertions.assertEquals(checkOutCompletedURL, bot.getURL()
                , "Finish page site URL does not match, order not completed");
    }

}
