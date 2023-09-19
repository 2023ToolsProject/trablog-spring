package com.trablog.spring.webapps.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "roleSet")
public class Member extends BaseEntity {

    @Column(nullable = false, length = 10, unique = true)
    private String username; // 로그인 아이디

    @Column(length = 100)
    private String password; // 비밀번호(해쉬를 이용한 암호화를 할 것이므로 사이즈를 넉넉히)

    @Column(nullable = false, length = 100)
    private String email;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    @Id
    // 1부터 시작하여 자동으로 1씩 증가하도록 증가 전략 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 회원 일련번호

    public void changePassword(String password){
        this.password = password;
    }

    public void addRole(MemberRole memberRole){
        this.roleSet.add(memberRole);
    }

    public void clearRoles() {
        this.roleSet.clear();
    }
}
