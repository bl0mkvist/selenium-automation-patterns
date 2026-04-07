package pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pom.core.BasePage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CategoryPage extends BasePage {
    private final String wspinaczkaSlug = "/wspinaczka/";

    private final By cartButton = By.cssSelector(".cart-contents");
    private final By regularPrices = By.cssSelector(".price > .amount bdi");
    private final By promoPrices = By.cssSelector(".price ins bdi");
    private final By listOfAddToCartButtons = By.cssSelector("#main .add_to_cart_button");

    By blockUIOverlay = By.cssSelector(".loading");
    By categoryProducts = By.cssSelector("#main");

    public CategoryPage(WebDriver driver) {
        super(driver);
    }


    public CategoryPage goToWspinaczkaCategory() {
        driver.get(baseURL + "/product-category" + wspinaczkaSlug);
        storeNotice.dismissStoreNotice();
        return this;
    }

    public List<BigDecimal> readAllCategoryPrices() {
        List<BigDecimal> pricesList = new ArrayList<>();

        waitForElementVisibility(categoryProducts);

        List<WebElement> regularPrice = driver.findElements(regularPrices);
        for (WebElement price : regularPrice) {
            String cleaned = price.getText().replaceAll("[^\\d,.-]", "");
            pricesList.add(new BigDecimal(cleaned.replace(",", ".")));
        }

        List<WebElement> promoPrice = driver.findElements(promoPrices);
        for (WebElement price : promoPrice) {
            String cleaned = price.getText().replaceAll("[^\\d,.-]", "");
            pricesList.add(new BigDecimal(cleaned.replace(",", ".")));
        }
        return pricesList;
    }

    public CategoryPage addAllCategoryProductsToCart() {
        waitForElementVisibility(listOfAddToCartButtons);
        List<WebElement> buttons = driver.findElements(listOfAddToCartButtons);

        for (WebElement element : buttons) {
            waitForElementTobeClickable(element);
            element.click();
            waitForElementToDisappear(blockUIOverlay);
        }
        return this;
    }

    public CartPage goToCart() {
        clickElement(cartButton);
        return new CartPage(driver);
    }
}
