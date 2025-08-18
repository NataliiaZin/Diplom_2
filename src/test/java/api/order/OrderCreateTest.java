package api.order;

import api.core.BaseTest;
import api.order.steps.OrderSteps;
import api.user.model.User;
import api.user.steps.UserSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

@Epic("Stellar Burgers API")
@Feature("Создание заказа")
public class OrderCreateTest extends BaseTest {

    private final UserSteps userSteps = new UserSteps();
    private final OrderSteps orderSteps = new OrderSteps();
    private final String[] ingredients = {"61c0c5a71d1f82001bdaaa6d"};
    private static final String NO_INGREDIENTS_ERROR_MESSAGE = "Ingredient ids must be provided";
    private User testUser;

    @Before
    public void beforeEach() {
        testUser = userSteps.initTestUser();
        userSteps.registerUser(testUser);
    }

    @After
    public void afterEach() {
        userSteps.deleteUser(testUser);
    }

    @Test
    @Story("Создание заказа")
    @Description("Успешное создание заказа с авторизацией")
    public void createOrderWithAuthTest() {
        userSteps.loginUser(testUser);
        orderSteps.createOrder(testUser.getAccessToken(), ingredients)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(RESPONSE_SUCCESS_FIELD, equalTo(true));
    }

    @Test
    @Story("Создание заказа")
    @Description("Создание заказа без авторизации")
    public void createOrderWithoutAuthTest() {
        orderSteps.createOrder(INVALID_DATA, ingredients)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    @Story("Создание заказа")
    @Description("Создание заказа без ингридиентов")
    public void createOrderWithoutIngredientsTest() {
        userSteps.loginUser(testUser);
        orderSteps.createOrder(testUser.getAccessToken())
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(RESPONSE_SUCCESS_FIELD, equalTo(false))
                .body(RESPONSE_MESSAGE_FIELD, equalTo(NO_INGREDIENTS_ERROR_MESSAGE));
    }

    @Test
    @Story("Создание заказа")
    @Description("Создание заказа невалидными ингридиентами")
    public void createOrderWithInvalidIngredientsTest() {
        userSteps.loginUser(testUser);
        orderSteps.createOrder(testUser.getAccessToken(), new String[]{INVALID_DATA})
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}
