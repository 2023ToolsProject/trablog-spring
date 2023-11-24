package com.trablog.spring.webapps.repository;

import com.trablog.spring.webapps.domain.BoardImage;
import com.trablog.spring.webapps.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
}
