//package com.trablog.spring.webapps.persistence;
//
//import com.trablog.spring.webapps.domain.User;
//import jakarta.persistence.EntityManager;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface UserRepository extends JpaRepository<User, Integer> {
//    private final EntityManager em;
//    // SELECT * FROM user WHERE username = ?1;
//    Optional<User> findByUsername(String username);
//
//    boolean existsById(String username);
//}
