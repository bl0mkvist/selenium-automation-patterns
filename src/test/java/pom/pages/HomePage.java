package pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import pom.core.BasePage;

import java.math.BigDecimal;

public class HomePage extends BasePage {
    private final By productCategories = By.cssSelector(".storefront-product-section");
    private final By windsurfingProductPageButton = By.cssSelector(".storefront-recent-products .post-4116");
    private final By addWindsurfingButton = By.cssSelector(".storefront-recent-products [data-product_id='4116']"); // poprawic
    private final By windsurfingActualPrice = By.cssSelector(".storefront-recent-products .post-4116 .amount");
    private final By addWindsurfingLoadingIcon = By.cssSelector(".storefront-recent-products .loading[data-product_id='4116']");
    private final By totalAmountTextField = By.cssSelector(".cart-contents .amount");
    private final By cartButton = By.cssSelector(".site-header-cart");
    private final By searchTextField = By.cssSelector("input.search-field");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage goToHomePage() {
        driver.get(baseURL);
        storeNotice.dismissStoreNoticeIfPresent();
        return this;
    }

    public HomePage addWindsurfingProductToCart() {
        clickElement(addWindsurfingButton);
        waitForDisappear(addWindsurfingLoadingIcon);
        return this;
    }

    public BigDecimal readWindsurfingProductPrice() {
        return convertStringToBigDecimal(windsurfingActualPrice);
    }

    public BigDecimal readTotalCartAmount() {
        return convertStringToBigDecimal(totalAmountTextField);
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

    public ProductPage goToWindsurfingProductPage() {
        clickElement(windsurfingProductPageButton);
        return new ProductPage(driver);
    }

    public CartPage goToCartPage() {
        clickElement(cartButton);
       // storeNotice.dismissStoreNoticeIfPresent();
        return new CartPage(driver);
    }

    public SearchResultsPage searchFor(String searchKeyWord) {
        sendKeys(searchTextField, searchKeyWord);
        driver.findElement(searchTextField).sendKeys(Keys.ENTER);
        waitUtils.waitForURLContains(searchKeyWord);

        return new SearchResultsPage(driver);
    }
}
