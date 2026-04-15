package pom.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pom.helpers.WaitUtils;

import java.util.List;

public class StoreNotice {
    protected final WebDriver driver;
    private final By storeNoticeDismissButton = By.cssSelector("p[aria-label='Napis w sklepie'] [role='button']");
    private final By storeNoticePanel = By.cssSelector("p[aria-label='Napis w sklepie']");
    private final WaitUtils waitUtils;

    public StoreNotice(WebDriver driver, int waitValueInSeconds) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver, waitValueInSeconds);
    }

    public void dismissStoreNotice() {
        waitUtils.waitForVisibility(storeNoticePanel);
        waitUtils.waitTobeClickable(storeNoticeDismissButton).click();
        waitUtils.waitToBeInvisible(storeNoticePanel);
    }
}