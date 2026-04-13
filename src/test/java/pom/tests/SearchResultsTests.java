package pom.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pom.core.BaseTest;
import pom.pages.HomePage;
import pom.pages.SearchResultsPage;

public class SearchResultsTests extends BaseTest {

    @Test
    @DisplayName("Should return matching products for search query")
    void shouldReturnMatchingProductsForSearchQuery() {
        final String searchKeyWord = "yoga";

        SearchResultsPage searchResultsPage = new HomePage(driver)
                .goToHomePage()
                .searchFor(searchKeyWord);

        Assertions.assertTrue(searchResultsPage.containsResultsMatchingKeyword(searchKeyWord)
        ,"Products on the search list do not match searched keyword");
    }
}
