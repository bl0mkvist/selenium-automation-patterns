package bot;

import bot.core.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginTests extends TestBase {
    static final String standardUser = "standard_user";
    static final String lockedOutUser = "locked_out_user";
    static final String validPassword = "secret_sauce";

    private final String errorMessageContainer = ".error-message-container";
    private final String lockedUserErrorMessage = "Epic sadface: Sorry, this user has been locked out.";
    private final String invalidPasswordErrorMessage = "Epic sadface: Username and password do not match any user in this service";
    private final String emptyUsernameErrorMessage = "Epic sadface: Username is required";
    private final String logoutSidePanelSelector = "#react-burger-menu-btn";
    private final String logoutButtonSelector = "#logout_sidebar_link";

    @Test
    @DisplayName("User logs in properly with correct credentials.")
    public void shouldLoginSuccessfullyWithValidCredentials() {
        bot.login(standardUser, validPassword);

        Assertions.assertEquals(
                bot.getURL(),
                baseURL + "/inventory.html",
                "Incorrect URL, user with correct credentials was not logged in");
    }

    @Test
    @DisplayName("Correct error message displayed for user with locked account.")
    public void shouldDenyAccessForLockedOutUser() {
        bot.login(lockedOutUser, validPassword);

        Assertions.assertEquals(
                bot.getTextString(errorMessageContainer),
                lockedUserErrorMessage,
                "Error message not displayed. User might have forbidden access");

    }

    @Test
    @DisplayName("Correct error message displayed for user logging in with incorrect password.")
    public void shouldShowErrorWhenPasswordIsIncorrect() {
        bot.login(standardUser, "wrongPassword");

        Assertions.assertAll(
                () -> Assertions.assertEquals(invalidPasswordErrorMessage, bot.getTextString(errorMessageContainer)),
                () -> Assertions.assertEquals(baseURL + "/", bot.getURL()));

    }


    @Test
    @DisplayName("Correct error displayed when username field is blank")
    public void shouldShowErrorWhenUsernameIsBlank() {
        bot.login("", validPassword);

        Assertions.assertEquals(emptyUsernameErrorMessage, bot.getTextString(errorMessageContainer));

    }


    @Test
    @DisplayName("User is returned back to login screen when logging out from the application")
    public void shouldBeOnLoginPageWhenLoggedOut() {
        bot.login(standardUser, validPassword);
        bot.click(logoutSidePanelSelector);
        bot.waitForElementToBeClickable(logoutButtonSelector);
        bot.click(logoutButtonSelector);

        Assertions.assertEquals(baseURL + "/", bot.getURL(), "User is not transited to login page");

    }


}