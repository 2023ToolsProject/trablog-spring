package com.trablog.spring.webapps.controller;

import com.trablog.spring.webapps.domain.Member;
import com.trablog.spring.webapps.dto.MemberResponseDTO;
import com.trablog.spring.webapps.security.Token;
import com.trablog.spring.webapps.security.dto.MemberJoinDTO;
import com.trablog.spring.webapps.security.dto.MemberLoginDTO;
import com.trablog.spring.webapps.security.dto.RefreshTokenDTO;
import com.trablog.spring.webapps.service.MemberService;
import com.trablog.spring.webapps.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/profile")
    public ResponseEntity<Member> getMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) auth.getPrincipal();

        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    @PostMapping("/join")
    public ResponseEntity<Token> joinPOST(@RequestBody MemberJoinDTO memberJoinDTO) {
        log.info("join post...");
        log.info(memberJoinDTO);
//        MemberResponseDTO memberResponseDTO;
        Token jwtToken;
        try {
           jwtToken  = memberService.join(memberJoinDTO);
        } catch (MemberService.UsernameExistException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }

    @PostMapping("/login")
    public ResponseEntity<Token> loginPOST(@RequestBody MemberLoginDTO memberLoginDTO) {
        log.info("login post...");
        log.info(memberLoginDTO);
        Token jwtToken;
        try {
            jwtToken = memberService.login(memberLoginDTO);
        } catch (MemberService.UsernameNotFoundException e1) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (MemberService.WrongPasswordException e2) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }


//    @PostMapping("/refresh")
//    public ResponseEntity<Token> refreshPOST(@RequestBody RefreshTokenDTO refreshTokenDTO) {
//        log.info("refresh post...");
//        log.info(refreshTokenDTO);
//        try {
//            // 리프레시 토큰의 유효성 검사
//        } catch () { // 리프레시 토큰 기간 만료
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        return ResponseEntity.status(HttpStatus.OK).body(token); // 액세스 토큰과 리프레시 토큰 재발급
//    }



}
