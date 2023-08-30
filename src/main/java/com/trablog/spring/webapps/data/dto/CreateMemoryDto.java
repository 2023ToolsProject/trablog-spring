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
//    private String subTitle;
    private String content;
//    private MemoryType memoryType;
    private Member member;
    private List<MemoryImage> memoryImage = new ArrayList<>();
    private double latitude;
    private double longitude;
    private String address;
    private String roadAddress;
    private String buildingName;
    public Memory create(){
        Memory savedMemory = Memory.builder()
                .title(this.getTitle())
                .content(this.getContent())
                .member(this.getMember())
                .memoryImage(this.getMemoryImage())
                .latitude(this.getLatitude())
                .longitude(this.getLongitude())
                .address(this.getAddress())
                .roadAddress(this.getRoadAddress())
                .buildingName(this.getBuildingName())
                .build();
        return savedMemory;
    }
}
