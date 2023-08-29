package com.trablog.spring.webapps.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="member")
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo; // 식별자

    @Column(nullable = false, unique = true)
    private String userName; //ID

    private int loginType; // 로그인 타입

    //@Column(nullable = false)
    private String nickName; //닉네임

    @Column(length = 100)
    private String imageUrl; //프로필 사진 url

    @Column(length = 300)
    private String introduction; //자기소개

    private LocalDateTime joinDate; //가입일

    private LocalDateTime updateDate; //개인정보 수정날짜

    /*
    @Column(length = 128)
    private String salt; //솔트값
    */

    /*
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    //@NotBlank(message = "이메일 주소를 입력하세요.")
    private String email;
     */

    @NotBlank(message = "비밀번호를 입력해주세요.")
    //@Pattern(regexp = "^(?=.*\\\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password; //비밀번호
    private LocalDateTime pwUpdateDate; //비밀번호 수정일

    private String accessToken;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public String getUserName(){
        return this.userName;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.userName;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}

