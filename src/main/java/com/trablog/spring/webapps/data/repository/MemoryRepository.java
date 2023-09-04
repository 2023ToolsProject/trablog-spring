package com.trablog.spring.webapps.data.repository;

import com.trablog.spring.webapps.data.entity.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MemoryRepository extends JpaRepository<Memory, Long> {

}
