package com.trablog.spring.webapps.service;


import com.trablog.spring.webapps.data.entity.Memory;
import com.trablog.spring.webapps.data.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;

    public Page<Memory> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }
    public Memory findBoardById(Long id) {
        Memory memory = boardRepository.findById(id).orElse(new Memory());
        return memory;
    }
    public Memory save(Memory memory) {

        return boardRepository.save(memory);
    }

    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }
}
