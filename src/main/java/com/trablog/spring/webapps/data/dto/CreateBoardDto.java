package com.trablog.spring.webapps.data.dto;

import com.trablog.spring.webapps.data.entity.Board;
import com.trablog.spring.webapps.domain.enums.BoardType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBoardDto {
    private String title;
    private String subTitle;
    private String content;
    private BoardType boardType;
    private Long memberId;
    public Board create(){
        Board savedBoard = Board.builder()
                .title(this.getTitle())
                .boardType(this.getBoardType())
                .content(this.getContent())
                .subTitle(this.getSubTitle())
                .build();
        return savedBoard;
    }
}
