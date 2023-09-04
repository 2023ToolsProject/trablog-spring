package com.trablog.spring.webapps.data.dto;

import com.trablog.spring.webapps.data.entity.Member;
import com.trablog.spring.webapps.data.entity.Memory;
import com.trablog.spring.webapps.data.entity.MemoryImage;
import com.trablog.spring.webapps.domain.enums.MemoryType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateMemoryDto {
    private String title;
    private String content;
    private double latitude;
    private double longitude;
    private String address;
    public Memory create(){
        Memory savedMemory = Memory.builder()
                .title(this.getTitle())
                .content(this.getContent())
                .latitude(this.getLatitude())
                .longitude(this.getLongitude())
                .address(this.getAddress())
                .build();
        return savedMemory;
    }
}
