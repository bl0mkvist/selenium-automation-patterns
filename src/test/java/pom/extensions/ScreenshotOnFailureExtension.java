package pom.extensions;

import pom.core.BaseTest;
import pom.helpers.AllureAttachments;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;


public class ScreenshotOnFailureExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        boolean testFailed = context.getExecutionException().isPresent();

        if (testFailed) {
            System.out.println("TEST FAILED: " + context.getDisplayName());

            Object testInstance = context.getRequiredTestInstance();
            BaseTest baseTest = (BaseTest) testInstance;
            WebDriver driver = baseTest.getDriver();

            if (driver != null) {
                AllureAttachments.addScreenshot(driver, "Screenshot - " + context.getDisplayName());
                AllureAttachments.addPageSource(driver, "Page Source - " + context.getDisplayName());
                System.out.println("Screenshot attached to Allure report");
            } else {
                System.out.println("Driver is null");
            }
        }
    }
}