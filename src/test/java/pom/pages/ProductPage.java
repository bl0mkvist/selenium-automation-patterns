package pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import pom.core.BasePage;

import java.math.BigDecimal;

public class ProductPage extends BasePage {
    private final By addToCart = By.cssSelector(".cart>button");
    private final By addToCartConfirmationWidget = By.cssSelector(".woocommerce-message[role='alert']");
    private final By productPrice = By.cssSelector(".summary >.price>.amount");
    private final By cartPriceTotal = By.cssSelector("#site-header-cart .cart-contents .amount");
    private final By cartHeader = By.cssSelector("#site-header-cart");
    private final By cartTotalPriceOnDropdown = By.cssSelector("#site-header-cart .total .amount");
    private final By quantityInputField = By.cssSelector("input[name='quantity']");
    private final By blockUIOverlay = By.cssSelector(".blockOverlay");
    private final By productMaxQuantity = By.cssSelector(".summary .stock");
    private final By addToWishlistButton = By.cssSelector(".add_to_wishlist");
    private final By wishListButton = By.cssSelector("#menu-item-248");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public ProductPage openToProductPage(String productSlug) {
        this.goToProductPage(productSlug);
        return this;
    }

    public ProductPage addProductToCart() {
        clickElement(addToCart);
        waitUtils.waitForVisibility(addToCartConfirmationWidget);
        return this;
    }

    public ProductPage clickAddToCart() {
        clickElement(addToCart);
        return this;
    }

    public BigDecimal readProductPrice() {
        return convertStringToBigDecimal(productPrice);
    }

    public BigDecimal readTotalCartAmount() {
        return convertStringToBigDecimal(cartPriceTotal);
    }

    public BigDecimal readTotalCartAmountOnDropdown() {
        hoverOverElement(cartHeader);
        return convertStringToBigDecimal(cartTotalPriceOnDropdown);
    }

    public ProductPage setQuantity(int productsAmount) {
        clearInputField(quantityInputField);

        int quantityMax = Integer.parseInt(driver.findElement(productMaxQuantity)
                .getText()
                .replaceAll("[^\\d,.-]", ""));

        if (productsAmount < 0 || productsAmount > quantityMax) {
            throw new IllegalArgumentException(
                    "Incorrect number provided. Number of products must be between 1 and " + quantityMax
                            + ". Number provided : " + productsAmount);
        }

        sendKeys(quantityInputField, String.valueOf(productsAmount));
        waitForToDisappear(blockUIOverlay);
        return this;
    }

    public ProductPage setRawQuantity(int productsAmount) {
        clearInputField(quantityInputField);
        sendKeys(quantityInputField, String.valueOf(productsAmount));
        waitForToDisappear(blockUIOverlay);
        return this;
    }

    public boolean isQuantityValid() {
        WebElement quantityField = driver.findElement(quantityInputField);

        boolean isValid = (Boolean) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].checkValidity();", quantityField);
        return isValid;
    }

    public ProductPage addProductToWishList() {
        clickElement(addToWishlistButton);
        waitForToDisappear(blockUIOverlay);
        return this;
    }

    public WishlistPage goToWishlistPage() {
        String originalWindow = driver.getWindowHandle();
        clickElement(wishListButton);

        windowHelper.switchToNewWindow(originalWindow);
        return new WishlistPage(driver);
    }
}
