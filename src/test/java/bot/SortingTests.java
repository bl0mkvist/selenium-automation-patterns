package bot;

import core.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingTests extends TestBase {

    private final String sortingContainer = ".product_sort_container";
    private final String sortAtoZButton = "option[value='az']";
    private final String sortZtoAButton = "option[value='za']";

    private final String listProductsHeaders = ".inventory_item_name ";

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
}

/*
Sortowanie
1.
Sortowanie A→Z → sprawdź, że nazwy rosną alfabetycznie. (id.scribd.com)
2.
Sortowanie Z→A → sprawdź, że nazwy maleją alfabetycznie. (id.scribd.com)
3.
Sortowanie cena Low→High → ceny rosną. (id.scribd.com)
4.
Sortowanie cena High→Low → ceny maleją. (id.scribd.com)
5.
Zmiana sortowania nie wpływa na zawartość koszyka (produkty w koszyku pozostają).
 */
