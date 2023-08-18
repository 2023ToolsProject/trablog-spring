package com.trablog.spring.webapps.service;


import com.trablog.spring.webapps.data.dto.CreateBoardDto;
import com.trablog.spring.webapps.data.entity.Board;
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

    public Page<Board> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }
    public Board findBoardById(Long id) {
        Board board = boardRepository.findById(id).orElse(new Board());
        return board;
    }
    public Board save(Board board) {

        return boardRepository.save(board);
    }

    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }
}
