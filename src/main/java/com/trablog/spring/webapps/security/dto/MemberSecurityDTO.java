package com.trablog.spring.webapps.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User {

    private String username;
    private String password;
    private String email;

    public MemberSecurityDTO(String username, String password, String email, Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);

        this.username = username;
        this.password = password;
        this.email = email;
    }

}
