package pom.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pom.core.BaseTest;
import pom.pages.HomePage;
import pom.pages.SearchResultsPage;

public class SearchResultsTests extends BaseTest {

    @Test
    void shouldReturnMatchingProductsForSearchQuery() {

        final String searchKeyWord = "yoga";

        SearchResultsPage searchResultsPage = new HomePage(driver)
                .goToHomePage()
                .searchForProduct(searchKeyWord);

        Assertions.assertTrue(searchResultsPage.checkSearchKeyWord(searchKeyWord)
        ,"Products on the search list do not match searched keyword");
    }
}
