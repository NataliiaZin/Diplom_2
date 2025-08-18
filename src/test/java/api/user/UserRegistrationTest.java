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
@Feature("Регистрация пользователя")
public class UserRegistrationTest extends BaseUserTest {

    private static final String USER_ALREADY_EXISTS_MESSAGE = "User already exists";
    private static final String REQUIRED_FIELD_MESSAGE = "Email, password and name are required fields";

    @After
    public void afterEach() {
        userSteps.deleteUser(user);
    }

    @Test
    @Story("Создание уникального пользователя")
    @Description("Регистрация нового пользователя с уникальным email")
    public void createUniqueUserTest() {
        userSteps.registerUser(user)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(RESPONSE_SUCCESS_FIELD, equalTo(true));
    }

    @Test
    @Story("Создание пользователя, который уже зарегистрирован")
    @Description("Попытка регистрации пользователя с уже существующим email")
    public void createExistingUserTest() {
        userSteps.registerUser(user);
        userSteps.registerUser(user)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body(RESPONSE_SUCCESS_FIELD, equalTo(false))
                .body(RESPONSE_MESSAGE_FIELD, equalTo(USER_ALREADY_EXISTS_MESSAGE));
    }

    @Test
    @Story("Создание пользователя без обязательного поля")
    @Description("Попытка регистрации без email")
    public void createUserWithoutEmailTest() {
        user.setEmail(null);
        userSteps.registerUser(user)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body(RESPONSE_SUCCESS_FIELD, equalTo(false))
                .body(RESPONSE_MESSAGE_FIELD, equalTo(REQUIRED_FIELD_MESSAGE));
    }

    @Test
    @Story("Создание пользователя без обязательного поля")
    @Description("Попытка регистрации без login")
    public void createUserWithoutNameTest() {
        user.setName(null);
        userSteps.registerUser(user)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body(RESPONSE_SUCCESS_FIELD, equalTo(false))
                .body(RESPONSE_MESSAGE_FIELD, equalTo(REQUIRED_FIELD_MESSAGE));
    }

    @Test
    @Story("Создание пользователя без обязательного поля")
    @Description("Попытка регистрации без password")
    public void createUserWithoutPasswordTest() {
        user.setPassword(null);
        userSteps.registerUser(user)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body(RESPONSE_SUCCESS_FIELD, equalTo(false))
                .body(RESPONSE_MESSAGE_FIELD, equalTo(REQUIRED_FIELD_MESSAGE));
    }
}
