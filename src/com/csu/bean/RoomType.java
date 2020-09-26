package com.csu.bean;

public class RoomType {
    private int id;
    private String typeName;
    private int limitNum;

    public int getId() {
        return id;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
