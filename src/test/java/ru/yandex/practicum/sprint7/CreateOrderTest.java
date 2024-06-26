package ru.yandex.practicum.sprint7;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import ru.yandex.practicum.sprint7.model.CancelOrderDto;
import ru.yandex.practicum.sprint7.model.CreateOrderResult;
import ru.yandex.practicum.sprint7.model.OrderCreateDto;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static ru.yandex.practicum.sprint7.CommonSteps.checkStatusIs201;
import static ru.yandex.practicum.sprint7.constants.ScooterTestConstants.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.protocol.HTTP.CONTENT_TYPE;

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

    @Before
    public void setUp() {
        RestAssured.baseURI = SCOOTER_URL;
    }

    @Test
    @DisplayName("Check order can be created")
    public void checkOrderCanBeCreated() {
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
        checkStatusIs201(orderCreateResponse);
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
                .header(CONTENT_TYPE, JSON_CONTENT_TYPE).and().body(orderCreateDto)
                .post(ORDERS_ENDPOINT);

        return response;
    }

    @Step("Send PUT request to /api/v1/orders/cancel")
    public Response cancelOrder(CancelOrderDto cancelOrderDto) {

        Response response = given()
                .header(CONTENT_TYPE, JSON_CONTENT_TYPE).and().body(cancelOrderDto)
                .put(CANCEL_ORDER_ENDPOINT);

        return response;
    }

    @Step("Check response contains track number")
    public void checkTrackNumberIsNotNull(Response response) {
        response.then().body(TRACK_FIELD, CoreMatchers.notNullValue());
    }
}