package com.trablog.spring.webapps.domain;

import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Getter
public class BoardImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @Column(nullable = false)
    private String imageName; // 파일 원본명

    @Column(nullable = false)
    private String imagePath; // 파일 저장 경로

    private Long imageSize;

    @Builder
    public BoardImage(String imageName, String imagePath, Long imageSize) {
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.imageSize = imageSize;
    }

    public void setBoard(Board board){
        this.board = board;

        //게시글에 현재 파일이 존재하지 않는다면
        if(!board.getBoardImage().contains(this))
            // 파일 추가
            board.getBoardImage().add(this);
    }

}
