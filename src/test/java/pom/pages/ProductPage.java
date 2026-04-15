package pom.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import pom.core.BasePage;

import java.math.BigDecimal;

public class ProductPage extends BasePage {
    private final By addToCartButton = By.cssSelector("[name='add-to-cart']");
    private final By addToCartConfirmationWidget = By.cssSelector(".woocommerce-message[role='alert']");
    private final By productPrice = By.cssSelector(".summary .price .amount");
    private final By cartPriceTotal = By.cssSelector("#site-header-cart .cart-contents .amount");
    private final By cartHeader = By.cssSelector("#site-header-cart");
    private final By cartTotalPriceOnDropdown = By.cssSelector("#site-header-cart .total .amount");
    private final By quantityInputField = By.cssSelector("input[name='quantity']");
    private final By blockUIOverlay = By.cssSelector(".blockOverlay");
    private final By productMaxQuantity = By.cssSelector(".summary .stock");
    private final By addToWishlistButton = By.cssSelector(".add_to_wishlist");
    private final By wishListButton = By.cssSelector("#menu-menu [href*='/wishlist/']");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @Step("Go to product page")
    public ProductPage openProductPage(String productSlug) {
        goToProductPage(productSlug);
        return this;
    }

    @Step("Add product to cart")
    public ProductPage addProductToCart() {
        clickElement(addToCartButton);
        waitForVisibility(addToCartConfirmationWidget);
        return this;
    }

    public ProductPage clickAddToCart() {
        clickElement(addToCartButton);
        return this;
    }

    public BigDecimal readProductPrice() {
        return convertStringToBigDecimal(productPrice);
    }

    public BigDecimal readTotalCartAmount() {
        return convertStringToBigDecimal(cartPriceTotal);
    }

    public BigDecimal readTotalCartAmountOnDropdown() {
        hoverOver(cartHeader);
        return convertStringToBigDecimal(cartTotalPriceOnDropdown);
    }

    @Step("Set quantity to {productsAmount}")
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
        waitForDisappear(blockUIOverlay);
        return this;
    }

    @Step("Set quantity to {productsAmount}")
    public ProductPage setRawQuantity(int productsAmount) {
        clearInputField(quantityInputField);
        sendKeys(quantityInputField, String.valueOf(productsAmount));
        waitForDisappear(blockUIOverlay);
        return this;
    }

    public boolean isQuantityValid() {
        WebElement quantityField = driver.findElement(quantityInputField);

        boolean isValid = (Boolean) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].checkValidity();", quantityField);
        return isValid;
    }

    @Step("Add product to wishlist")
    public ProductPage addProductToWishlist() {
        clickElement(addToWishlistButton);
        waitForDisappear(blockUIOverlay);
        return this;
    }

    @Step("Go to wishlist page")
    public WishlistPage goToWishlistPage() {
        String originalWindow = driver.getWindowHandle();
        clickElement(wishListButton);

        windowHelper.switchToNewWindow(originalWindow);
        return new WishlistPage(driver);
    }
}
