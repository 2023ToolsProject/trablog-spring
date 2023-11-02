package com.trablog.spring.webapps.controller;

import com.trablog.spring.webapps.domain.Board;
import com.trablog.spring.webapps.domain.Member;
import com.trablog.spring.webapps.dto.CreateBoardDto;
import com.trablog.spring.webapps.security.JwtAuthenticationFilter;
import com.trablog.spring.webapps.security.JwtTokenProvider;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final JwtTokenProvider jwtTokenProvider;


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


    // 사용자가 작성한 모든 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<?> getMemories() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) auth.getPrincipal();
        List<Board> memories = boardService.findAll(member);
        return new ResponseEntity<>(memories, HttpStatus.OK);
    }

    // 사용자가 작성한 게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getMemoryById(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) auth.getPrincipal();
        Board board = boardService.findBoardById(member, id);
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    // 게시글 업로드 API
    /// 사진 업로드

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Board postMemory(
            @RequestPart(value="image", required=false) List<MultipartFile> files,
            @RequestPart(value = "requestDto") CreateBoardDto createBoardDto
    ) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) auth.getPrincipal();
        String username = member.getUsername();
        return boardService.save(username, createBoardDto, files);
    }

//     게시글 수정
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Board putMemory(
            @RequestPart(value="id") Long id,
            @RequestPart(value="image", required=false) List<MultipartFile> files,
            @RequestPart(value = "requestDto") CreateBoardDto createBoardDto
    ) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) auth.getPrincipal();
        String username = member.getUsername();
        Board persistMemory = boardService.findBoardById(member, id);
        persistMemory.update(createBoardDto.create());
        Board savedMemory = boardService.save(username, createBoardDto, files);
        return savedMemory;
    }

//     게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMemory(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) auth.getPrincipal();
        boardService.deleteById(member, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
