package com.nosecrets.dto;

public class LoginDTO {
    private String user;
    private String password;

    public LoginDTO() {}

    public LoginDTO(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getPassword() { return password; }
    public void setStat(String password) { this.password = password; }
}
