package pom.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pom.helpers.WaitUtils;

public class StoreNotice {
    protected final WebDriver driver;
    private final By storeNoticeDismissButton = By.cssSelector(".woocommerce-store-notice__dismiss-link");
    private final By storeNoticePanel = By.cssSelector(".woocommerce-store-notice");
    private final WaitUtils waitUtils;

    public StoreNotice(WebDriver driver, int waitValueInSeconds) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver, waitValueInSeconds);
    }

    public void dismissStoreNotice() {
        if (!driver.findElements(storeNoticeDismissButton).isEmpty()) {
            WebElement element = waitUtils.waitTobeClickable(storeNoticeDismissButton);
            element.click();
            waitUtils.waitToBeInvisible(storeNoticePanel);

        }
    }

}
