package com.trablog.spring.webapps.repository;

import com.trablog.spring.webapps.domain.Board;
import com.trablog.spring.webapps.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select distinct b from Board b left join fetch b.boardImages bi where b.member = :member")
    public List<Board> selectByUsername(Member member);

    @Query("select distinct b from Board b left join fetch b.boardImages bi where b.member = :member and b.id = :id")
    public Board findByUsername(Member member, Long id);


    @Modifying
    @Query("update Board b set b.title =:title, b.content =:content, b.boardImages =:boardImages, b.latitude =:latitude, b.longitude =:longitude, b.address =:address")
    public void update(String title, String content, double latitude, double longitude, String address);

    @Modifying
    @Query("delete from Board b where b.member =:member and b.id =:id")
    public void deleteByUsername(Member member, Long id);

    @Modifying
    @Query("delete from BoardImage bi where bi.board.id = :id")
    public void deleteBoardImages(Long id);

    @Query("select b from Board b where b.title LIKE %:keyword% OR b.content LIKE %:keyword%")
    public List<Board> searchAll(String keyword);




}
