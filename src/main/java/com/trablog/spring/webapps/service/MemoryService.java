package com.trablog.spring.webapps.service;


import com.trablog.spring.webapps.data.entity.Memory;
import com.trablog.spring.webapps.data.repository.MemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoryService {
    private final MemoryRepository memoryRepository;
    private final MemberService memberService;

    public Page<Memory> findAll(Pageable pageable) {
        return memoryRepository.findAll(pageable);
    }
    public Memory findMemoryById(Long id) {
        Memory memory = memoryRepository.findById(id).orElse(new Memory());
        return memory;
    }
    public Memory save(Memory memory) {

        return memoryRepository.save(memory);
    }

    public void deleteById(Long id) {
        memoryRepository.deleteById(id);
    }
}
