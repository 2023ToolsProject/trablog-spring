//package com.trablog.spring.webapps.controller;
//
//import com.trablog.spring.webapps.domain.User;
//import com.trablog.spring.webapps.dto.ResponseDTO;
//import com.trablog.spring.webapps.dto.SignInDTO;
//import com.trablog.spring.webapps.dto.UserDTO;
////import com.trablog.spring.webapps.exception.TrablogException;
//import com.trablog.spring.webapps.jwt.JwtTokenProvider;
//import com.trablog.spring.webapps.service.UserService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.sql.Timestamp;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//public class UserController {
//
//    private UserService userService;
//    private PasswordEncoder passwordEncoder;
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Autowired
//    public UserController(UserService userService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @PostMapping("/auth/sign-in")
//    public @ResponseBody ResponseDTO<?> singIn(@RequestBody SignInDTO singInDTO, HttpSession session) {
//        User findUser = userService.getUserByUsername(singInDTO.getUsername());
//        // 검색 결과 유무와 사용자가 입력한 비밀번호가 유효한지 확인한다.
//        if(findUser.getUsername() == null) {
//
//            return new ResponseDTO<>(HttpStatus.UNAUTHORIZED.value(), "아이디가 존재하지 않아요.");
//        } else {
//            if(passwordEncoder.matches(singInDTO.getPassword(), findUser.getPassword())) {
//                // 로그인 성공 시 accessToken 생성해서 accessToken 필드에 저장
//                String accessToken = jwtTokenProvider.createToken(findUser.getUsername(), findUser.getRoles());
//                findUser.setAccessToken(accessToken);
//                return new ResponseDTO<>(HttpStatus.OK.value(), findUser.getUsername() + "님 로그인 성공하셨습니다. accesstoken: " + accessToken);
//            } else {
//                return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "비밀번호 오류!");
//            }
//        }
//    }
//
//    @PostMapping("/auth/insertUser")
//    public @ResponseBody ResponseDTO<?> insertUser(@RequestBody UserDTO userDTO) {
//        Optional<User> findUser = userService.getUser(userDTO.getUsername());
//        if(findUser.ifPresent(m -> {
//            System.out.println("신규회원입니다.");});) {
////             회원가입 성공 시 accessToken 생성해서 accessToken 필드에 저장
//            List<String> roles = Collections.singletonList("ROLE_USER");
//            String accessToken = jwtTokenProvider.createToken(userDTO.getUsername(), roles);
//            User newUser = User.builder()
//                            .username(userDTO.getUsername())
//                            .password(userDTO.getPassword())
//                            .email(userDTO.getEmail())
//                            .roles(roles)
//                            .createDate(new Timestamp(System.currentTimeMillis()))
//                            .accessToken(accessToken)
//                    .build();
//            userService.insertUser(newUser);
//            return new ResponseDTO<>(HttpStatus.OK.value(), newUser.getUsername() + "님 회원가입 성공하셨습니다.!");
//        } else {
//            System.out.println(findUser);
//            return new ResponseDTO<>(HttpStatus.CONFLICT.value(), userDTO.getUsername() + "님은 이미 회원입니다.!");
//    }
//
//    @PutMapping("/user")
//    public @ResponseBody ResponseDTO<?> updateUser(@RequestBody UserDTO userDTO, @AuthenticationPrincipal User principal) {
//        User updatedUser = userService.getUserByUsername(userDTO.getUsername());
//        updatedUser.setUsername(userDTO.getUsername());
//        updatedUser.setPassword(userDTO.getPassword());
//        updatedUser.setEmail(updatedUser.getEmail());
//        userService.updateUser(updatedUser);
////        // 회원 정보 수정과 동시에 세션 갱신
////        principal.setUser(userService.updateUser(updatedUser));
//        return new ResponseDTO<>(HttpStatus.OK.value(), updatedUser.getUsername() + "수정 완료");    }
//}
