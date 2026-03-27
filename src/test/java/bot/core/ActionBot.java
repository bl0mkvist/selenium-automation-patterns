package bot.core;

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
    protected final String standardUser = "standard_user";
    protected final String validPassword = "secret_sauce";

    public ActionBot(WebDriver driver, String baseURL) {
        this.driver = driver;
        this.baseURL = baseURL;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    //BOTMethods
    //ElementsMethods
    public WebElement getElement(String cssSelector) {
        return driver.findElement(By.cssSelector(cssSelector));
    }
    public List<WebElement> getElements(String cssSelector) {
        return driver.findElements(By.cssSelector(cssSelector));
    }
    public String getURL() {
        return driver.getCurrentUrl();
    }
    public String getTextString(String cssSelector) {
        return driver.findElement(By.cssSelector(cssSelector)).getText();
    }
    public void sendKeys(String cssSelector, String string) {
        driver.findElement(By.cssSelector(cssSelector)).sendKeys(string);

    }

    //Clicks
    public void click(String cssSelector) {
        waitForElementToBeClickable(cssSelector);
        driver.findElement(By.cssSelector(cssSelector)).click();

    }

    public int clickAllElementsReturnNumberOfClicks(List<WebElement> cssSelector) {
        int i = 0;
        for (WebElement element : cssSelector) {
            waitForElementToBeClickable(element);
            i++;
            element.click();

        }
        return i;
    }

    //ExplicitWaits
    public void waitForPresenceOfElementLocated(String cssSelector) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
    }

    public void waitForDomAtribute(String cssSelector, String attribute, String value) {
        wait.until(ExpectedConditions.domAttributeToBe(driver.findElement(By.cssSelector(cssSelector)), attribute, value));
    }

    public void waitTextToBePresentInElementLocated(String cssSelector,String textValue) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(cssSelector),textValue));
    }
    public void waitForElementToBeClickable(String cssSelector) {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
    }

    public void waitForElementToBeClickable(WebElement webElement) {
        wait.until(ExpectedConditions.elementToBeClickable((webElement)));
    }

    //Parsers
    public double parseStringToDouble(String cssSelector) {
        String stringValue = driver.findElement(By.cssSelector(cssSelector)).getText().replaceAll("[^0-9.]", "");

        return Double.parseDouble(stringValue);
    }

    //Logins
    public void validLogin() {
        driver.findElement(By.cssSelector("#user-name"))
                .sendKeys(standardUser);
        driver.findElement(By.cssSelector("#password"))
                .sendKeys(validPassword);
        driver.findElement(By.cssSelector("#login-button"))
                .click();
    }

    public void login(String userName, String password) {
        driver.get(baseURL);
        waitForPresenceOfElementLocated("#user-name");
        driver.findElement(By.cssSelector("#user-name"))
                .sendKeys(userName);
        waitForPresenceOfElementLocated("#password");
        driver.findElement(By.cssSelector("#password"))
                .sendKeys(password);
        waitForPresenceOfElementLocated("#login-button");
        driver.findElement(By.cssSelector("#login-button"))
                .click();
    }

    //TestMethods

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
