package ru.yandex.practicum.sprint7.model;

public class CourierLoginDto {

    private String login;
    private String password;

    public CourierLoginDto() {
    }

    public CourierLoginDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}