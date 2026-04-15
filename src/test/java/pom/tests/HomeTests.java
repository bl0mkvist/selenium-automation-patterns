package pom.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pom.core.BaseTest;
import pom.pages.HomePage;

import java.math.BigDecimal;

@Epic("E-commerce")
@Feature("Home Page")
@DisplayName("Home Page functionality")
public class HomeTests extends BaseTest {

    @Test
    @DisplayName("Should show home page content")
    void shouldShowHomePageContent() {
        HomePage homePage = new HomePage(driver);
        homePage.goToHomePage();

        String expectedPageTitle = "FakeStore – Sklep do nauki testowania";
        String expectedPageURL = configuration.getBaseURL().replaceAll("/+$", "");
        String actualURL = homePage.readCurrentUrl().replaceAll("/+$", "");

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        expectedPageURL
                        , actualURL
                        , "Page address is not correct"
                ),
                () -> Assertions.assertEquals(
                        expectedPageTitle
                        , homePage.readPageTitle()
                        , "Page title is incorrect"
                ),
                () -> Assertions.assertTrue(
                        (homePage.hasProductCategories()),
                        "Categories with products are not displayed")
        );
    }

    @Test
    @DisplayName("Should update cart total amount after adding one product")
    void shouldUpdateCartTotalAfterAddingOneProduct() {
        HomePage homePage = new HomePage(driver);

        homePage.goToHomePage().addWindsurfingProductToCart();

        BigDecimal expectedCartValue = homePage.readWindsurfingProductPrice();

        Assertions.assertEquals(
                0, expectedCartValue
                        .compareTo(homePage.readTotalCartAmount()),
                "Total cart value is not correct"
        );
    }
}
