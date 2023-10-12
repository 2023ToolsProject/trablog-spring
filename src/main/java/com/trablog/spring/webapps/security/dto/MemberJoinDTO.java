package com.trablog.spring.webapps.security.dto;

import lombok.Data;

@Data
public class MemberJoinDTO {

    private String username;
    private String password;
    private String email;
}
