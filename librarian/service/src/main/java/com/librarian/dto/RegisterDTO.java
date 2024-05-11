package com.librarian.dto;

import javax.validation.constraints.*;

public class RegisterDTO {
    @Email
    private String email;
    @Size(min = 12, max = 512)
    private String password;

    public RegisterDTO() {

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
