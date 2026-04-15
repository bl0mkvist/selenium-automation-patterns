package pom.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pom.helpers.WaitUtils;
import org.openqa.selenium.WebDriver;


public class StripeWidget {
    protected final WebDriver driver;
    private final WaitUtils waitUtils;
    private final By stripeDevToolsMainIframe = By.cssSelector("iframe[name^='__privateStripeFrame'][title='Ramka narzędzi programisty Stripe']");
    private final By stripeDevToolsButton = By.cssSelector("button[aria-label='Otwórz narzędzia dewelopera Stripe']");
    private final By stripeDevToolsSettingsButton = By.cssSelector("button[aria-controls='tabpanel-settings']");
    private final By stripeDevToolsTopLeftCornerButton = By.cssSelector("#tabpanel-settings [role='radiogroup']>:nth-child(1)");
    private final By stripeDevToolsHideButton = By.cssSelector("button[aria-label='Zamknij narzędzia dewelopera Stripe']");
    private final By stripeAnimationLoader = By.cssSelector(".is-entering");
    private final By stripeTopLeftPositionLocator = By.cssSelector("[class*='--topLeft']");
    private final By stripeBottomRightPositionLocator = By.cssSelector("[class*='bottomRight']");

    public StripeWidget(WebDriver driver, int waitInSeconds) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver, waitInSeconds);
    }
    // Handles Stripe dev tools overlay (iframe + animations) that blocks UI interactions in tests
    public void handleStripeWidgetOverlay() {
        WebElement mainIframe = waitUtils.waitForVisibility(stripeDevToolsMainIframe);
        try {
            driver.switchTo().frame(mainIframe);
            waitUtils.waitToDisappear(stripeAnimationLoader);

            waitUtils.waitTobeClickable(stripeDevToolsButton).click();

            waitUtils.waitToDisappear(stripeAnimationLoader);

            waitUtils.waitToAppear(stripeBottomRightPositionLocator);

            waitUtils.waitTobeClickable(stripeDevToolsSettingsButton).click();

            waitUtils.waitTobeClickable(stripeDevToolsTopLeftCornerButton).click();

            waitUtils.waitToAppear(stripeTopLeftPositionLocator);

            waitUtils.waitToDisappear(stripeAnimationLoader);
        } finally {
            driver.switchTo().defaultContent();
        }
    }

}
