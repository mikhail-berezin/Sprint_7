package ru.yandex.practicum.sprint7;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static ru.yandex.practicum.sprint7.CommonSteps.checkStatusIs200;
import static ru.yandex.practicum.sprint7.constants.ScooterTestConstants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {

    @Test
    @DisplayName("Check getting orders")
    @Description("Basic test for /api/v1/orders")
    public void checkGettingOrders() {
        Response orderCreateResponse = getOrders();
        checkStatusIs200(orderCreateResponse);
        checkResponseOrdersField(orderCreateResponse);
    }

    @Step("Send GET request to /api/v1/orders")
    public Response getOrders() {
        Response response = given().get(ORDERS_ENDPOINT);
        return response;
    }

    @Step("Checking response not null content")
    public void checkResponseOrdersField(Response orderCreateResponse) {
        orderCreateResponse.then().body(ORDERS_FIELD, notNullValue());
    }
}