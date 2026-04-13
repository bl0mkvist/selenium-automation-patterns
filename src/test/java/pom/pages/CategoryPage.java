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
    private final String windsurfingSlug = "/windsurfing/";

    private final By cartButton = By.cssSelector(".cart-contents");
    private final By regularPrices = By.cssSelector(".price > .amount bdi");
    private final By promoPrices = By.cssSelector(".price ins bdi");
    private final By listOfAddToCartButtons = By.cssSelector("#main .add_to_cart_button");
    private final By productsContainer = By.id("main");
    private final By sortingDropdowns = By.cssSelector(".woocommerce-ordering");
    private final By sortByPriceDescendingButton = By.cssSelector("option[value='price-desc']");

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

    public CategoryPage goToWindsurfingCategory() {
        driver.get(baseURL + "/product-category" + windsurfingSlug);
        storeNotice.dismissStoreNotice();
        return this;
    }


    public List<BigDecimal> readAllCategoryPrices() {
        List<BigDecimal> pricesList = new ArrayList<>();

        waitForVisibility(categoryProducts);

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
        waitForVisibility(listOfAddToCartButtons);
        List<WebElement> buttons = driver.findElements(listOfAddToCartButtons);

        for (WebElement element : buttons) {
            waitToBeClickable(element);
            element.click();
            waitForDisappear(blockUIOverlay);
        }
        return this;
    }

    public CartPage goToCart() {
        clickElement(cartButton);
        return new CartPage(driver);
    }

    public WebElement getFirstSortingDropdown() {
        return driver.findElements(sortingDropdowns).get(0);
    }

    public CategoryPage sortProductsByPriceDescending() {
        waitToBeClickable(sortingDropdowns);
        getFirstSortingDropdown().click();
        clickElement(sortByPriceDescendingButton);
        waitUtils.waitForURLContains("orderby=price-desc");
        return this;
    }
}
