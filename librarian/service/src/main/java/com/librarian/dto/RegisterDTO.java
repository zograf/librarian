package com.librarian.dto;

import javax.validation.constraints.*;

import com.librarian.model.EGender;

public class RegisterDTO {

    private String name;

    @Email
    private String email;
    
    @Size(min = 12, max = 512)
    private String password;

    private Integer age;
    private EGender gender;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }
    
}
