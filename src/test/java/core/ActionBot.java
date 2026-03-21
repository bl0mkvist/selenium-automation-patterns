package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ActionBot {
    private final WebDriver driver;
    private final String baseURL;
    private WebDriverWait wait;
    protected String firstName = "Antoni";
    protected String lastName = "Kowalski";
    protected String postalCode = "123456";
    static final String standardUser = "standard_user";
    static final String validPassword = "secret_sauce";

    public ActionBot(WebDriver driver, String baseURL) {
        this.driver = driver;
        this.baseURL = baseURL;
    }


    //Methods

    public void validLogin() {
        driver.findElement(By.cssSelector("#user-name"))
                .sendKeys(standardUser);
        driver.findElement(By.cssSelector("#password"))
                .sendKeys(validPassword);
        driver.findElement(By.cssSelector("#login-button"))
                .click();
    }

    public void click(String cssSelector) {
        driver.findElement(By.cssSelector(cssSelector)).click();

    }

    public void waitForPresenceOfElementLocated(String cssSelector) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
    }

    public List<WebElement> getElements(String cssSelector) {
        return driver.findElements(By.cssSelector(cssSelector));
    }

    public String getTextString(String cssSelector) {
        return driver.findElement(By.cssSelector(cssSelector)).getText();
    }


    public int loopClicker(List<WebElement> cssSelector) {
        int i = 0;
        for (WebElement element : cssSelector) {
            i++;
            element.click();
            System.out.println(i + ". : " + "click!");

        }
        return i;

    }

    //two arguments - cssSelector, string
    public void sendKeys(String cssSelector, String string) {
        driver.findElement(By.cssSelector(cssSelector)).sendKeys(string);

    }

    public double parseStringToDouble(String cssSelector) {
        String stringValue = driver.findElement(By.cssSelector(cssSelector)).getText().replaceAll("[^0-9.]", "");
        double doubleValue = Double.parseDouble(stringValue);

        return doubleValue;
    }

    public String getURL() {
        return driver.getCurrentUrl();
    }

    public void login(String userName, String password) {
        driver.get(baseURL);
        driver.findElement(By.cssSelector("#user-name"))
                .sendKeys(userName);
        driver.findElement(By.cssSelector("#password"))
                .sendKeys(password);
        driver.findElement(By.cssSelector("#login-button"))
                .click();
    }

    public void waitForElementToBeClickable(String cssSelector) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
    }


    public int addingProvidedAmountOfItemsToCart(List<WebElement> cssSelector, int amountOfItemsToBeAdded) {
        if (amountOfItemsToBeAdded < 1 || amountOfItemsToBeAdded > cssSelector.size()) {
            throw new IllegalArgumentException(
                    "amountOfItemsToBeAdded value must be between 1 and " + cssSelector.size() + "."
                    + "Provided test amount was : " + amountOfItemsToBeAdded);
        }

        int clicksCount = Math.min(amountOfItemsToBeAdded, cssSelector.size());

        for (int i = 0; i < clicksCount; i++) {
            cssSelector.get(i).click();
        } return clicksCount;
    }


}
