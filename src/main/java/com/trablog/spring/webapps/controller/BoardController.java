package com.trablog.spring.webapps.controller;

import com.trablog.spring.webapps.data.dto.CreateBoardDto;
import com.trablog.spring.webapps.data.entity.Board;
import com.trablog.spring.webapps.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBoards(@PageableDefault Pageable pageable, PagedResourcesAssembler<Board> assembler) {
        Page<Board> boards = boardService.findAll(pageable);
        PagedModel<EntityModel<Board>> model = assembler.toModel(boards);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postBoard(@RequestBody CreateBoardDto board) {
        Board savedBoard = boardService.save(board.create());
        return new ResponseEntity<>(savedBoard, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putBoard(@PathVariable("id") Long id, @RequestBody CreateBoardDto board) {
        Board persistBoard = boardService.findBoardById(id);
        persistBoard.update(board.create());
        Board savedBoard = boardService.save(persistBoard);
        return new ResponseEntity<>(savedBoard, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
