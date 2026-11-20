package com.nosecrets.dto;

public class UserDTO {
    private int id;
    private String user;
    private String stat;

    public UserDTO() {}

    public UserDTO(int id, String user, String stat) {
        this.id = id;
        this.user = user;
        this.stat = stat;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getStat() { return stat; }
    public void setStat(String stat) { this.stat = stat; }
}