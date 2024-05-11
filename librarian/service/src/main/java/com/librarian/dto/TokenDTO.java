package com.librarian.dto;

import com.librarian.model.ERole;

public class TokenDTO {
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private ERole userRole;
    private String email;

    public TokenDTO() {

    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public ERole getUserRole() {
        return userRole;
    }
    public void setUserRole(ERole role) {
        this.userRole = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
