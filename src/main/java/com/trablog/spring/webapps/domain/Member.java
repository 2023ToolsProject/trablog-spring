package com.trablog.spring.webapps.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "roleSet")
public class Member extends BaseEntity implements UserDetails {

    @Column(nullable = false, length = 100)
    private String email;

    // 1부터 시작하여 자동으로 1씩 증가하도록 증가 전략 설정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 회원 일련번호

    @Column(length = 100)
    private String password; // 비밀번호(해쉬를 이용한 암호화를 할 것이므로 사이즈를 넉넉히)

//    @ElementCollection(fetch = FetchType.LAZY)
//    @Builder.Default
//    private Set<MemberRole> roleSet = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Column(nullable = false, length = 10, unique = true)
    private String username; // 로그인 아이디

    private String refreshToken;

    private long tokenValidTime;// 14 * 24 * 60 * 60 * 1000L; 2주

    private Date tokenIssueDate;

    public void changePassword(String password){
        this.password = password;
    }

    public void addRole(String memberRole){
        this.roles.add(memberRole);
    }

    public void setRefreshToken(String refreshToken) {this.refreshToken = refreshToken;}

    public void clearRoles() {
        this.roles.clear();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
