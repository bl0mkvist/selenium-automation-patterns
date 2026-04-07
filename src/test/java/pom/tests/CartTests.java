package pom.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pom.core.BaseTest;
import pom.pages.CartPage;
import pom.pages.CategoryPage;
import pom.pages.HomePage;

import java.math.BigDecimal;
import java.util.List;

public class CartTests extends BaseTest {


    @Test
    void shouldDisplayProductInCartWithCorrectTotalCartPrice() {
        final HomePage homePage = new HomePage(driver);
        homePage.goToHomePage();

        final BigDecimal actualProductPrice = homePage.readWindsurfingProductPrice();

        final CartPage cartPage = homePage.addWindsurfingProductToCart().goToCart();

        final BigDecimal expectedCartTotal = cartPage.readTotalCartAmount();

        Assertions.assertEquals(expectedCartTotal
                , actualProductPrice
                , "Total cart amount : " + expectedCartTotal
                        + " does not match actual product price : " + actualProductPrice
        );
    }

    @Test
    void shouldRecalculateCartValueAfterChangingQuantity() {
        final HomePage homePage = new HomePage(driver);
        final BigDecimal actualProductPrice = homePage.goToHomePage()
                .readWindsurfingProductPrice();

        final int quantity = 5;

        final CartPage cartPage = homePage.addWindsurfingProductToCart()
                .goToCart()
                .setQuantity(quantity)
                .updateCart();

        final BigDecimal expectedCalculatedCartTotalValue = actualProductPrice.multiply(BigDecimal.valueOf(quantity));
        final BigDecimal actualCartTotalValue = cartPage.readTotalCartAmount();

        Assertions.assertEquals(expectedCalculatedCartTotalValue,
                actualCartTotalValue,
                "Expected calculated amount : " + expectedCalculatedCartTotalValue
                        + " does not match with actual cart total value : " + actualCartTotalValue);

        Assertions.assertEquals(0, expectedCalculatedCartTotalValue.compareTo(actualCartTotalValue),
                "Expected calculated amount : " + expectedCalculatedCartTotalValue
                        + " does not match with actual cart total value : " + actualCartTotalValue);
    }

    @Test
    void shouldRecalculateCartValueAfterApplyingDiscountCoupon() {
        final HomePage homePage = new HomePage(driver);
        homePage.goToHomePage().addWindsurfingProductToCart();

        final int quantity = 10;

        final CartPage cartPage = homePage.goToCart()
                .setQuantity(quantity)
                .updateCart();

        final BigDecimal cartTotalValueBeforeDiscount = cartPage.readTotalCartAmount();

        final String couponTenPercentDiscount = "10procent";
        cartPage.applyCoupon(couponTenPercentDiscount);

        final BigDecimal actualCartValueWithDiscount = cartPage.readTotalCartAmount();
        final BigDecimal expectedCartValueWithDiscount =
                cartTotalValueBeforeDiscount
                        .multiply(new BigDecimal("0.90"));

        Assertions.assertEquals(0, expectedCartValueWithDiscount.compareTo(actualCartValueWithDiscount)
                , "Expected calculated amount : " + expectedCartValueWithDiscount
                        + " does not match actual cart total value : " + actualCartValueWithDiscount);
    }


    @Test
    void shouldCalculateTotalCartValueForAllProductsInCategory() {
        CategoryPage categoryPage = new CategoryPage(driver);

        List<BigDecimal> listAllPrices = categoryPage.goToWspinaczkaCategory().readAllCategoryPrices();

        BigDecimal expectedCartAmount = BigDecimal.ZERO;

        for (BigDecimal price : listAllPrices) {
            expectedCartAmount = expectedCartAmount.add(price);
        }

        CartPage cartPage = categoryPage.addAllCategoryProductsToCart().goToCart();

        BigDecimal actualCartAmount = cartPage.readTotalCartAmount();

        Assertions.assertEquals(0, expectedCartAmount.compareTo(actualCartAmount)
                , "Expected total cart value : " + expectedCartAmount
                        + " does not match with cart total value : " + actualCartAmount
        );

    }
}
