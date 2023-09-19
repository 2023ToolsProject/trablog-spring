//package com.trablog.spring.webapps.domain;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//@Getter
//@Table(name = "USERS")
//public class User implements UserDetails {
//    // Primary Key와 매핑되는 식별자 변수
//    @Id
//    // 1부터 시작하여 자동으로 1씩 증가하도록 증가 전략 설정
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id; // 회원 일련번호
//
//    @Column(nullable = false, length = 10, unique = true)
//    private String username; // 로그인 아이디
//
//    @Column(length = 100)
//    private String password; // 비밀번호(해쉬를 이용한 암호화를 할 것이므로 사이즈를 넉넉히)
//
//    @Column(nullable = false, length = 100)
//    private String email;
//
////    @Column(name = "ADMIN", length = 4)
////    @Enumerated(EnumType.STRING) // 설정할 수 있는 값을 제한한다.
////    private Admin admin;
//
//    @ElementCollection(fetch = FetchType.EAGER)
//    @Builder.Default
//    private List<String> roles = new ArrayList<>();
//
//    @CreationTimestamp // 현재 시간이 기본 값으로 등록되도록 설정
//    private Timestamp createDate;
//
//    private String accessToken;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
