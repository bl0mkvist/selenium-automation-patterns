package pom.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pom.components.StoreNotice;
import pom.helpers.ConfigurationReader;
import pom.helpers.WaitUtils;
import pom.helpers.WindowHelper;

import java.math.BigDecimal;
import java.time.Duration;


public abstract class BasePage {
    protected final WebDriver driver;
    protected final String baseURL;
    protected int waitValueInSeconds;
    protected StoreNotice storeNotice;
    protected WindowHelper windowHelper;
    protected WaitUtils waitUtils;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.baseURL = new ConfigurationReader().getBaseURL();
        this.waitValueInSeconds = new ConfigurationReader().getWait();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitValueInSeconds));
        this.storeNotice = new StoreNotice(driver, waitValueInSeconds);
        this.windowHelper = new WindowHelper(driver, waitValueInSeconds);
        this.waitUtils = new WaitUtils(driver, waitValueInSeconds);
    }

    protected void goToProductPage(String productSlug) {
        driver.get(baseURL + "/products" + productSlug);
        storeNotice.dismissStoreNotice();
    }

    protected void clickElement(By cssSelector){
        WebElement element = waitUtils.waitTobeClickable(cssSelector);
        element.click();
    }

    protected void hoverOverElement(By cssSelector) {
        WebElement elementToHover = waitUtils.waitForVisibility(cssSelector);
        new Actions(driver).moveToElement(elementToHover).perform();
    }

    protected String readText(By cssSelector) {
       return waitUtils.waitForVisibility(cssSelector).getText();
    }

    protected BigDecimal convertStringToBigDecimal(By cssSelector) {
        waitUtils.waitForVisibility(cssSelector);
        String readedString = readText(cssSelector).replaceAll("[^\\d,.-]", "");
        return new BigDecimal(readedString.replace(",", "."));
    }

    protected void clearInputField(By cssSelector) {
        WebElement element = waitUtils.waitForVisibility(cssSelector);
        element.clear();
    }

    protected void sendKeys(By cssSelector, String value) {
        WebElement element = waitUtils.waitForVisibility(cssSelector);
        element.sendKeys(value);
    }

    public int getListSize(By locator) {
        waitForVisibility(locator);
        return driver.findElements(locator).size();
    }

    //domen waits
    public void waitForToDisappear(By cssSelector) {
        wait.until(ExpectedConditions.numberOfElementsToBe(cssSelector, 0));
    }
    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public WebElement waitToBeClickable(WebElement locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
