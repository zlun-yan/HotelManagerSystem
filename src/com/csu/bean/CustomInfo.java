package com.csu.bean;

public class CustomInfo {
    private String id;
    private String name;
    private String telephone;
    private double totConsume;
    private int vip;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public double getTotConsume() {
        return totConsume;
    }

    public String getTelephone() {
        return telephone;
    }

    public int getVip() {
        return vip;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setTotConsume(double totConsume) {
        this.totConsume = totConsume;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }
}
