package com.trablog.spring.webapps.repository;

import com.trablog.spring.webapps.domain.Board;
import com.trablog.spring.webapps.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select b from Board b where b.member =:member")
    public List<Board> selectByUsername(Member member);

    @Query("select b from Board b where b.member =:name and b.id =:id")
    public Board findByUsername(Member member, Long id);

    @Query("delete from Board b where b.member =:member and b.id =:id")
    public void deleteByUsername(Member member, Long id);

    @Query("select b from Board b where b.title LIKE %:keyword% OR b.content LIKE %:keyword%")
    public List<Board> searchAll(String keyword);
}
