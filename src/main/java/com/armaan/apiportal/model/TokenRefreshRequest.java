package com.armaan.apiportal.model;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}