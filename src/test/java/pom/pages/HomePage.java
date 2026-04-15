package pom.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import pom.core.BasePage;

import java.math.BigDecimal;

public class HomePage extends BasePage {
    private final By productCategories = By.cssSelector(".storefront-product-section");
    private final By windsurfingLanzaroteProductContainer = By.cssSelector(".storefront-recent-products [href*='windsurfing-w-lanzarote']");
    private final By addWindsurfingButton = By.cssSelector(".storefront-recent-products [data-product_id='4116']");
    private final By windsurfingPrice = By.cssSelector(".storefront-recent-products .post-4116 .amount");
    private final By addWindsurfingLoadingIcon = By.cssSelector(".storefront-recent-products .loading[data-product_id='4116']");
    private final By cartTotalAmount = By.cssSelector(".cart-contents .amount");
    private final By cartHeaderButton = By.cssSelector("#site-header-cart");
    private final By searchTextField = By.cssSelector("input.search-field");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Go to home page")
    public HomePage goToHomePage() {
        driver.get(baseURL);
        storeNotice.dismissStoreNotice();
        return this;
    }

    @Step("Add Windsurfing product to cart")
    public HomePage addWindsurfingProductToCart() {
        clickElement(addWindsurfingButton);
        waitForDisappear(addWindsurfingLoadingIcon);
        return this;
    }

    public BigDecimal readWindsurfingProductPrice() {
        return convertStringToBigDecimal(windsurfingPrice);
    }

    public BigDecimal readTotalCartAmount() {
        return convertStringToBigDecimal(cartTotalAmount);
    }

    public String readCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String readPageTitle() {
        return driver.getTitle();
    }

    public boolean hasProductCategories() {
        waitForVisibility(productCategories);
        return !driver.findElements(productCategories).isEmpty();
    }

    @Step("Go to Windsurfing product page")
    public ProductPage goToWindsurfingProductPage() {
        clickElement(windsurfingLanzaroteProductContainer);
        return new ProductPage(driver);
    }

    @Step("Go to cart page")
    public CartPage goToCartPage() {
        clickElement(cartHeaderButton);
        return new CartPage(driver);
    }

    @Step("Search for key word {searchKeyWord}")
    public SearchResultsPage searchFor(String searchKeyWord) {
        sendKeys(searchTextField, searchKeyWord);
        driver.findElement(searchTextField).sendKeys(Keys.ENTER);
        waitUtils.waitForURLContains(searchKeyWord);

        return new SearchResultsPage(driver);
    }
}
