package com.trablog.spring.webapps.controller;

import com.trablog.spring.webapps.domain.*;
import com.trablog.spring.webapps.dto.BoardReturnDto;
import com.trablog.spring.webapps.dto.CreateBoardDto;
import com.trablog.spring.webapps.dto.NoImageBoardReturnDto;
import com.trablog.spring.webapps.repository.BoardImageRepository;
import com.trablog.spring.webapps.repository.BoardRepository;
import com.trablog.spring.webapps.repository.MemberRepository;
import com.trablog.spring.webapps.security.JwtAuthenticationFilter;
import com.trablog.spring.webapps.security.JwtTokenProvider;
import com.trablog.spring.webapps.security.dto.MemberSecurityDTO;
import com.trablog.spring.webapps.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final MemberRepository memberRepository;
    private final BoardImageRepository boardImageRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final FileHandler fileHandler;


    @GetMapping
    public String get() {
        return "hello";
    }

    // 검색 API
    // 사용자가 작성한 글 제목 & 내용 검색(title & content) / containing
    public ResponseEntity<?> search(@RequestParam(value="keyword") String keyword) {
        List<Board> result = boardService.searchAll(keyword);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /* Todo : 사진도 같이 리턴해야 함.*/
    // 사용자가 작성한 모든 리스트 조회
    @GetMapping("/list")
    public List<BoardReturnDto> getMemories() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MemberSecurityDTO memberSecurityDTO = ((MemberSecurityDTO) auth.getPrincipal());
        Member member = memberRepository.getWithRoles(memberSecurityDTO.getUsername()).get();
        List<Board> memories = boardService.findAll(member);
        List<BoardReturnDto> boardReturnDtos = new ArrayList<>();
        for(Board board : memories) {
            List<BoardImageReturnDto> bIreturnDtos = new ArrayList<>();
            for(BoardImage boardImage : board.getBoardImages()) {
                BoardImageReturnDto boardImageReturnDto = new BoardImageReturnDto(boardImage.getId(), boardImage.getImageName(), boardImage.getImagePath(), boardImage.getImageSize());
                bIreturnDtos.add(boardImageReturnDto);
            }
            BoardReturnDto boardReturnDto = BoardReturnDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .boardImages(bIreturnDtos)
                    .latitude(board.getLatitude())
                    .longitude(board.getLongitude())
                    .address(board.getAddress())
                    .build();
            boardReturnDtos.add(boardReturnDto);
            }
        return boardReturnDtos;
    }


    /* Todo : 수정 */
    // 사용자가 작성한 게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getMemoryById(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MemberSecurityDTO memberSecurityDTO = ((MemberSecurityDTO) auth.getPrincipal());
        Member member = memberRepository.getWithRoles(memberSecurityDTO.getUsername()).get();
        Board board = boardService.findBoardById(member, id);
        if(board.getBoardImages() != null) {
            List<BoardImageReturnDto> bIreturnDtos = new ArrayList<>();
            for (BoardImage boardImage : board.getBoardImages()) {
                BoardImageReturnDto boardImageReturnDto = new BoardImageReturnDto(boardImage.getId(), boardImage.getImageName(), boardImage.getImagePath(), boardImage.getImageSize());
                bIreturnDtos.add(boardImageReturnDto);
            }
            BoardReturnDto boardReturnDto = BoardReturnDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .boardImages(bIreturnDtos)
                    .latitude(board.getLatitude())
                    .longitude(board.getLongitude())
                    .address(board.getAddress())
                    .build();
            return new ResponseEntity<>(boardReturnDto, HttpStatus.OK);
        } else {
            NoImageBoardReturnDto noImageBoardReturnDto = NoImageBoardReturnDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .latitude(board.getLatitude())
                    .longitude(board.getLongitude())
                    .address(board.getAddress())
                    .build();
            return new ResponseEntity<>(noImageBoardReturnDto, HttpStatus.OK);
        }

    }

    // 게시글 업로드 API
    @PostMapping(value= "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> postMemory(
            @RequestPart() CreateBoardDto createBoardDto,
            @RequestPart(value="image", required=false) List<MultipartFile> files // required=false가 사진 안올려도 된다는 거 아닌가? 아니면 그냥 헤더에 추가 안해도 된다는 건가?
    ) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MemberSecurityDTO memberSecurityDTO = ((MemberSecurityDTO) auth.getPrincipal());
        String username = memberSecurityDTO.getUsername();
        Board board = boardService.save(username, createBoardDto, files);

        return getMemoryById(board.getId());
    }

    // 게시글 수정
    @PutMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE )// MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}
    @ResponseStatus(HttpStatus.OK)
    public BoardReturnDto updateContent(@PathVariable("id") Long id,
                                        @RequestPart(value = "requestDto") CreateBoardDto createBoardDto) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MemberSecurityDTO memberSecurityDTO = ((MemberSecurityDTO) auth.getPrincipal());
        Member member = memberRepository.getWithRoles(memberSecurityDTO.getUsername()).get();
        Board board = boardService.findBoardById(member, id);
        boardService.updateContent(member, id, createBoardDto);
        Board updatedBoard = boardService.findBoardById(member, id);
        List<BoardImageReturnDto> bIreturnDtos = new ArrayList<>();
        for(BoardImage boardImage : updatedBoard.getBoardImages()) {
            BoardImageReturnDto boardImageReturnDto = new BoardImageReturnDto(boardImage.getId(), boardImage.getImageName(), boardImage.getImagePath(), boardImage.getImageSize());
            bIreturnDtos.add(boardImageReturnDto);
        }
        BoardReturnDto boardReturnDto = BoardReturnDto.builder()
                .id(updatedBoard.getId())
                .title(updatedBoard.getTitle())
                .content(updatedBoard.getContent())
                .boardImages(bIreturnDtos)
                .latitude(updatedBoard.getLatitude())
                .longitude(updatedBoard.getLongitude())
                .address(updatedBoard.getAddress())
                .build();
        return boardReturnDto;
    }

