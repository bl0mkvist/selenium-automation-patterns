package pom.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class WindowHelper {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public WindowHelper(WebDriver driver, int waitInSeconds) {
       this.driver = driver;
       this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitInSeconds));
    }


    public void switchToNewWindow(String originalWindowHandle) {
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

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
