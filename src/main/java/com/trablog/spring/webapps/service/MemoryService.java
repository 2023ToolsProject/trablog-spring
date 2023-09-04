package com.trablog.spring.webapps.service;


import com.trablog.spring.webapps.data.dto.CreateMemoryDto;
import com.trablog.spring.webapps.data.entity.Member;
import com.trablog.spring.webapps.data.entity.Memory;
import com.trablog.spring.webapps.data.entity.MemoryImage;
import com.trablog.spring.webapps.data.repository.MemberRepository;
import com.trablog.spring.webapps.data.repository.MemoryImageRepository;
import com.trablog.spring.webapps.data.repository.MemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoryService {
    private final MemoryRepository memoryRepository;
    private final MemberRepository memberRepository;
    private final MemoryImageRepository memoryImageRepository;
    private final FileHandler fileHandler;

    public Page<Memory> findAll(Pageable pageable) {
        return memoryRepository.findAll(pageable);
    }
    public Memory findMemoryById(Long id) {
        Memory memory = memoryRepository.findById(id).orElse(new Memory());
        return memory;
    }

    public Memory save(String userName, CreateMemoryDto createMemoryDto, List<MultipartFile> files) throws Exception {
        Member member = memberRepository.getByUserName(userName);
        Memory memory = Memory.builder()
                .title(createMemoryDto.getTitle())
                .content(createMemoryDto.getContent())
                .member(member)
                .latitude(createMemoryDto.getLatitude())
                .longitude(createMemoryDto.getLongitude())
                .address(createMemoryDto.getAddress())
                .build();
        List<MemoryImage> memoryImageList = fileHandler.parseFileInfo(files);

        // 파일이 존재할 때에만 처리
        if(!memoryImageList.isEmpty()) {
            for(MemoryImage memoryImage : memoryImageList) {
                // 파일을 DB에 저장
                memory.addMemoryImage(memoryImageRepository.save(memoryImage));
            }
        }
        return memoryRepository.save(memory);
    }

    public void deleteById(Long id) {
        memoryRepository.deleteById(id);
    }
}
