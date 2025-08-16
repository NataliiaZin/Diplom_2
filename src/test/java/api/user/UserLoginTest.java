package api.user;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

@Epic("Stellar Burgers API")
@Feature("Авторизация пользователя")
public class UserLoginTest extends BaseUserTest {

    private static final String WRONG_CREDENTIALS_MESSAGE = "email or password are incorrect";

    @After
    public void afterEach() {
        userSteps.deleteUser(user);
    }

    @Test
    @Story("Вход под существующим пользователем")
    public void loginWithValidCredentialsTest() {
        userSteps.registerUser(user);
        userSteps.loginUser(user)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(RESPONSE_SUCCESS_FIELD, equalTo(true))
                .body("user.email", equalTo(user.getEmail().toLowerCase()));
    }

    @Test
    @Story("Вход с некорректным полем")
    @Description("Вход с неверными эмейлом")
    public void loginWithInvalidEmailTest() {
        userSteps.registerUser(user);
        String realUserEmail = user.getEmail();
        user.setEmail(INVALID_DATA);
        userSteps.loginUser(user)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body(RESPONSE_SUCCESS_FIELD, equalTo(false))
                .body(RESPONSE_MESSAGE_FIELD, equalTo(WRONG_CREDENTIALS_MESSAGE));
        user.setEmail(realUserEmail);
    }

    @Test
    @Story("Вход с некорректным полем")
    @Description("Вход с неверными паролем")
    public void loginWithInvalidPasswordTest() {
        userSteps.registerUser(user);
        String realUserPassword = user.getPassword();
        user.setPassword(INVALID_DATA);
        userSteps.loginUser(user)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body(RESPONSE_SUCCESS_FIELD, equalTo(false))
                .body(RESPONSE_MESSAGE_FIELD, equalTo(WRONG_CREDENTIALS_MESSAGE));
        user.setPassword(realUserPassword);
    }
}