package pom.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public WaitUtils(WebDriver driver, int waitInSecs) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitInSecs));
    }

    public WebElement waitTobeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitToBeClickable(WebElement locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForToDisappear(By cssSelector) {
        wait.until(ExpectedConditions.numberOfElementsToBe(cssSelector, 0));
    }

    public void waitForWindowsToBe(int numberOfWindows){
        wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
    }

    public void waitForURLContains(String url) {
        wait.until(ExpectedConditions.urlContains(url));

    }

    public void waitToBeInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));

    }
}
