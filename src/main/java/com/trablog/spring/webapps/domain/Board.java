package com.trablog.spring.webapps.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseTimeEntity implements Serializable { // serializable: 객체를 파일로
    //저장하거나 네트워크를 통해 전송하고, 나중에 역직렬화 하여 객체를 다시 복원할 수 있음

    @Id
    @Column(name = "BOARD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(cascade = CascadeType.MERGE , targetEntity = Member.class) // CascadeType.MERGE는 cascade 속성 중 하나로, 엔티티의 상태가 변경될 때 연관된 엔티티에도 해당 변경 사항을 전파하는 역할
    @JoinColumn(name = "MEMBER_ID", updatable = false)
    @JsonBackReference // 순환참조 무한루프 방지
    private Member member;

    @OneToMany(mappedBy = "board",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<BoardImage> boardImage = new ArrayList<>();

    private double latitude;

    private double longitude;

    private String address;

    public void update(Board board) {
        this.title = title;
        this.content = content;
        this.boardImage = boardImage;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public void addBoardImage(BoardImage boardImage) {
        this.boardImage.add(boardImage);

        // 게시글에 파일이 저장되어있지 않은 경우
        if(boardImage.getBoard() != this) {
            // 파일 저장
            boardImage.setBoard(this);
        }

    }
}

