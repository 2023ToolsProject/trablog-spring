package com.trablog.spring.webapps.data.dto;

import com.trablog.spring.webapps.data.entity.Memory;
import com.trablog.spring.webapps.domain.enums.MemoryType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMemoryDto {
    private String title;
    private String subTitle;
    private String content;
    private MemoryType memoryType;
    private Long memberId;
    public Memory create(){
        Memory savedMemory = Memory.builder()
                .title(this.getTitle())
                .memoryType(this.getMemoryType())
                .content(this.getContent())
                .subTitle(this.getSubTitle())
                .build();
        return savedMemory;
    }
}
