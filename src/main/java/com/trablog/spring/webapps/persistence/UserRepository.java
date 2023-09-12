package com.trablog.spring.webapps.persistence;

import com.trablog.spring.webapps.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    // SELECT * FROM user WHERE username = ?1;
    Optional<User> findByUsername(String username);
}
