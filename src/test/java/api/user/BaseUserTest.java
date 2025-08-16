package api.user;

import api.core.BaseTest;
import api.user.model.User;
import api.user.steps.UserSteps;
import org.junit.Before;

public class BaseUserTest extends BaseTest {

    protected User user;
    protected UserSteps userSteps = new UserSteps();

    @Before
    public void beforeEach() {
        user = userSteps.initTestUser();
    }
}
