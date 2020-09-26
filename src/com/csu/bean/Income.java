package com.csu.bean;

public class Income {
    private int id;
    private String date;
    private double income;
    private int bookNum;
    private int stayNum;
    private int leaveNum;

    public int getId() {
        return id;
    }

    public double getIncome() {
        return income;
    }

    public int getBookNum() {
        return bookNum;
    }

    public int getLeaveNum() {
        return leaveNum;
    }

    public int getStayNum() {
        return stayNum;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public void setBookNum(int bookNum) {
        this.bookNum = bookNum;
    }

    public void setLeaveNum(int leaveNum) {
        this.leaveNum = leaveNum;
    }

    public void setStayNum(int stayNum) {
        this.stayNum = stayNum;
    }
}
