package com.trablog.spring.webapps.data.repository;

import com.trablog.spring.webapps.data.entity.Memory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Memory, Long> {
}
