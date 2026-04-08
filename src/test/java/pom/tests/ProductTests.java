package pom.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pom.core.BaseTest;
import pom.pages.HomePage;
import pom.pages.ProductPage;

import java.math.BigDecimal;


public class ProductTests extends BaseTest {
    String windsurfingSlug = "/windsurfing-w-lanzarote-costa-teguise/";

    private final By quantityInputField = By.cssSelector("input[name='quantity']");
    private final String quantityInput =  "input[name='quantity']";

    @Test
    void shouldUpdateCartPricesAfterAddingProduct_updated() {
        HomePage homePage = new HomePage(driver);

        ProductPage productPage =
                homePage.goToHomePage()
                        .goToWindsurfingPage()
                        .addProductToCart();

        BigDecimal expectedProductPrice = productPage.readProductPrice();

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        0, expectedProductPrice
                                .compareTo(productPage.readTotalCartAmount())
                        , "Value of the product in Cart is not correct"
                ),
                () -> Assertions.assertEquals(
                        0, expectedProductPrice
                                .compareTo(productPage.readTotalCartAmountOnDropdown()),
                        "Value of the product on Cart dropdown is not correct"
                )
        );
    }

    @Test
    void shouldRecalculateTotalPriceWhenChangingQuantity() {
        ProductPage productPage = new ProductPage(driver)
                .go(windsurfingSlug);

        BigDecimal actualProductPrice = productPage.readProductPrice();

        final int quantity = 10;
        productPage.setQuantity(quantity).addProductToCart();

        BigDecimal expectedTotalPrice = actualProductPrice.multiply(BigDecimal.valueOf(quantity));

        Assertions.assertEquals(
                0, expectedTotalPrice.compareTo(
                        productPage.readTotalCartAmount()),
                "Calculated amount of total cart price is not correct"
        );
    }

    @Test
    void shouldNotAllowNegativeProductQuantityValue() {
        ProductPage productPage = new ProductPage(driver)
                .go(windsurfingSlug)
                .setRawQuantity(-1)
                .clickAddToCart();

                Assertions.assertFalse(productPage.isQuantityValid(),
                        "Error validation was not triggered on Quantity field");
    }

}
