package ru.yandex.practicum.sprint7;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.*;

public class CommonSteps {

    @Step("Check response status is 200")
    public static void checkStatusIs200(Response response) {
        response.then().statusCode(SC_OK);
    }

    @Step("Check response status is 201")
    public static void checkStatusIs201(Response response) {
        response.then().statusCode(SC_CREATED);
    }

    @Step("Check response status is 400")
    public static void checkStatusIs400(Response response) {
        response.then().statusCode(SC_BAD_REQUEST);
    }

    @Step("Check response status is 404")
    public static void checkStatusIs404(Response response) {
        response.then().statusCode(SC_NOT_FOUND);
    }

    @Step("Check response status is 409")
    public static void checkStatusIs409(Response response) {
        response.then().statusCode(SC_CONFLICT);
    }
}