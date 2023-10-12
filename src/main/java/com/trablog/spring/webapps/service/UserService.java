//package com.trablog.spring.webapps.service;
//
//import com.trablog.spring.webapps.domain.Admin;
//import com.trablog.spring.webapps.domain.User;
//import com.trablog.spring.webapps.persistence.UserRepository;
//import jakarta.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Service
//public class UserService {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    private final EntityManager em;
//
//    @Transactional
//    public void insertUser(User user) {
//        // 비밀번호 암호화
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
////        user.setRole(Admin.USER);
//        userRepository.save(user);
//    }
//
////    public Optional<User> getUser(String username) {
////        // 검색 결과가 없다면 빈 객체를 리턴한다.(람다식)
////        Optional<User> findUser = userRepository.findByUsername(username);
////        return findUser;
////    }
//    public User getUserByUsername(String username) {
//        // 검색 결과가 없다면 빈 객체를 리턴한다.(람다식)
//        User findUser = userRepository.findByUsername(username).orElseGet(()->{
//            return new User();
//        });
//        return findUser;
//    }
//
//    public Optional<User> getUser(String username) {
//        Optional<User> findUser = Optional.of(userRepository.findByUsername(username)).orElseGet(()-> {
//            return null;
//        });
//        return findUser;
//    }
//
//    @Transactional
//    public User updateUser(User user) {
//        User findUser = userRepository.findById(user.getId()).get();
//        findUser.setUsername(user.getUsername());
//        findUser.setPassword(passwordEncoder.encode(user.getPassword()));
//        findUser.setEmail(user.getEmail());
//        return findUser;
//    }
//
//    public boolean existByUsername(String username) {
//        List<User> users = em.createQuery("select u from User u where u.username =: username", User.class)
//                .setParameter("username", username)
//                .getResultList();
//        return users.stream().findAny().isPresent();
//    }
//}
