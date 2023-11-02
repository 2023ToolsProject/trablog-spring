package com.trablog.spring.webapps.repository;

import com.trablog.spring.webapps.domain.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
}
