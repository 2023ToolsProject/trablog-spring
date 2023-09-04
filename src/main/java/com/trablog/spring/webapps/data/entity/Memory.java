package com.trablog.spring.webapps.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.trablog.spring.webapps.domain.enums.MemoryType;
import jakarta.persistence.*;
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
public class Memory extends BaseTimeEntity implements Serializable {
    //클래스가 파일에 읽거나 쓸 수 있도록 하거나, 다른 서버로 보내거나 받을 수 있도록 하려면클래스가 파일에 읽거나 쓸 수 있도록 하거나, 다른 서버로 보내거나 받을 수 있도록 하기 위함

    @Id
    @Column(name = "MEMORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Member.class)
    @JoinColumn(name = "MEMBER_ID", updatable = false)
    @JsonBackReference
    private Member member;

    @OneToMany(mappedBy = "memory",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<MemoryImage> memoryImage = new ArrayList<>();

    private double latitude;

    private double longitude;

    private String address;

    public void update(Memory memory) {
        this.title = memory.getTitle();
        this.content = memory.getContent();
        this.member = memory.getMember();
        this.memoryImage = memory.getMemoryImage();
        this.latitude = memory.getLatitude();
        this.longitude = memory.getLongitude();
        this.address = memory.getAddress();
    }

    public void addMemoryImage(MemoryImage memoryImage) {
        this.memoryImage.add(memoryImage);

        // 게시글에 파일이 저장되어있지 않은 경우
        if(memoryImage.getMemory() != this)
            // 파일 저장
            memoryImage.setMemory(this);
    }
}
