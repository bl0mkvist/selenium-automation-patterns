package pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pom.core.BasePage;

import java.math.BigDecimal;

public class CartPage extends BasePage  {

    By totalCartValue = By.cssSelector("td[data-title='Suma'] strong");
    By quantityInputField = By.cssSelector(".quantity .input-text");
    By updateCartButton = By.cssSelector(".actions button[name='update_cart']");
    By blockUWrapper = By.cssSelector(".blockUI");
    By couponInputField = By.cssSelector("#coupon_code");
    By couponApplyButton = By.cssSelector(".coupon button");
    By removeProductButton = By.cssSelector(".product-remove a[role='button']");
    By productInCartList = By.cssSelector(".cart_item");
    By emptyCartNotification = By.cssSelector(".woocommerce-notices-wrapper .cart-empty");
    By couponErrorText = By.cssSelector(".coupon-error-notice");


    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isEmptyCartNotificationDisplayed() {
        waitForElementVisibility(emptyCartNotification);
        return driver.findElement(emptyCartNotification).isDisplayed();
    }

    public boolean isCartEmpty() {
        return driver.findElements(productInCartList).isEmpty();
    }

    public BigDecimal readTotalCartAmount() {
        waitForElementVisibility(totalCartValue);
        return convertStringToBigDecimal(totalCartValue);
    }


    public CartPage setQuantity(int quantity) {
        waitForElementVisibility(quantityInputField);
        clearInputField(quantityInputField);
        sendKeys(quantityInputField, String.valueOf(quantity));
        return this;
    }

    public CartPage updateCart() {
        clickElement(updateCartButton);
        waitForElementToDisappear(blockUWrapper);
        return this;
    }

    public CartPage applyCoupon(String couponCode) {
        waitForElementVisibility(couponInputField);
        sendKeys(couponInputField, couponCode);
        clickElement(couponApplyButton);
        waitForElementToDisappear(blockUWrapper);
        return this;
    }

    public CartPage removeProductFromCart() {
        clickElement(removeProductButton);
        waitForElementToDisappear(blockUWrapper);
        return this;
    }

    public String readCouponErrorMessage() {
        waitForElementVisibility(couponErrorText);
        return driver.findElement(couponErrorText).getText();
    }
}
