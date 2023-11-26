package com.trablog.spring.webapps.service;

import com.trablog.spring.webapps.domain.*;
import com.trablog.spring.webapps.dto.CreateBoardDto;
import com.trablog.spring.webapps.repository.BoardImageRepository;
import com.trablog.spring.webapps.repository.BoardRepository;
import com.trablog.spring.webapps.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        Board createdBoard = boardRepository.save(board);
        List<BoardImage> boardImageList = fileHandler.parseFileInfo(createdBoard, files);

        // 사용자가 파일을 업로드 했을 때에만 처리
        if(!boardImageList.isEmpty()) {
            for(BoardImage boardImage : boardImageList) {
                // 파일을 DB에 저장
                createdBoard.addBoardImage(boardImageRepository.save(boardImage));
            }
        }
        return createdBoard;
    }

    // 게시글 수정
    @Transactional
    public void updateContent(Member member, Long id, CreateBoardDto createBoardDto) throws Exception {
        Board board = findBoardById(member, id);
        board.update(createBoardDto.getTitle(), createBoardDto.getContent(), createBoardDto.getLatitude(), createBoardDto.getLongitude(), createBoardDto.getAddress());
        boardRepository.save(board);
    }

    //사진 추가

    // 사진 삭제
//    @Transactional
//    public void update(Member member, Long id, CreateBoardDto createBoardDto, List<MultipartFile> addFileList) throws Exception {
//        Board board = findBoardById(member, id);
//        List<BoardImage> boardImages = fileHandler.parseFileInfo(board, addFileList);
//        if(!boardImages.isEmpty()) {
//            for(BoardImage boardImage : boardImages) {
//                board.addBoardImage(boardImageRepository.save(boardImage));
//            }
//        }
//        board.update(createBoardDto.getTitle(), createBoardDto.getContent(), createBoardDto.getLatitude(), createBoardDto.getLongitude(), createBoardDto.getAddress());
//        boardRepository.save(board);
//    }
//




    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteBoardImages(id);
        boardRepository.deleteById(id);
    }

//    public void deleteImage(Member member, Long imageId) {
//        boardRepository.deleteBoardImages(member, imageId);
//    }

//    @Transactional
//    public void deleteBoardImage(Member member, Long imageId) {
//        boardRepository.deleteBoardImages(member, imageId);
//    }
}
