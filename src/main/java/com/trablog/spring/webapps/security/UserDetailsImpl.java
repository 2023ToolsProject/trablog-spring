package com.trablog.spring.webapps.security;

import com.trablog.spring.webapps.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    User user;


    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되지 않았는지 반환
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

    // 계정이 활성화되었는지 반환
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 계정이 가지고 있는 권한 목록 저장하여 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> roleList = new ArrayList<>();

        roleList.add(() -> {
            return "ROLE_" + user.getRole();
        });

        return roleList;
    }
}
