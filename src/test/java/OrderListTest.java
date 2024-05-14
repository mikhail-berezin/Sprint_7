import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static constants.ScooterTestConstants.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {

    @Test
    @DisplayName("Check getting orders")
    @Description("Basic test for /api/v1/orders")
    public void checkGettingOrders() {
        Response orderCreateResponse = getOrders();
        checkResponseStatusAndContent(orderCreateResponse);
    }

    @Step("Send GET request to /api/v1/orders")
    public Response getOrders() {
        Response response = given().get(ORDERS_ENDPOINT);
        return response;
    }

    @Step("Checking OK response status and not null content")
    public void checkResponseStatusAndContent(Response orderCreateResponse) {
        orderCreateResponse.then().statusCode(SC_OK).and().body(ORDERS_FIELD, notNullValue());
    }
}