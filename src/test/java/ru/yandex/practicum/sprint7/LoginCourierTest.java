package ru.yandex.practicum.sprint7;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import ru.yandex.practicum.sprint7.model.CourierLoginDto;
import ru.yandex.practicum.sprint7.model.CourierLoginResult;
import ru.yandex.practicum.sprint7.model.CreateCourierDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static ru.yandex.practicum.sprint7.CommonSteps.*;
import static ru.yandex.practicum.sprint7.constants.ScooterTestConstants.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.protocol.HTTP.CONTENT_TYPE;
import static org.hamcrest.CoreMatchers.*;

public class LoginCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = SCOOTER_URL;
        createCourier();
    }

    @Test
    @DisplayName("Check courier can log in")
    @Description("Basic test for /api/v1/courier/login")
    public void checkCourierCanLogIn() {
        Response response = loginCourier(new CourierLoginDto(COURIER_LOGIN, COURIER_PASSWORD));
        checkStatusIs200(response);
    }

    @Test
    @DisplayName("Check authorization fails without any required field")
    public void checkAuthorizationWithoutAnyField() {
        Response responseWithoutLogin = loginCourier(new CourierLoginDto(EMPTY_STRING, COURIER_PASSWORD));
        checkStatusIs400(responseWithoutLogin);

        Response responseWithoutPassword = loginCourier(new CourierLoginDto(COURIER_LOGIN, EMPTY_STRING));
        checkStatusIs400(responseWithoutPassword);
    }

    @Test
    @DisplayName("Check authorization fails if any field is incorrect")
    public void checkIncorrectLoginOrPassword() {
        Response responseWithWrongLogin = loginCourier(new CourierLoginDto(INCORRECT_LOGIN, COURIER_PASSWORD));
        checkStatusIs404(responseWithWrongLogin);

        Response responseWithWrongPassword = loginCourier(new CourierLoginDto(COURIER_LOGIN, INCORRECT_PASSWORD));
        checkStatusIs404(responseWithWrongPassword);
    }

    @Test
    @DisplayName("Check authorization with incorrect field error text")
    public void checkAuthorizationWithoutAnyFieldError() {
        Response responseWithoutLogin = loginCourier(new CourierLoginDto(EMPTY_STRING, COURIER_PASSWORD));
        checkErrorText(responseWithoutLogin, INCOMPLETE_DATA_MESSAGE);

        Response responseWithoutPassword = loginCourier(new CourierLoginDto(COURIER_LOGIN, EMPTY_STRING));
        checkErrorText(responseWithoutPassword, INCOMPLETE_DATA_MESSAGE);
    }

    @Test
    @DisplayName("Check authorization with not existing login")
    public void checkNotExistingLogin() {
        Response response = loginCourier(new CourierLoginDto(INCORRECT_LOGIN, COURIER_PASSWORD));
        checkStatusIs404(response);
    }

    @Test
    @DisplayName("Check logging in response has id field")
    public void checkLoggingInResponseHasId() {
        Response response = loginCourier(new CourierLoginDto(COURIER_LOGIN, COURIER_PASSWORD));
        checkResponseHasId(response);
    }

    @After
    public void deleteCourierIfExists() {
        int courierId = getCourierId(new CourierLoginDto(COURIER_LOGIN, COURIER_PASSWORD));
        if (courierId != 0) {
            deleteCourierById(courierId);
        }
    }

    @Step("Prepare courier")
    public void createCourier() {
        CreateCourierDto createCourierDto = new CreateCourierDto(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME);

        given()
                .header(CONTENT_TYPE, JSON_CONTENT_TYPE).and().body(createCourierDto)
                .post(CREATE_COURIER_ENDPOINT);
    }

    @Step("Send POST request to /api/v1/courier/login")
    public Response loginCourier(CourierLoginDto courierLoginDto) {

        Response response = given()
                .header(CONTENT_TYPE, JSON_CONTENT_TYPE).and().body(courierLoginDto)
                .post(LOGIN_COURIER_ENDPOINT);

        return response;
    }

    @Step("Check response has courier id")
    public void checkResponseHasId(Response response) {
        response.then().body("id", notNullValue()).and().body(ID_FIELD, isA(Integer.class));
    }

    @Step("Check response has correct error text")
    public void checkErrorText(Response response, String errorMessage) {
        response.then().body(MESSAGE_FIELD, equalTo(errorMessage));
    }

    @Step("Search for existing user and get id")
    public int getCourierId(CourierLoginDto courierLoginDto) {
        return loginCourier(courierLoginDto).body().as(CourierLoginResult.class).getId();
    }

    @Step("Send DELETE request to /api/v1/courier")
    public void deleteCourierById(int courierId) {
        given().delete(DELETE_COURIER_ENDPOINT_PREFIX + courierId);
    }
}