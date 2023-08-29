package com.trablog.spring.webapps.data.entity;

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
public class Memory extends BaseTimeEntity implements Serializable {
    //클래스가 파일에 읽거나 쓸 수 있도록 하거나, 다른 서버로 보내거나 받을 수 있도록 하려면클래스가 파일에 읽거나 쓸 수 있도록 하거나, 다른 서버로 보내거나 받을 수 있도록 하기 위함

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String subTitle; // Todo: 삭제

    @Column
    private String content;

    @Column
    private MemoryType memoryType; // Todo: 삭제

    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "memory")
    private List<MemoryImage> memoryImage = new ArrayList<>();

    @Column
    private double latitude;

    @Column
    private double longitude;

    @Column
    private String address;

    @Column
    private String roadAddress;

    @Column
    private String buildingName;



    @Builder
    public Memory(Long id, String title, String subTitle, String content, MemoryType memoryType, Member member) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.memoryType = memoryType;
        this.member = member;
    }

    public void update(Memory memory) {
        this.title = memory.getTitle();
        this.subTitle = memory.getSubTitle();
        this.content = memory.getContent();
        this.memoryType = memory.getMemoryType();
    }

}
