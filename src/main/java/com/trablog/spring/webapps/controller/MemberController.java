package com.trablog.spring.webapps.controller;

import com.trablog.spring.webapps.domain.Member;
import com.trablog.spring.webapps.security.Token;
import com.trablog.spring.webapps.security.dto.MemberJoinDTO;
import com.trablog.spring.webapps.security.dto.MemberLoginDTO;
import com.trablog.spring.webapps.security.dto.MemberSecurityDTO;
import com.trablog.spring.webapps.security.dto.RefreshTokenDTO;
import com.trablog.spring.webapps.service.MemberService;
import com.trablog.spring.webapps.service.MemberServiceImpl;
import com.trablog.spring.webapps.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;

    @GetMapping("/profile")
    public ResponseEntity<Member> getMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MemberSecurityDTO memberSecurityDTO = ((MemberSecurityDTO) auth.getPrincipal());
        Member member = Member.builder()
                .email(memberSecurityDTO.getEmail())
                .password(memberSecurityDTO.getPassword())
                .username(memberSecurityDTO.getUsername())
                .build();


        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    @PostMapping("/join")
    public ResponseEntity<Token> joinPOST(@RequestBody MemberJoinDTO memberJoinDTO) {
        log.info("join post...");
        log.info(memberJoinDTO);
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


    @PostMapping("/refresh") // 리프레시 토큰 일치 검사는 액세스 토큰이 만료되었을 때 하는 것임.
    public ResponseEntity<Token> refreshPOST(@RequestBody RefreshTokenDTO refreshTokenDTO) throws MemberService.UsernameNotFoundException {
        log.info("refresh post...");
        log.info(refreshTokenDTO);
        String refreshToken = refreshTokenDTO.getRefreshToken();
        Optional<Member> member = memberService.getMemberByRefreshToken(refreshToken);
        if(member.isEmpty()){
            return new  ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String username = member.get().getUsername();
        if(refreshTokenService.CheckToken(username, refreshToken)) {
            if(!refreshTokenService.CheckTokenValidTime(username)){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  //리프레시 토큰 만료된 경우
            }
            // 토큰 일치 & 유효해도 액세스 토큰 만료되었으므로 액세스 토큰 & 리프레시 토큰 갱신
            Token token = refreshTokenService.TokenReissue(username);
            return ResponseEntity.status(HttpStatus.OK).body(token); // 액세스 토큰과 리프레시 토큰 재발급
        } else {
            // 리프레시 토큰 불일치
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }



}
