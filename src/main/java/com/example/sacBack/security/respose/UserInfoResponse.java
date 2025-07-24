package com.example.sacBack.security.respose;

import lombok.Getter;

import java.util.List;

@Getter
public class UserInfoResponse {
    private Long id;
    private String username;
    private List<String> roles;

    public UserInfoResponse(Long id, String username, List<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
