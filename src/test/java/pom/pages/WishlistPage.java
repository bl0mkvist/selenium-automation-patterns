package pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pom.core.BasePage;

public class WishlistPage extends BasePage {

    By wishlistProductList = By.cssSelector(".wishlist-items-wrapper tr");


    protected WishlistPage(WebDriver driver) {
        super(driver);
    }


    public int getNumberOfProducts() {
        waitForElementVisibility(wishlistProductList);
        return driver.findElements(wishlistProductList).size();
    }
}
