package bot;

import core.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortingTests extends TestBase {

    private final String sortingContainer = ".product_sort_container";
    private final String sortingContainerSelectedValue = ".product_sort_container option:checked";
    private final String sortAtoZButton = "select>option[value='az']";
    private final String sortZtoAButton = "select>option[value='za']";
    private final String sortPriceLtoHButton = "select>option[value='lohi']";
    private final String sortPriceHtoLButton = "select>option[value='hilo']";
    private final String listProductsHeaders = ".inventory_item_name ";
    private final String listProductPrices = ".inventory_item_price";

    @Test
    @DisplayName("Sorts products by name A to Z")
    void shouldSortProductsAlphabetically() {

        bot.validLogin();
        bot.click(sortingContainer);
        bot.click(sortAtoZButton);

        List<WebElement> headersList = bot.getElements(listProductsHeaders);
        List<String> actualHeadersList = new ArrayList<>();

        for (WebElement header : headersList){
            actualHeadersList.add(header.getText().trim());
        }

        List<String> expectedSortedHeadersList = new ArrayList<>(actualHeadersList);
        Collections.sort(expectedSortedHeadersList, String.CASE_INSENSITIVE_ORDER);

        Assertions.assertEquals(expectedSortedHeadersList, actualHeadersList, "Products are not sorted by name A to Z");

    }

    @Test
    @DisplayName("Sorts products by name Z to A")
    void shouldSortProductsAlphabeticallyDescending(){
        bot.validLogin();
        bot.click(sortingContainer);
        bot.click(sortZtoAButton);

        List<WebElement> headersList = bot.getElements(listProductsHeaders);
        List<String> actualHeadersList = new ArrayList<>();

        for (WebElement header : headersList) {
            actualHeadersList.add(header.getText().trim());
        }

        List<String> expectedSortedHeaderList = new ArrayList<>(actualHeadersList);
        Collections.sort(expectedSortedHeaderList, String.CASE_INSENSITIVE_ORDER.reversed());

        Assertions.assertEquals(expectedSortedHeaderList, actualHeadersList, "Products are not sorted by name Z to A");
    }


    @Test
    @DisplayName("Sorts products by price Lowest to Highest")
    void shouldSortProductsByPriceLowToHigh(){
        bot.validLogin();
        bot.click(sortingContainer);
        bot.click(sortPriceLtoHButton);
        bot.waitTextToBePresentInElementLocated(sortingContainerSelectedValue, "Price (low to high)");

        String selectedOption = bot.getTextString(sortingContainerSelectedValue).trim();
        Assertions.assertEquals("Price (low to high)", selectedOption
                , "Sorting method does not change");

        List<WebElement> productPrices = bot.getElements(listProductPrices);
        Assertions.assertFalse(productPrices.isEmpty()
                , "List is empty, possible issue with loading elements on Products Page");

        List<BigDecimal> actualBigDecimalProductPrices = new ArrayList<>();

        for (WebElement price : productPrices) {
            String stringPrice = price.getText().replace("$", "").trim();
            actualBigDecimalProductPrices.add(new BigDecimal((stringPrice)));
        }

        List<BigDecimal> expectedProductPrices = new ArrayList<>(actualBigDecimalProductPrices);
        expectedProductPrices.sort(Comparator.naturalOrder());

        Assertions.assertEquals(expectedProductPrices, actualBigDecimalProductPrices,
                "Prices are not sorted by price ascending");
    }
}

/*
Sortowanie
1.
Sortowanie A→Z → sprawdź, że nazwy rosną alfabetycznie. (id.scribd.com) - done
2.
Sortowanie Z→A → sprawdź, że nazwy maleją alfabetycznie. (id.scribd.com) - done
3.
Sortowanie cena Low→High → ceny rosną. (id.scribd.com)
4.
Sortowanie cena High→Low → ceny maleją. (id.scribd.com)
5.
Zmiana sortowania nie wpływa na zawartość koszyka (produkty w koszyku pozostają).
 */
