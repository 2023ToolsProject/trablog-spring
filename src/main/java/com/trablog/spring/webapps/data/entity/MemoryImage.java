package com.trablog.spring.webapps.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MemoryImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMORY_ID")
    private Memory memory;

    @Column(nullable = false)
    private String imageName; // 파일 원본명

    @Column(nullable = false)
    private String imagePath; // 파일 저장 경로

    private Long imageSize;

    @Builder
    public MemoryImage(String imageName, String imagePath, Long imageSize) {
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.imageSize = imageSize;
    }

    public void setMemory(Memory memory){
        this.memory = memory;

        //게시글에 현재 파일이 존재하지 않는다면
        if(!memory.getMemoryImage().contains(this))
            // 파일 추가
            memory.getMemoryImage().add(this);
    }

}
