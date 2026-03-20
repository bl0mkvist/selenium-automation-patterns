package bot;

import core.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginTests extends TestBase {
    private String standardUser = "standard_user";
    private String lockedOutUser = "locked_out_user";
    private String validPassword = "secret_sauce";

    private String errorMessageContainer = ".error-message-container";
    private String lockedUserErrorMessage = "Epic sadface: Sorry, this user has been locked out.";
    private String invalidPasswordErrorMessage = "Epic sadface: Username and password do not match any user in this service";

    @Test
    @DisplayName("User logs in properly with correct credentials.")
    public void verify_correct_login() {
        bot.login(standardUser, validPassword);

        Assertions.assertEquals(bot.getURL(), baseURL + "/inventory.html",
                "Incorrect URL, user with correct credentials was not logged in");
    }

    @Test
    @DisplayName("Correct error message displayed for user with locked account.")
    public void verify_locked_user_should_not_have_access() {
        bot.login(lockedOutUser, password);

        Assertions.assertEquals(bot.getTextString(errorMessageContainer), lockedUserErrorMessage,
                "Error message not displayed. User might have forbidden access");

    }

    @Test
    @DisplayName("Correct error message displayed for user logging in with incorrect password.")
    public void verify_error_for_user_with_wrong_password() {
        bot.login(standardUser, "test123");

        Assertions.assertAll(
                () -> Assertions.assertEquals(invalidPasswordErrorMessage, bot.getTextString(errorMessageContainer)),
                () -> Assertions.assertFalse((bot.getURL().equals(baseURL))));

    }


}

/*
Login
1.
Logowanie poprawne: standard_user / secret_sauce → przejście na listę produktów. (slqa.ru) - done
2.
Logowanie zablokowane: locked_out_user / secret_sauce → komunikat o blokadzie. (slqa.ru) - done
3.
Logowanie z innymi userami testowymi: problem_user, performance_glitch_user, error_user, visual_user → wejście do aplikacji lub specyficzne zachowanie (warto asercje dopasować do faktycznego zachowania). (slqa.ru)
4.
Błędne hasło dla poprawnego loginu → błąd walidacji.
5.
Puste pola login/hasło → błąd walidacji.
6.
Logout z menu → powrót na ekran logowania.
 */