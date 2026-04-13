package pom.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pom.core.BaseTest;
import pom.pages.CategoryPage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CategoryTests extends BaseTest {

    @Test
    @DisplayName("Should sort products by price descending")
    void shouldSortProductsByPriceDescending() {

        CategoryPage categoryPage = new CategoryPage(driver)
                .goToWindsurfingCategory()
                .sortProductsByPriceDescending();

        List<BigDecimal> actualPriceList = categoryPage.readAllCategoryPrices();
        List<BigDecimal> expectedPriceList = new ArrayList<>(actualPriceList);

        expectedPriceList.sort(Comparator.reverseOrder());

        Assertions.assertEquals(expectedPriceList
                , actualPriceList
                , "Prices are not sorted in descending order"
        );
    }

}
