package com.csu.bean;

public class Room {
    private int roomId;
    private String type;
    private int floor;
    private double price;
    private int state;
    private String customId;
    private String date;
    private double consume;

    public double getPrice() {
        return price;
    }

    public String getCustomId() {
        return customId;
    }

    public int getFloor() {
        return floor;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getState() {
        return state;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public double getConsume() {
        return consume;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setConsume(double consume) {
        this.consume = consume;
    }
}
