package pom.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pom.components.StoreNotice;
import pom.helpers.ConfigurationReader;
import pom.helpers.WaitUtils;
import pom.helpers.WindowHelper;
import java.math.BigDecimal;


public abstract class BasePage {
    protected final WebDriver driver;
    protected final String baseURL;
    protected int waitValueInSeconds;
    protected StoreNotice storeNotice;
    protected WindowHelper windowHelper;
    protected WaitUtils waitUtils;


    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.baseURL = new ConfigurationReader().getBaseURL();
        this.waitValueInSeconds = new ConfigurationReader().getWait();
        this.storeNotice = new StoreNotice(driver, waitValueInSeconds);
        this.windowHelper = new WindowHelper(driver, waitValueInSeconds);
        this.waitUtils = new WaitUtils(driver, waitValueInSeconds);
    }

    protected void goToProductPage(String productSlug) {
        driver.get(baseURL + "/products" + productSlug);
        storeNotice.dismissStoreNoticeIfPresent();
    }

    protected void clickElement(By locator) {
        WebElement element = waitUtils.waitTobeClickable(locator);
        element.click();
    }

    protected void hoverOver(By locator) {
        WebElement elementToHover = waitUtils.waitForVisibility(locator);
        new Actions(driver).moveToElement(elementToHover).perform();
    }

    protected String readText(By locator) {
        return waitUtils.waitForVisibility(locator).getText();
    }

    protected BigDecimal convertStringToBigDecimal(By locator) {
        waitUtils.waitForVisibility(locator);
        String readString = readText(locator).replaceAll("[^\\d,.-]", "");
        return new BigDecimal(readString.replace(",", "."));
    }

    protected void clearInputField(By locator) {
        WebElement element = waitUtils.waitForVisibility(locator);
        element.clear();
    }

    protected void sendKeys(By locator, String value) {
        WebElement element = waitUtils.waitForVisibility(locator);
        element.sendKeys(value);
    }

    protected int getListSize(By locator) {
        waitForVisibility(locator);
        return driver.findElements(locator).size();
    }

    //domen waits
    protected void waitForDisappear(By locator) {
        waitUtils.waitToDisappear(locator);
    }

    protected WebElement waitForVisibility(By locator) {
        return waitUtils.waitForVisibility(locator);
    }

    protected WebElement waitToBeClickable(WebElement locator) {
        return waitUtils.waitToBeClickable(locator);
    }

    protected WebElement waitToBeClickable(By locator) {
       return waitUtils.waitTobeClickable(locator);
    }
}
