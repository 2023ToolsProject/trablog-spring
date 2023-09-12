package com.trablog.spring.webapps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInDTO {
    private String username;
    private String password;
}
