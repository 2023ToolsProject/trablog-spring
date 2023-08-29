package com.trablog.spring.webapps.data.repository;

import com.trablog.spring.webapps.data.entity.Memory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoryRepository extends JpaRepository<Memory, Long> {
}
