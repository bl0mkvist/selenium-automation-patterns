package pom.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pom.core.BaseTest;
import pom.pages.HomePage;
import pom.pages.ProductPage;

import java.math.BigDecimal;


@Epic("E-commerce")
@Feature("Product page")
@DisplayName("Product page functionality")
public class ProductTests extends BaseTest {
    String windsurfingSlug = "/windsurfing-w-lanzarote-costa-teguise/";

    @Test
    @DisplayName("Should update cart prices after adding product")
    void shouldUpdateCartPricesAfterAddingProduct() {
        HomePage homePage = new HomePage(driver);

        ProductPage productPage =
                homePage.goToHomePage()
                        .goToWindsurfingProductPage()
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
    @DisplayName("Should recalculate total price when changing quantity")
    void shouldRecalculateTotalPriceWhenChangingQuantity() throws InterruptedException {
        ProductPage productPage = new ProductPage(driver)
                .openProductPage(windsurfingSlug);

        BigDecimal productPrice = productPage.readProductPrice();

        final int quantity = 10;
        productPage.setQuantity(quantity).addProductToCart();

        BigDecimal expectedTotalPrice = productPrice.multiply(BigDecimal.valueOf(quantity));
        BigDecimal actualTotalPrice = productPage.readTotalCartAmount();

        Assertions.assertEquals(
                0, expectedTotalPrice.compareTo(
                        actualTotalPrice),
                "Calculated amount of total cart price is not correct"
        );
    }

    @Test
    @DisplayName("Should not allow negative product quantity value")
    void shouldNotAllowNegativeProductQuantityValue() {
        int quantityNegativeValue = -1;

        ProductPage productPage = new ProductPage(driver)
                .openProductPage(windsurfingSlug)
                .setRawQuantity(quantityNegativeValue)
                .clickAddToCart();

        Assertions.assertFalse(productPage.isQuantityValid(),
                "Expected quantity field to be invalid for value "
                        + quantityNegativeValue
                        + ", but it was reported as valid.");
    }

}
