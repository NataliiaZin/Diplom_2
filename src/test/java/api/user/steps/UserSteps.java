package api.user.steps;

import api.user.model.User;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;

import static api.core.constant.ApiEndpoints.*;
import static api.core.util.TestUtils.generateWithDefaultSize;
import static io.restassured.RestAssured.given;

public class UserSteps {
    
    @Step("Регистрация пользователя")
    public Response registerUser(User user) {
        return given()
                .contentType(String.valueOf(ContentType.JSON))
                .body(user)
                .when()
                .post(USER_REGISTER_ENDPOINT);
    }

    @Step("Авторизация пользователя")
    public Response loginUser(User user) {
        Response response = given()
                .contentType(String.valueOf(ContentType.JSON))
                .body(user)
                .when()
                .post(USER_AUTH_ENDPOINT);
        if (response.getStatusCode() == HttpStatus.SC_OK) {
            String accessToken = response.jsonPath().getString("accessToken");
            user.setAccessToken(accessToken);
        }
        return response;
    }

    @Step("Удаление пользователя")
    public void deleteUser(User user) {
        if (user.getAccessToken() == null) {
            loginUser(user);
        }
        given()
                .header(HttpHeaders.AUTHORIZATION, user.getAccessToken())
                .contentType(String.valueOf(ContentType.JSON))
                .body(user)
                .when()
                .post(USER_DELETE_ENDPOINT);
    }

    @Step("Удаление пользователя")
    public User initTestUser() {
        return User.builder()
                .email(generateWithDefaultSize() + "@yandex.ru")
                .name(generateWithDefaultSize())
                .password(generateWithDefaultSize())
                .build();
    }
}
