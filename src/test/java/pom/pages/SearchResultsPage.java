package pom.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pom.core.BasePage;

import java.util.List;

public class SearchResultsPage extends BasePage {


    By listOfAllProductTitles = By.cssSelector(".product h2");
    By productsContainer = By.cssSelector(".products");


    protected SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Check if search results contains keyword")
    public boolean containsResultsMatchingKeyword(String searchKeyWord) {
        waitForVisibility(productsContainer);

        List<WebElement> productsTitles = driver.findElements(listOfAllProductTitles);

        return productsTitles.stream()
                .map(WebElement::getText)
                .map(String::toLowerCase)
                .map(s->s.replace("ą", "a"))
                .map(s->s.replace("ę", "e"))
                .allMatch(s->s.contains(searchKeyWord));
    }
}
