package pom.helpers;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public class AllureAttachments {

    public static void addScreenshot(WebDriver driver, String attachmentName) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(attachmentName, "image/png", new ByteArrayInputStream(screenshot), ".png");
        } catch (Exception e) {
            System.out.println("Could not attach screenshot: " + e.getMessage());
        }
    }

    public static void addPageSource(WebDriver driver, String name) {
        try {
            String pageSource = driver.getPageSource();
            Allure.addAttachment(name, "text/html", pageSource, ".html");
        } catch (Exception e) {
            System.out.println("Could not attach page source: " + e.getMessage());
        }
    }
}