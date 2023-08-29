package com.trablog.spring.webapps.data.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SignUpResultDto {

    private boolean success;

    private int code;

    private String msg;

    private String token;
}
