import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CancelOrderDto;
import model.CreateOrderResult;
import model.OrderCreateDto;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static constants.ScooterTestConstants.*;
import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    List<String> color;
    int track = -1;

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getLionData() {
        return new Object[][]{
                {List.of()},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")}
        };
    }

    @Test
    @DisplayName("Check order can be created")
    public void creatingOrderTest() {
        OrderCreateDto orderCreateDto = new OrderCreateDto(
                CUSTOMER_FIRST_NAME,
                CUSTOMER_LAST_NAME,
                ORDER_ADDRESS,
                ORDER_METRO_STATION,
                ORDER_PHONE,
                ORDER_RENT_TIME,
                ORDER_DELIVERY_DATE,
                ORDER_COMMENT,
                color
        );
        Response orderCreateResponse = createOrder(orderCreateDto);
        CreateOrderResult createOrderResult = orderCreateResponse.as(CreateOrderResult.class);
        if (createOrderResult != null) {
            track = createOrderResult.track;
        }
        checkTrackNumberIsNotNull(orderCreateResponse);
    }

    @After
    public void cancelOrder() {
        if (track > 0) {
            CancelOrderDto cancelOrderDto = new CancelOrderDto(track);
            cancelOrder(cancelOrderDto);
        }
    }

    @Step("Send POST request to /api/v1/orders")
    public Response createOrder(OrderCreateDto orderCreateDto) {

        Response response = given()
                .header("Content-type", "application/json").and().body(orderCreateDto)
                .post(ORDERS_ENDPOINT);

        return response;
    }

    @Step("Send POST request to /api/v1/orders/cancel")
    public Response cancelOrder(CancelOrderDto cancelOrderDto) {

        Response response = given()
                .header("Content-type", "application/json").and().body(cancelOrderDto)
                .put(CANCEL_ORDER_ENDPOINT);

        return response;
    }

    @Step("Check response contains track number")
    public void checkTrackNumberIsNotNull(Response response) {
        response.then().body("track", CoreMatchers.notNullValue());
    }
}