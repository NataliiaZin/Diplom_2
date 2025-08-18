package api.core;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.BeforeClass;

import static api.core.constant.ApiEndpoints.APP_API_URL;
import static io.restassured.RestAssured.filters;

public class BaseTest {

    protected static final String RESPONSE_SUCCESS_FIELD = "success";
    protected static final String RESPONSE_MESSAGE_FIELD = "message";
    protected static final String INVALID_DATA = "invalid";

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = APP_API_URL;
        filters(new AllureRestAssured());
    }
}
