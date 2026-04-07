package pom.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class StoreNotice {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private final By storeNoticeDismissButton = By.cssSelector(".woocommerce-store-notice__dismiss-link");
    private final By storeNoticePanel = By.cssSelector(".woocommerce-store-notice");

    public StoreNotice(WebDriver driver, int waitValueInSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitValueInSeconds));
    }

    public void dismissStoreNotice() {
        if (!driver.findElements(storeNoticeDismissButton).isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(storeNoticeDismissButton));
            driver.findElement(storeNoticeDismissButton).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(storeNoticePanel));
        }
    }

}
