package pom.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pom.core.BaseTest;
import pom.pages.HomePage;
import pom.pages.ProductPage;

import java.math.BigDecimal;


public class ProductTests extends BaseTest {
    String windsurfingSlug = "/windsurfing-w-lanzarote-costa-teguise/";

    @Test
    void shouldUpdateCartPricesAfterAddingProduct() {
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
                .openToProductPage(windsurfingSlug);

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
        int quantityNegativeValue = -1;

        ProductPage productPage = new ProductPage(driver)
                .openToProductPage(windsurfingSlug)
                .setRawQuantity(quantityNegativeValue)
                .clickAddToCart();

        Assertions.assertFalse(productPage.isQuantityValid(),
                "Expected quantity field to be invalid for value "
                        + quantityNegativeValue
                        + ", but it was reported as valid.");
    }

}
