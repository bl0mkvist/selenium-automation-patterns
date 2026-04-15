package pom.helpers;

import org.openqa.selenium.WebDriver;
import java.util.Set;

public class WindowHelper {
    private final WebDriver driver;
    protected WaitUtils waitUtils;

    public WindowHelper(WebDriver driver, int waitInSeconds) {
       this.driver = driver;
       this.waitUtils = new WaitUtils(driver, waitInSeconds);
    }

    public void switchToNewWindow(String originalWindowHandle) {
        waitUtils.waitForWindowsToBe(2);

        Set<String> allWindowsHandles = driver.getWindowHandles();

        String newWindow = null;

        for (String window : allWindowsHandles) {
            if (!window.equals(originalWindowHandle)) {
                newWindow = window;
                break;
            }
        }

        if (newWindow == null) {
            throw new IllegalStateException("New window was not opened");
        }

        driver.switchTo().window(newWindow);
    }
}
