package pom.core;

import pom.extensions.ScreenshotOnFailureExtension;
import pom.helpers.BrowserFactory;
import pom.helpers.ConfigurationReader;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

@ExtendWith(ScreenshotOnFailureExtension.class)
public class BaseTest {
    protected WebDriver driver;
    protected static ConfigurationReader configuration;

    @BeforeAll
    public static void loadConfiguration() {
        configuration = new ConfigurationReader();
    }

    @BeforeEach
    void setup() {
        BrowserFactory browser = new BrowserFactory();
        driver = browser.createInstance(configuration);
        if (configuration.getHeadless()) {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    @AfterEach
    void quit() {
        driver.quit();
    }
}

