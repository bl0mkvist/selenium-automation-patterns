package pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public ProductPage go(String productSlug) {
        driver.get(baseURL + "/product/" + productSlug);
        storeNotice.dismissStoreNotice();
        return this;
    }

    public ProductPage addProductToCart() {
        waitForElementTobeClickable(addToCart);
        clickElement(addToCart);
        waitForElementVisibility(addToCartConfirmationWidget);
        return this;
    }

    public BigDecimal readProductPrice() {
        waitForElementVisibility(productPrice);
        return convertStringToBigDecimal(productPrice);
    }

    public BigDecimal readTotalCartAmount() {
        waitForElementVisibility(cartPriceTotal);
        return convertStringToBigDecimal(cartPriceTotal);
    }

    public BigDecimal readTotalCartAmountOnDropdown() {
        waitForElementVisibility(cartHeader);
        hoverOverElement(cartHeader);
        return convertStringToBigDecimal(cartTotalPriceOnDropdown);
    }

    public ProductPage setQuantity(int productsNumber) {
        waitForElementVisibility(quantityInputField);
        clearInputField(quantityInputField);

        int quantityMax = Integer.parseInt(driver.findElement(productMaxQuantity)
                .getText()
                .replaceAll("[^\\d,.-]", ""));

        if (productsNumber < 0 || productsNumber > quantityMax) {
            throw new IllegalArgumentException(
                    "Incorrect number provided. Number of products must be between 1 and " + quantityMax
                            + ". Number provided : " + productsNumber);
        }

        sendKeys(quantityInputField, String.valueOf(productsNumber));
        waitForElementToDisappear(blockUIOverlay);
        return this;
    }
}
