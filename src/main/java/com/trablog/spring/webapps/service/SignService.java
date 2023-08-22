package com.trablog.spring.webapps.service;

import com.trablog.spring.webapps.data.dto.SignInResultDto;
import com.trablog.spring.webapps.data.dto.SignUpResultDto;

public interface SignService {
    SignUpResultDto signUp(String id, String password, String name, String role);

    SignInResultDto signIn(String id, String password) throws RuntimeException;

}
