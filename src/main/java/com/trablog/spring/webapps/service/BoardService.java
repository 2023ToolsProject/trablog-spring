package com.trablog.spring.webapps.service;

import com.trablog.spring.webapps.domain.Board;
import com.trablog.spring.webapps.domain.BoardImage;
import com.trablog.spring.webapps.domain.FileHandler;
import com.trablog.spring.webapps.domain.Member;
import com.trablog.spring.webapps.dto.CreateBoardDto;
import com.trablog.spring.webapps.repository.BoardImageRepository;
import com.trablog.spring.webapps.repository.BoardRepository;
import com.trablog.spring.webapps.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.io.File;


@Service
@RequiredArgsConstructor
public class BoardService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;
    private final FileHandler fileHandler;


    public List<Board> searchAll(String keyword) {
        return boardRepository.searchAll(keyword);
    }
    public List<Board> findAll(Member member) {
        // 작성자 id가 username과 일치하는 것만 보여주기
        return boardRepository.selectByUsername(member);
    }

    public Board findBoardById(Member member, Long id) {
        Board board = boardRepository.findByUsername(member, id);
        return board;
    }

    public Board save(String username, CreateBoardDto createMemoryDto, List<MultipartFile> files) throws Exception {
        Optional<Member> member = memberRepository.getWithRoles(username);
        Board board = Board.builder()
                .title(createMemoryDto.getTitle())
                .content(createMemoryDto.getContent())
                .member(member.get())
                .latitude(createMemoryDto.getLatitude())
                .longitude(createMemoryDto.getLongitude())
                .address(createMemoryDto.getAddress())
                .build();
        List<BoardImage> boardImageList = fileHandler.parseFileInfo(files);

        // 파일이 존재할 때에만 처리
        if(!boardImageList.isEmpty()) {
            for(BoardImage boardImage : boardImageList) {
                // 파일을 DB에 저장
                board.addBoardImage(boardImageRepository.save(boardImage));
            }
        }
        return boardRepository.save(board);
    }

    public void deleteById(Member member, Long id) {
        boardRepository.deleteByUsername(member, id);
    }

}
