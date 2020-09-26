package com.csu.bean;

public class Clerk {
    private int id;
    private String name;
    private String job;
    private String jid;
    private String password;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getPassword() {
        return password;
    }

    public String getJid() {
        return jid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }
}
