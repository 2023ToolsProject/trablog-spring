package com.trablog.spring.webapps.controller;

import com.trablog.spring.webapps.domain.Board;
import com.trablog.spring.webapps.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    @GetMapping
    public String get() {
        return "hello";
    }


}
