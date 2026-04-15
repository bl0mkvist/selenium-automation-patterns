package pom.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pom.core.BaseTest;
import pom.pages.CartPage;
import pom.pages.CategoryPage;
import pom.pages.HomePage;

import java.math.BigDecimal;
import java.util.List;

@Epic("E-commerce")
@Feature("Cart")
@DisplayName("Cart functionality")
public class CartTests extends BaseTest {



    @Test
    @DisplayName("Should display product in cart with correct total cart price")
    void shouldDisplayProductInCartWithCorrectTotalCartPrice() {
        final HomePage homePage = new HomePage(driver);
        homePage.goToHomePage();

        final BigDecimal actualProductPrice = homePage.readWindsurfingProductPrice();

        final CartPage cartPage = homePage.addWindsurfingProductToCart().goToCartPage();

        final BigDecimal expectedCartTotal = cartPage.readTotalCartAmount();

        Assertions.assertEquals(expectedCartTotal
                , actualProductPrice
                , "Total cart amount : " + expectedCartTotal
                        + " does not match actual product price : " + actualProductPrice
        );
    }

    @Test
    @DisplayName("Should recalculate cart value after changing quantity")
    void shouldRecalculateCartValueAfterChangingQuantity() {
        final HomePage homePage = new HomePage(driver);
        final BigDecimal actualProductPrice = homePage.goToHomePage()
                .readWindsurfingProductPrice();

        final int quantity = 5;

        final CartPage cartPage = homePage.addWindsurfingProductToCart()
                .goToCartPage()
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
    @DisplayName("Should recalculate cart value after applying coupon")
    void shouldRecalculateCartValueAfterApplyingCoupon() {
        HomePage homePage = new HomePage(driver);
        homePage.goToHomePage().addWindsurfingProductToCart();

        final int quantity = 10;

        final CartPage cartPage = homePage.goToCartPage()
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
    @DisplayName("Should not apply invalid coupon")
    void shouldNotApplyInvalidCoupon() {
        CartPage cartPage = new HomePage(driver)
                .goToHomePage()
                .addWindsurfingProductToCart()
                .goToCartPage();

        final BigDecimal cartValue = cartPage.readTotalCartAmount();
        final String invalidCoupon = "invalid";

        cartPage.applyCoupon(invalidCoupon);
        String couponErrorMessage = cartPage.readCouponErrorMessage();

        Assertions.assertAll(
                () -> Assertions.assertTrue(cartValue.compareTo(cartPage.readTotalCartAmount()) == 0
                        , "Cart value should not change after applying invalid coupon"),
                () -> Assertions.assertTrue(couponErrorMessage.contains(invalidCoupon)
                        , "Coupon text not present in error message"),
                () -> Assertions.assertTrue(couponErrorMessage.contains("nie istnieje")
                        , "Coupon text message is not correct")
        );
    }


    @Test
    @DisplayName("Should calculate total cart value for all products in category")
    void shouldCalculateTotalCartValueForAllProductsInCategory() {
        CategoryPage categoryPage = new CategoryPage(driver);

        List<BigDecimal> listAllPrices =
                categoryPage.goToWspinaczkaCategory()
                        .readAllCategoryPrices();

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

    @Test
    @DisplayName("Should remove product from cart")
    void shouldRemoveProductFromCart() {
        CartPage cartPage = new HomePage(driver)
                .goToHomePage()
                .addWindsurfingProductToCart()
                .goToCartPage()
                .removeProductFromCart();

        Assertions.assertAll(
                () -> Assertions.assertTrue(cartPage.isCartEmpty()
                        , "Product is still displayed on Cart Page"),
                () -> Assertions.assertTrue(cartPage.isEmptyCartMessageDisplayed()
                        , "Empty cart notification is not displayed")
        );
    }

}
