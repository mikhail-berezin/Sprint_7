package ru.yandex.practicum.sprint7.model;

public class CancelOrderDto {

    public int track;

    public CancelOrderDto() {
    }

    public CancelOrderDto(int track) {
        this.track = track;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }
}