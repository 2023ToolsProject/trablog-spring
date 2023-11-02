package com.trablog.spring.webapps.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardImageDto {
    private String origFileName;
    private String filePath;
    private Long fileSize;
}
