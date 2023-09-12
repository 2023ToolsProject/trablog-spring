package com.trablog.spring.webapps.controller;

import com.trablog.spring.webapps.domain.RoleType;
import com.trablog.spring.webapps.domain.User;
import com.trablog.spring.webapps.dto.ResponseDTO;
import com.trablog.spring.webapps.dto.SignInDTO;
import com.trablog.spring.webapps.dto.UserDTO;
//import com.trablog.spring.webapps.exception.TrablogException;
import com.trablog.spring.webapps.persistence.UserRepository;
import com.trablog.spring.webapps.security.UserDetailsImpl;
import com.trablog.spring.webapps.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Supplier;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/auth/sign-in")
    public @ResponseBody ResponseDTO<?> singIn(@RequestBody SignInDTO singInDTO, HttpSession session) {
        User findUser = userService.getUser(singInDTO.getUsername());
        // 검색 결과 유무와 사용자가 입력한 비밀번호가 유효한지 확인한다.
        if(findUser.getUsername() == null) {

            return new ResponseDTO<>(HttpStatus.UNAUTHORIZED.value(), "아이디가 존재하지 않아요.");
        } else {
            if(passwordEncoder.matches(singInDTO.getPassword(), findUser.getPassword())) {
                // 로그인 성공 시 세션에 사용자 정보를 저장한다.
                session.setAttribute("principal", findUser);
                return new ResponseDTO<>(HttpStatus.OK.value(), findUser.getUsername() + "님 로그인 성공하셨습니다.");
            } else {
                return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "비밀번호 오류!");
            }
        }
    }

    @PostMapping("/auth/insertUser")
    public @ResponseBody ResponseDTO<?> insertUser(@RequestBody UserDTO userDTO) { //RequestBody id, 비밀번호, 이메일만 있는 Dto로 바꾸기.
        User findUser = userService.getUser(userDTO.getUsername());
        if(findUser.getUsername() == null) {
            User newUser = User.builder()
                            .username(userDTO.getUsername())
                            .password(userDTO.getPassword())
                            .email(userDTO.getEmail())
                            .role(RoleType.USER)
                            .createDate(new Timestamp(System.currentTimeMillis()))
                    .build();
            userService.insertUser(newUser);
            return new ResponseDTO<>(HttpStatus.OK.value(), newUser.getUsername() + "님 회원가입 성공하셨습니다.!");
        } else {
            return new ResponseDTO<>(HttpStatus.CONFLICT.value(), findUser.getUsername() + "님은 이미 회원입니다.!");
        }
    }

    @PutMapping("/user")
    public @ResponseBody ResponseDTO<?> updateUser(@RequestBody UserDTO userDTO, @AuthenticationPrincipal UserDetailsImpl principal) {
        User updatedUser = userService.getUser(userDTO.getUsername());
        updatedUser.setUsername(userDTO.getUsername());
        updatedUser.setPassword(userDTO.getPassword());
        updatedUser.setEmail(updatedUser.getEmail());
        userService.updateUser(updatedUser);
        // 회원 정보 수정과 동시에 세션 갱신
        principal.setUser(userService.updateUser(updatedUser));
        return new ResponseDTO<>(HttpStatus.OK.value(), updatedUser.getUsername() + "수정 완료");    }


}
