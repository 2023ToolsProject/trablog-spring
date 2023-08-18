package com.trablog.spring.webapps.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo; // 식별자

    @Column(nullable = false, length = 20)
    private String userName; //ID

    private int loginType; // 로그인 타입

    @Column( length = 12)
    private String nickName; //닉네임

    @Column(length = 100)
    private String imageUrl; //프로필 사진 url

    @Column(length = 300)
    private String introduction; //자기소개

    private LocalDateTime joinDate; //가입일

    private LocalDateTime updateDate; //개인정보 수정날짜

    @Column(length = 128)
    private String salt; //솔트값

    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    @NotBlank(message = "이메일 주소를 입력하세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String password; //비밀번호
    private LocalDateTime pwUpdateDate; //비밀번호 수정일

    private String accessToken;
}

