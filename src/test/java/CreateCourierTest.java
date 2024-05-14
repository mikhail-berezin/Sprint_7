import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CourierLoginDto;
import model.CourierLoginResult;
import model.CreateCourierDto;
import org.apache.http.protocol.HTTP;
import org.junit.After;
import org.junit.Test;

import static constants.ScooterTestConstants.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.apache.http.protocol.HTTP.CONTENT_TYPE;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {

    @Test
    @DisplayName("Check courier can be created")
    public void testCourierCanBeCreated() {
        createCourier(new CreateCourierDto(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME));

        Response courierLoginResponse = loginCourier(new CourierLoginDto(COURIER_LOGIN, COURIER_PASSWORD));
        checkStatusIs200(courierLoginResponse);
    }

    @Test
    @DisplayName("Check courier can not be created twice")
    public void testCourierCanNotBeCreatedTwice() {
        createCourier(new CreateCourierDto(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME));

        Response createCourierCopyResponse =
                createCourier(new CreateCourierDto(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME));
        checkStatusIs409(createCourierCopyResponse);
    }

    @Test
    @DisplayName("Check courier can not be created without required fields")
    public void testCourierCanNotBeCreatedWithoutRequiredFields() {
        createCourier(new CreateCourierDto(EMPTY_STRING, COURIER_PASSWORD, COURIER_FIRST_NAME));
        createCourier(new CreateCourierDto(COURIER_LOGIN, EMPTY_STRING, COURIER_FIRST_NAME));

        Response tryToLoginResponse = loginCourier(new CourierLoginDto(COURIER_LOGIN, COURIER_PASSWORD));
        checkStatusIs404(tryToLoginResponse);
    }

    @Test
    @DisplayName("Check courier creating response status is 201")
    public void testCourierCreatingStatus() {
        Response creatingCourierResponse =
                createCourier(new CreateCourierDto(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME));

        checkStatusIs201(creatingCourierResponse);
    }

    @Test
    @DisplayName("Check courier creating OK field contains true value")
    public void testCourierCreatingOk() {
        Response creatingCourierResponse =
                createCourier(new CreateCourierDto(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME));

        checkOkFieldIsTrue(creatingCourierResponse);
    }

    @Test
    @DisplayName("Check courier can not be created without login")
    public void testCourierCanNotBeCreatedWithoutLogin() {
        Response createCourierWithoutLoginResponse =
                createCourier(new CreateCourierDto(EMPTY_STRING, COURIER_PASSWORD, COURIER_FIRST_NAME));
        checkStatusIs400(createCourierWithoutLoginResponse);

        Response createCourierWithoutPasswordResponse =
                createCourier(new CreateCourierDto(COURIER_LOGIN, EMPTY_STRING, COURIER_FIRST_NAME));
        checkStatusIs400(createCourierWithoutPasswordResponse);
    }

    @Test
    @DisplayName("Check different couriers can not be created with same login")
    public void testCreatingCouriersWithSameLogin() {
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

    @Step("Check response status is 200")
    public void checkStatusIs200(Response response) {
        response.then().statusCode(SC_OK);
    }

    @Step("Check response status is 201")
    public void checkStatusIs201(Response response) {
        response.then().statusCode(SC_CREATED);
    }

    @Step("Check response status is 400")
    public void checkStatusIs400(Response response) {
        response.then().statusCode(SC_BAD_REQUEST);
    }

    @Step("Check response status is 404")
    public void checkStatusIs404(Response response) {
        response.then().statusCode(SC_NOT_FOUND);
    }

    @Step("Check response status is 409")
    public void checkStatusIs409(Response response) {
        response.then().statusCode(SC_CONFLICT);
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