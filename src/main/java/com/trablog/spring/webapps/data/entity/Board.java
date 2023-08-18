package com.trablog.spring.webapps.data.entity;

import com.trablog.spring.webapps.domain.enums.BoardType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class Board extends BaseTimeEntity implements Serializable {
    //클래스가 파일에 읽거나 쓸 수 있도록 하거나, 다른 서버로 보내거나 받을 수 있도록 하려면클래스가 파일에 읽거나 쓸 수 있도록 하거나, 다른 서버로 보내거나 받을 수 있도록 하기 위함

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String subTitle;

    @Column
    private String content;

    @Column
    private BoardType boardType;

    @ManyToOne
    private Member member;

    @Builder
    public Board(Long id, String title, String subTitle, String content, BoardType boardType, Member member) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.boardType = boardType;
        this.member = member;
    }

    public void update(Board board) {
        this.title = board.getTitle();
        this.subTitle = board.getSubTitle();
        this.content = board.getContent();
        this.boardType = board.getBoardType();
    }

}
