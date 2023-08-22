package com.trablog.spring.webapps.data.repository;

import com.trablog.spring.webapps.data.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member getByUsername(String userName);

}
