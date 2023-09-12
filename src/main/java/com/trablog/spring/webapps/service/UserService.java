package com.trablog.spring.webapps.service;

import com.trablog.spring.webapps.domain.RoleType;
import com.trablog.spring.webapps.domain.User;
import com.trablog.spring.webapps.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void insertUser(User user) {
        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    public User getUser(String username) {
        // 검색 결과가 없다면 빈 객체를 리턴한다.(람다식)
        User findUser = userRepository.findByUsername(username).orElseGet(()->{
            return new User();
        });
        return findUser;
    }

    @Transactional
    public User updateUser(User user) {
        User findUser = userRepository.findById(user.getId()).get();
        findUser.setUsername(user.getUsername());
        findUser.setPassword(passwordEncoder.encode(user.getPassword()));
        findUser.setEmail(user.getEmail());
        return findUser;
    }
}
