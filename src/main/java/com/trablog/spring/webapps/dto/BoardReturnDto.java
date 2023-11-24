package com.trablog.spring.webapps.dto;

import com.trablog.spring.webapps.domain.BoardImage;
import com.trablog.spring.webapps.domain.BoardImageReturnDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BoardReturnDto {
    private Long id;
    private String title;
    private String content;
    private List<BoardImageReturnDto> boardImages;
    private double latitude;
    private double longitude;
    private String address;
}