//    // 사진 삭제
//    @DeleteMapping(value="/{id}/images/{imageId}", produces = MediaType.APPLICATION_JSON_VALUE )// MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}
//    @ResponseStatus(HttpStatus.OK)
//    public BoardReturnDto deleteBoardImage(
//            @PathVariable("id") Long id,
//            @PathVariable("imageId") Long imageId
//    ) throws Exception {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        MemberSecurityDTO memberSecurityDTO = ((MemberSecurityDTO) auth.getPrincipal());
//        Member member = memberRepository.getWithRoles(memberSecurityDTO.getUsername()).get();
////        Board board = boardService.findBoardById(member, id);
//        boardService.deleteBoardImage(member, imageId);
//        Board updatedBoard = boardService.findBoardById(member, id);
//        List<BoardImageReturnDto> bIreturnDtos = new ArrayList<>();
//        for(BoardImage boardImage : updatedBoard.getBoardImages()) {
//            BoardImageReturnDto boardImageReturnDto = new BoardImageReturnDto(boardImage.getId(), boardImage.getImageName(), boardImage.getImagePath(), boardImage.getImageSize());
//            bIreturnDtos.add(boardImageReturnDto);
//        }
//        BoardReturnDto boardReturnDto = BoardReturnDto.builder()
//                .id(updatedBoard.getId())
//                .title(updatedBoard.getTitle())
//                .content(updatedBoard.getContent())
//                .boardImages(bIreturnDtos)
//                .latitude(updatedBoard.getLatitude())
//                .longitude(updatedBoard.getLongitude())
//                .address(updatedBoard.getAddress())
//                .build();
//        return boardReturnDto;
//    }

    // 사진 추가
    @PostMapping(value="/{id}/images", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE )// MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}
    @ResponseStatus(HttpStatus.OK)
    public BoardReturnDto addBoardImage(
            @PathVariable("id") Long id,
            @RequestPart(value="image", required=false) List<MultipartFile> files
    ) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MemberSecurityDTO memberSecurityDTO = ((MemberSecurityDTO) auth.getPrincipal());
        Member member = memberRepository.getWithRoles(memberSecurityDTO.getUsername()).get();
        Board board = boardService.findBoardById(member, id);

        if(!CollectionUtils.isEmpty(files)) { // 전달되어 온 파일이 하나라도 존재
            List<BoardImage> boardImages = fileHandler.parseFileInfo(board, files);
            for(BoardImage boardImage : boardImages)
                board.addBoardImage(boardImageRepository.save(boardImage));
        } else { // 전달되어 온 파일 아예 x
            throw new RuntimeException();
        }

        Board updatedBoard = boardService.findBoardById(member, id);
        List<BoardImageReturnDto> bIreturnDtos = new ArrayList<>();
        for(BoardImage boardImage : updatedBoard.getBoardImages()) {
            BoardImageReturnDto boardImageReturnDto = new BoardImageReturnDto(boardImage.getId(), boardImage.getImageName(), boardImage.getImagePath(), boardImage.getImageSize());
            bIreturnDtos.add(boardImageReturnDto);
        }
        BoardReturnDto boardReturnDto = BoardReturnDto.builder()
                .id(updatedBoard.getId())
                .title(updatedBoard.getTitle())
                .content(updatedBoard.getContent())
                .boardImages(bIreturnDtos)
                .latitude(updatedBoard.getLatitude())
                .longitude(updatedBoard.getLongitude())
                .address(updatedBoard.getAddress())
                .build();
        return boardReturnDto;
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMemory(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MemberSecurityDTO memberSecurityDTO = ((MemberSecurityDTO) auth.getPrincipal());
        Member member = memberRepository.getWithRoles(memberSecurityDTO.getUsername()).get();
        boardService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
