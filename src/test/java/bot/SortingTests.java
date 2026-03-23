package bot;

import core.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import java.math.BigDecimal;
import java.util.ArrayList;
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

        bot.waitTextToBePresentInElementLocated(sortingContainerSelectedValue, "Name (A to Z)");
        bot.waitForPresenceOfElementLocated(listProductPrices);

        List<WebElement> headersList = bot.getElements(listProductsHeaders);
        Assertions.assertFalse(
                headersList.isEmpty(),
                 "List is empty, possible issue with loading elements on Products Page");
        List<String> actualHeadersList = new ArrayList<>();

        for (WebElement header : headersList) {
            actualHeadersList.add(header.getText().trim());
        }

        List<String> expectedSortedHeadersList = new ArrayList<>(actualHeadersList);
        expectedSortedHeadersList.sort(String.CASE_INSENSITIVE_ORDER);
        Assertions.assertEquals(expectedSortedHeadersList, actualHeadersList, "Products are not sorted by name A to Z");

    }

    @Test
    @DisplayName("Sorts products by name Z to A")
    void shouldSortProductsAlphabeticallyDescending(){
        bot.validLogin();
        bot.click(sortingContainer);
        bot.click(sortZtoAButton);

        bot.waitTextToBePresentInElementLocated(sortingContainerSelectedValue, "Name (Z to A)");
        bot.waitForPresenceOfElementLocated(listProductPrices);

        List<WebElement> headersList = bot.getElements(listProductsHeaders);
        Assertions.assertFalse(headersList.isEmpty()
                , "List is empty, possible issue with loading elements on Products Page");
        List<String> actualHeadersList = new ArrayList<>();

        for (WebElement header : headersList) {
            actualHeadersList.add(header.getText().trim());
        }

        List<String> expectedSortedHeaderList = new ArrayList<>(actualHeadersList);
        expectedSortedHeaderList.sort(String.CASE_INSENSITIVE_ORDER.reversed());
        Assertions.assertEquals(expectedSortedHeaderList, actualHeadersList, "Products are not sorted by name Z to A");
    }


    @Test
    @DisplayName("Sorts products by price Lowest to Highest")
    void shouldSortProductsByPriceLowToHigh() {
        bot.validLogin();
        bot.click(sortingContainer);
        bot.click(sortPriceLtoHButton);

        bot.waitTextToBePresentInElementLocated(sortingContainerSelectedValue, "Price (low to high)");
        bot.waitForPresenceOfElementLocated(listProductPrices);

        List<WebElement> productPrices = bot.getElements(listProductPrices);
        Assertions.assertFalse(productPrices.isEmpty()
                , "List is empty, possible issue with loading elements on Products Page");

        List<BigDecimal> actualProductPrices = new ArrayList<>();

        for (WebElement price : productPrices) {
            String stringPrice = price.getText().replace("$", "").trim();
            actualProductPrices.add(new BigDecimal((stringPrice)));
        }

        List<BigDecimal> expectedProductPrices = new ArrayList<>(actualProductPrices);
        expectedProductPrices.sort(Comparator.naturalOrder());

        Assertions.assertEquals(expectedProductPrices, actualProductPrices,
                "Prices are not sorted by price ascending");
    }

    @Test
    @DisplayName("Sorts products by price Highest to Lowest")
    void shouldSortProductsByPriceHighToLow() {
    bot.validLogin();
    bot.click(sortingContainer);
    bot.click(sortPriceHtoLButton);

    bot.waitTextToBePresentInElementLocated(sortingContainerSelectedValue,"Price (high to low)");
    bot.waitForPresenceOfElementLocated(listProductPrices);

    List<WebElement> productPrices = bot.getElements(listProductPrices);
    Assertions.assertFalse(productPrices.isEmpty()
                , "List is empty, possible issue with loading elements on Products Page");

    List<BigDecimal> actualProductPrices = new ArrayList<>();

    for (WebElement price : productPrices) {
        String stringPrice = price.getText().replace("$", "").trim();
        actualProductPrices.add(new BigDecimal(stringPrice));
    }

    List<BigDecimal> expectedProductPrices = new ArrayList<>(actualProductPrices);
    expectedProductPrices.sort(Comparator.reverseOrder());

    Assertions.assertEquals(
            expectedProductPrices,
            actualProductPrices,
            "Prices are not sorted by price descending");
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
