package core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;

public class TestBase {
    protected WebDriver driver;
    private WebDriverWait wait;
    protected ActionBot bot;

    protected final String baseURL = "https://www.saucedemo.com";
    protected final String username = "standard_user";
    protected final String password = "secret_sauce";


    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
        bot = new ActionBot(driver, baseURL);
        driver.get(baseURL);

        driver.findElement(By.cssSelector("#user-name"))
                .sendKeys(username);
        driver.findElement(By.cssSelector("#password"))
                .sendKeys(password);
        driver.findElement(By.cssSelector("#login-button"))
                .click();
    }

    @AfterEach
    public void quit() {
        driver.quit();
    }


}
