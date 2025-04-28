package com.jumooong.forms;

import jakarta.validation.constraints.NotBlank;

public class AppUser {
    @NotBlank(message = "Please input your username.")
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
