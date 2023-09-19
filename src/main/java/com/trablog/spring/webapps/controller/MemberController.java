package com.trablog.spring.webapps.controller;

import com.trablog.spring.webapps.dto.MemberResponseDTO;
import com.trablog.spring.webapps.security.dto.MemberJoinDTO;
import com.trablog.spring.webapps.service.MemberService;
import com.trablog.spring.webapps.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<MemberResponseDTO> joinPOST(@RequestBody MemberJoinDTO memberJoinDTO) {
        log.info("join post...");
        log.info(memberJoinDTO);
        MemberResponseDTO memberResponseDTO;
        try {
           memberResponseDTO  = memberService.join(memberJoinDTO);
        } catch (MemberService.UsernameExistException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDTO);
    }



}
