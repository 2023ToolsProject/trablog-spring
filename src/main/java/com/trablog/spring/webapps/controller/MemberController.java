package com.trablog.spring.webapps.controller;

import com.trablog.spring.webapps.data.dto.ChangeMemberNameDto;
import com.trablog.spring.webapps.data.dto.MemberDto;
import com.trablog.spring.webapps.data.dto.MemberResponseDto;
import com.trablog.spring.webapps.service.MemberService;
//import jakarta.validation.Valid;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping()
    public ResponseEntity<List<MemberResponseDto>> getMember() {
        List<MemberResponseDto> memberResponseDto = memberService.getMember();

        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }
    @GetMapping("/{userNo}")
    public ResponseEntity<MemberResponseDto> getMemberById(@PathVariable Long userNo) {
        MemberResponseDto memberResponseDto = memberService.getMemberById(userNo);

        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }
//    @GetMapping("/signup")
//    public String singup(MemberCreateForm memberCreateForm) {
//        return "signup_form";
//    }

    @PostMapping()
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberDto memberDto) {
        MemberResponseDto memberResponseDto = memberService.saveMember(memberDto);

        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }

    @PostMapping("/signup")
    public String signUp(@Valid MemberDto memberDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            /* 회원가입 실패 시 입력 데이터 값 유지 */

            /* 유효성 검사를 통과하지 못 한 필드와 메시지 핸들링 */
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put("valid_" + error.getField(), error.getDefaultMessage());
                // 에러 코드 리턴
            }
            /* 회원가입 페이지로 리턴 ? 에러 코드 리턴? */

            return "/member/signup";
        }

        // 회원가입 성공 시
        // 비밀번호 암호화
        // create member
        String accessToken = "";
        String randInt = "";
        String status = String.valueOf(createMember(memberDto));
        if(status.substring(0, 4).equals("201")) {
            randInt = String.valueOf((int)((Math.random() * 10000)%10)) +
                    String.valueOf((int)((Math.random() * 10000)%10)) +
                    String.valueOf((int)((Math.random() * 10000)%10)) +
                    String.valueOf((int)((Math.random() * 10000)%10));
            accessToken = memberDto.getUserName() + randInt;
        }
        // DB의 해당 레코드의 accessToken 필드에 저장
        return accessToken;
    }

//    @PostMapping("/signup")
//    public ResponseEntity<MemberResponseDto> singup(@Valid MemberCreateForm memberCreateForm){ //, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
//        }
//
//        if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())) {
//            bindingResult.rejectValue("password2", "passwordInCorrect",
//                    "2개의 패스워드가 일치하지 않습니다.");
//            return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
//        }
//
//        try {
//            MemberResponseDto responseDto = memberService.createMember(memberCreateForm.getUsername(), memberCreateForm.getEmail(), memberCreateForm.getPassword1());
//        } catch(DataIntegrityViolationException e) {
//            e.printStackTrace();
////            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
//            throw new RuntimeException();
//        } catch (Exception e) {
//            e.printStackTrace();
////            bindingResult.reject("signupFailed", e.getMessage());
//            throw new RuntimeException();
//        }
//        MemberResponseDto responseDto = memberService.createMember(memberCreateForm.getUsername(), memberCreateForm.getEmail(), memberCreateForm.getPassword1());
//        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
//    }

    @PutMapping()
    public ResponseEntity<MemberResponseDto> changeMemberName(
            @RequestBody ChangeMemberNameDto changeMemberNameDto) throws Exception {
        MemberResponseDto memberResponseDto = memberService.changeMemberName(
                changeMemberNameDto.getUserNo(),
                changeMemberNameDto.getUserName());

        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteMember(Long userNo) throws Exception {
        memberService.deleteMember(userNo);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
}
