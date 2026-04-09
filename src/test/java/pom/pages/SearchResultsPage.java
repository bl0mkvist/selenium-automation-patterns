package pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pom.core.BasePage;

import java.util.List;

public class SearchResultsPage extends BasePage {


    By listOfAllProductTitles = By.cssSelector(".columns-3 h2");
    By productsContainer = By.cssSelector(".products");


    protected SearchResultsPage(WebDriver driver) {
        super(driver);
    }


    public boolean checkSearchKeyWord(String searchKeyWord) {
        waitForElementVisibility(productsContainer);

        List<WebElement> productsTitles = driver.findElements(listOfAllProductTitles);

        return productsTitles.stream()
                .map(WebElement::getText)
                .map(String::toLowerCase)
                .map(s->s.replace("ą", "a"))
                .map(s->s.replace("ę", "e"))
                .allMatch(s->s.contains(searchKeyWord));
    }

}
