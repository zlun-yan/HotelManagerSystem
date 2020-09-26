package com.csu.bean;

public class Product {
    private int productId;
    private String productName;
    private double price;
    private int lastNum;

    public double getPrice() {
        return price;
    }

    public int getLastNum() {
        return lastNum;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setLastNum(int lastNum) {
        this.lastNum = lastNum;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
