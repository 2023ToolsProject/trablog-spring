package com.trablog.spring.webapps.repository;

import com.trablog.spring.webapps.domain.Member;
import com.trablog.spring.webapps.security.Token;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = "roles")
    @Query("select m from Member m where m.username = :username")
    Optional<Member> getWithRoles(String username);

    @Query("select m from Member m where m.username = :username")
    Optional<Member> existsByUsername(String username);



}
