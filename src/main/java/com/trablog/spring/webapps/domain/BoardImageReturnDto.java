package com.trablog.spring.webapps.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class BoardImageReturnDto {
    private Long id;
    private String origFileName;
    private String filePath;
    private Long fileSize;

    public BoardImageReturnDto(Long id, String origFileName, String filePath, Long fileSize) {
        this.id = id;
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
