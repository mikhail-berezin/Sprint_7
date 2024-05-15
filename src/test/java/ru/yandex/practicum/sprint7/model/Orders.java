package ru.yandex.practicum.sprint7.model;

import java.util.List;

public class Orders {
    List<Order> orders;
    PageInfo pageInfo;
    List<MetroStation> availableStations;

    public Orders() {
    }

    public Orders(List<Order> orders, PageInfo pageInfo, List<MetroStation> availableStations) {
        this.orders = orders;
        this.pageInfo = pageInfo;
        this.availableStations = availableStations;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<MetroStation> getAvailableStations() {
        return availableStations;
    }

    public void setAvailableStations(List<MetroStation> availableStations) {
        this.availableStations = availableStations;
    }


    public static class Order {

        private int id;
        private int courierId;
        private String firstName;
        private String lastName;
        private String address;
        private String metroStation;
        private String phone;
        private int rentTime;
        private String deliveryDate;
        private int track;
        private List<String> color;
        private String comment;
        private String createdAt;
        private String updatedAt;
        private int status;

        public Order() {
        }

        public Order(
                int id,
                int courierId,
                String firstName,
                String lastName,
                String address,
                String metroStation,
                String phone,
                int rentTime,
                String deliveryDate,
                int track,
                List<String> color,
                String comment,
                String createdAt,
                String updatedAt,
                int status
        ) {
            this.id = id;
            this.courierId = courierId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.metroStation = metroStation;
            this.phone = phone;
            this.rentTime = rentTime;
            this.deliveryDate = deliveryDate;
            this.track = track;
            this.color = color;
            this.comment = comment;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCourierId() {
            return courierId;
        }

        public void setCourierId(int courierId) {
            this.courierId = courierId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMetroStation() {
            return metroStation;
        }

        public void setMetroStation(String metroStation) {
            this.metroStation = metroStation;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getRentTime() {
            return rentTime;
        }

        public void setRentTime(int rentTime) {
            this.rentTime = rentTime;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public int getTrack() {
            return track;
        }

        public void setTrack(int track) {
            this.track = track;
        }

        public List<String> getColor() {
            return color;
        }

        public void setColor(List<String> color) {
            this.color = color;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class PageInfo {

        private int page;
        private int total;
        private int limit;

        public PageInfo() {
        }

        public PageInfo(int page, int total, int limit) {
            this.page = page;
            this.total = total;
            this.limit = limit;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }
    }

    public static class MetroStation {
        private String name;
        private String number;
        private String color;

        public MetroStation() {
        }

        public MetroStation(String name, String number, String color) {
            this.name = name;
            this.number = number;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}