package ru.yandex.practicum.sprint7.constants;

public class ScooterTestConstants {

    public static final String SCOOTER_URL = "http://qa-scooter.praktikum-services.ru";
    public static final String CREATE_COURIER_ENDPOINT = SCOOTER_URL + "/api/v1/courier";
    public static final String DELETE_COURIER_ENDPOINT_PREFIX = SCOOTER_URL + "/api/v1/courier/";
    public static final String LOGIN_COURIER_ENDPOINT = SCOOTER_URL + "/api/v1/courier/login";
    public static final String ORDERS_ENDPOINT = SCOOTER_URL + "/api/v1/orders";
    public static final String CANCEL_ORDER_ENDPOINT = SCOOTER_URL + "/api/v1/orders/cancel";

    public static final String COURIER_LOGIN = "mikhailberezintest";
    public static final String COURIER_PASSWORD = "1234";
    public static final String COURIER_FIRST_NAME = "foo";

    public static final String CUSTOMER_FIRST_NAME = "berezinmikhailtestfirstname";
    public static final String CUSTOMER_LAST_NAME = "berezinmikhailtestlastname";
    public static final String ORDER_ADDRESS = "Москва, Новоясеневский проспект, дом 1";
    public static final int ORDER_METRO_STATION = 22;
    public static final String ORDER_PHONE = "+79876543210";
    public static final int ORDER_RENT_TIME = 3;
    public static final String ORDER_DELIVERY_DATE = "2024-05-15";
    public static final String ORDER_COMMENT = "Вы лучший сервис, с нетерпением жду моего самоката";

    public static final String INCORRECT_LOGIN = "zavedomoplohoilogin";
    public static final String INCORRECT_PASSWORD = "0000";
    public static final String ANOTHER_PASSWORD = "anotherpwd";
    public static final String ANOTHER_COURIER_NAME = "bar";

    public static final String JSON_CONTENT_TYPE = "application/json";

    public static final String ID_FIELD = "id";
    public static final String OK_FIELD = "ok";
    public static final String MESSAGE_FIELD = "message";
    public static final String ORDERS_FIELD = "orders";
    public static final String TRACK_FIELD = "track";

    public static final String INCOMPLETE_DATA_MESSAGE = "Недостаточно данных для входа";

    public static final String EMPTY_STRING = "";
}