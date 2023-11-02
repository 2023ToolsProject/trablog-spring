package com.trablog.spring.webapps.dto;

import com.trablog.spring.webapps.domain.Board;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBoardDto {
    private String title;
    private String content;
    private double latitude;
    private double longitude;
    private String address;
    public Board create(){
        Board savedBoard = Board.builder()
                .title(this.getTitle())
                .content(this.getContent())
                .latitude(this.getLatitude())
                .longitude(this.getLongitude())
                .address(this.getAddress())
                .build();
        return savedBoard;
    }
}
