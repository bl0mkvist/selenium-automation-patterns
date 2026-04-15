package pom.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

public class BrowserFactory {
    public WebDriver createInstance(ConfigurationReader configuration) {

        String browser = configuration.getBrowser();

        switch (browser) {
            case "firefox" -> {
                return createFirefoxInstance(configuration);
            }
            case "chrome" -> {
                return createChromeInstance(configuration);
            }
            case "edge" -> {
                return createEdgeInstance(configuration);

            } default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    WebDriver createChromeInstance (ConfigurationReader configuration){
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> chromeSettingsMap = new HashMap<>();
        chromeSettingsMap.put("profile.password_manager_enabled", false);
        chromeSettingsMap.put("credentials_enable_service", false);
        chromeSettingsMap.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", chromeSettingsMap);
        if (configuration.getHeadless()) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }
        return new ChromeDriver(options);
    }

    WebDriver createFirefoxInstance (ConfigurationReader configuration){
        FirefoxOptions options = new FirefoxOptions();
        if (configuration.getHeadless()) {
            options.addArguments("-headless", "--width=1920", "--height=1080");
        }
        return new FirefoxDriver(options);
    }

    WebDriver createEdgeInstance (ConfigurationReader configuration){
        EdgeOptions options = new EdgeOptions();
        if (configuration.getHeadless()) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }
        return new EdgeDriver(options);
    }
}
