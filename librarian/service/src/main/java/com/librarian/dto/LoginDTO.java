package com.librarian.dto;

import javax.validation.constraints.Size;

public class LoginDTO {
    private String email;

    @Size(min = 8, max = 512)
    private String password;
    public LoginDTO() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
