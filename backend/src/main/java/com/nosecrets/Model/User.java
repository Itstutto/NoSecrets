package com.nosecrets.model;

public class User {
    private int id;
    private String user;
    private String password;   // hash, no la contraseña en texto plano
    private String stat;

    public User() {}

    public User(int id, String user, String password, String stat) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.stat = stat;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getStat() { return stat; }
    public void setStat(String stat) { this.stat = stat; }
}