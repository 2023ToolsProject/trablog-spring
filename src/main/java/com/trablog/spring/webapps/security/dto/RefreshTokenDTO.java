package com.trablog.spring.webapps.security.dto;

import lombok.Data;

@Data
public class RefreshTokenDTO {
    private String username;
    private String refreshToken;
}
