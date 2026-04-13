package pom.tests;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pom.core.BaseTest;
import pom.pages.ProductPage;
import pom.pages.WishlistPage;

public class WishlistTests extends BaseTest {
    String granKoscielcowProduct = "/gran-koscielcow/"; //to move ProductsSlugs

    @Test
    @DisplayName("Should display product in wishlist")
    void shouldDisplayProductInWishlist() {
        WishlistPage wishlistPage = new ProductPage(driver)
                .openProductPage(granKoscielcowProduct)
                .addProductToWishlist()
                .goToWishlistPage();

        final int actualNumberOfProducts = wishlistPage.getNumberOfProducts();
        final int expectedNumberOfProducts = 1;
        Assertions.assertEquals(
                expectedNumberOfProducts
                , actualNumberOfProducts
                , "Amount of products does not match, expected : " + expectedNumberOfProducts
                + " Actual : " + actualNumberOfProducts
        );
    }
}
