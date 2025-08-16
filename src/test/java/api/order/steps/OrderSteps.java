package api.order.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;

import java.util.Map;

import static api.core.constant.ApiEndpoints.CREATE_ORDER_ENDPOINT;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создание заказа")
    public Response createOrder(String bearerToken, String... ingredients) {
        return given()
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .contentType(String.valueOf(ContentType.JSON))
                .body(Map.of("ingredients", ingredients))
                .when()
                .post(CREATE_ORDER_ENDPOINT);
    }

}
