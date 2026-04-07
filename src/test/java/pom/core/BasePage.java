package pom.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pom.components.StoreNotice;
import pom.helpers.ConfigurationReader;

import java.math.BigDecimal;
import java.time.Duration;


public abstract class BasePage {
    protected final WebDriver driver;
    protected final String baseURL;
    protected final int waitValueInSeconds;
    protected WebDriverWait wait;
    protected StoreNotice storeNotice;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.baseURL = new ConfigurationReader().getBaseURL();
        this.waitValueInSeconds = new ConfigurationReader().getWait();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitValueInSeconds));
        this.storeNotice = new StoreNotice(driver, waitValueInSeconds);
    }

    protected void clickElement(By cssSelector){
        wait.until(ExpectedConditions.elementToBeClickable(cssSelector));
        driver.findElement(cssSelector).click();
    }

    protected void waitForElementTobeClickable(By cssSelector) {
        wait.until(ExpectedConditions.elementToBeClickable(cssSelector));
    }

    protected void waitForElementTobeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitForElementToDisappear(By cssSelector) {
        wait.until(ExpectedConditions.numberOfElementsToBe(cssSelector, 0));
    }

    protected void waitForElementVisibility(By cssSelector) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cssSelector));
    }

    protected void hoverOverElement(By cssSelector) {
        waitForElementVisibility(cssSelector);
        WebElement elementToHover = driver.findElement(cssSelector);
        new Actions(driver).moveToElement(elementToHover).perform();
    }

    protected String readText(By cssSelector) {
        waitForElementVisibility(cssSelector);
        return driver.findElement(cssSelector).getText();
    }

    protected BigDecimal convertStringToBigDecimal(By cssSelector) {
        String readedString = readText(cssSelector).replaceAll("[^\\d,.-]", "");
        return new BigDecimal(readedString.replace(",", "."));
    }

    protected void clearInputField(By cssSelector) {
        waitForElementVisibility(cssSelector);
        driver.findElement(cssSelector).clear();
    }

    protected void sendKeys(By cssSelector, String value) {
        waitForElementVisibility(cssSelector);
        driver.findElement(cssSelector).sendKeys(value);
    }


}
