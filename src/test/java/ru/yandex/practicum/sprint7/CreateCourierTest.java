package ru.yandex.practicum.sprint7;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import ru.yandex.practicum.sprint7.model.CourierLoginDto;
import ru.yandex.practicum.sprint7.model.CourierLoginResult;
import ru.yandex.practicum.sprint7.model.CreateCourierDto;
import org.junit.After;
import org.junit.Test;

import static ru.yandex.practicum.sprint7.CommonSteps.*;
import static ru.yandex.practicum.sprint7.constants.ScooterTestConstants.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.protocol.HTTP.CONTENT_TYPE;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = SCOOTER_URL;
    }

    @Test
    @DisplayName("Check courier can be created")
    public void checkCourierCanBeCreated() {
        createCourier(new CreateCourierDto(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME));

        Response courierLoginResponse = loginCourier(new CourierLoginDto(COURIER_LOGIN, COURIER_PASSWORD));
        checkStatusIs200(courierLoginResponse);
    }

    @Test
    @DisplayName("Check courier can not be created twice")
    public void checkCourierCanNotBeCreatedTwice() {
        createCourier(new CreateCourierDto(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME));

        Response createCourierCopyResponse =
                createCourier(new CreateCourierDto(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME));
        checkStatusIs409(createCourierCopyResponse);
    }

    @Test
    @DisplayName("Check courier can not be created without any of required fields")
    public void checkCourierCanNotBeCreatedWithoutRequiredFields() {
        createCourier(new CreateCourierDto(EMPTY_STRING, COURIER_PASSWORD, COURIER_FIRST_NAME));
        createCourier(new CreateCourierDto(COURIER_LOGIN, EMPTY_STRING, COURIER_FIRST_NAME));

        Response tryToLoginResponse = loginCourier(new CourierLoginDto(COURIER_LOGIN, COURIER_PASSWORD));
        checkStatusIs404(tryToLoginResponse);
    }

    @Test
    @DisplayName("Check courier creating response status is 201")
    public void checkCourierCreatingStatus() {
        Response creatingCourierResponse =
                createCourier(new CreateCourierDto(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME));

        checkStatusIs201(creatingCourierResponse);
    }

    @Test
    @DisplayName("Check courier creating OK field contains true value")
    public void checkCourierCreatingOk() {
        Response creatingCourierResponse =
                createCourier(new CreateCourierDto(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME));

        checkOkFieldIsTrue(creatingCourierResponse);
    }

    @Test
    @DisplayName("Check courier can not be created without login")
    public void checkCourierCanNotBeCreatedWithoutLogin() {
        Response createCourierWithoutLoginResponse =
                createCourier(new CreateCourierDto(EMPTY_STRING, COURIER_PASSWORD, COURIER_FIRST_NAME));
        checkStatusIs400(createCourierWithoutLoginResponse);

        Response createCourierWithoutPasswordResponse =
                createCourier(new CreateCourierDto(COURIER_LOGIN, EMPTY_STRING, COURIER_FIRST_NAME));
        checkStatusIs400(createCourierWithoutPasswordResponse);
    }

    @Test
    @DisplayName("Check different couriers can not be created with same login")
    public void checkCreatingCouriersWithSameLogin() {
        createCourier(new CreateCourierDto(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME));

        Response creatingCourierResponse =
                createCourier(new CreateCourierDto(COURIER_LOGIN, ANOTHER_PASSWORD, ANOTHER_COURIER_NAME));

        checkStatusIs409(creatingCourierResponse);
    }

    @After
    public void deleteCourierIfExists() {
        int courierId = getCourierId(new CourierLoginDto(COURIER_LOGIN, COURIER_PASSWORD));
        if (courierId != 0) {
            deleteCourierById(courierId);
        }
    }

    @Step("Send POST request to /api/v1/courier")
    public Response createCourier(CreateCourierDto createCourierDto) {
        Response response = given()
                .header(CONTENT_TYPE, JSON_CONTENT_TYPE).and().body(createCourierDto)
                .post(CREATE_COURIER_ENDPOINT);
        return response;
    }

    @Step("Get id of existing user by login and password")
    public int getCourierId(CourierLoginDto courierLoginDto) {
        return loginCourier(courierLoginDto).body().as(CourierLoginResult.class).getId();
    }

    @Step("Send POST request to /api/v1/courier/login")
    public Response loginCourier(CourierLoginDto courierLoginDto) {
        Response response = given()
                .header(CONTENT_TYPE, JSON_CONTENT_TYPE).and().body(courierLoginDto)
                .post(LOGIN_COURIER_ENDPOINT);
        return response;
    }

    @Step("Check response ok field")
    public void checkOkFieldIsTrue(Response response) {
        response.then().body(OK_FIELD, equalTo(true));
    }

    @Step("Send DELETE request to /api/v1/courier")
    public void deleteCourierById(int courierId) {
        given().delete(DELETE_COURIER_ENDPOINT_PREFIX + courierId);
    }
}