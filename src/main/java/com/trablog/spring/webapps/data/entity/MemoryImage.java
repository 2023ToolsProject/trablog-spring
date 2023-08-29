package com.trablog.spring.webapps.data.entity;

import jakarta.persistence.*;

@Entity
public class MemoryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMORY_ID")
    private Memory memory;
}
