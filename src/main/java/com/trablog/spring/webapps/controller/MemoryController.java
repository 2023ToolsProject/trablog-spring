package com.trablog.spring.webapps.controller;

import com.trablog.spring.webapps.config.security.JwtAuthenticationFilter;
import com.trablog.spring.webapps.data.dto.CreateMemoryDto;
import com.trablog.spring.webapps.data.entity.Member;
import com.trablog.spring.webapps.data.entity.Memory;
import com.trablog.spring.webapps.service.MemberService;
import com.trablog.spring.webapps.service.MemoryService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memories")
public class MemoryController {

    private final MemoryService memoryService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMemories(@PageableDefault Pageable pageable, PagedResourcesAssembler<Memory> assembler) {
        Page<Memory> memories = memoryService.findAll(pageable);
        PagedModel<EntityModel<Memory>> model = assembler.toModel(memories);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getMemoryById(@PathVariable("id") Long id) {
        Memory memory = memoryService.findMemoryById(id);
        return new ResponseEntity<>(memory, HttpStatus.OK);
    }


//    @PostMapping
////    public ResponseEntity<?> postMemory(@RequestBody CreateMemoryDto memory) { //인자 files, member 추가
////        // userName 토큰에서 추출
////        String userName = jwtAuthenticationFilter.getUserName();
////        Memory savedMemory = memoryService.save(userName, memory.create()); //인자 files, member 추가
////        return new ResponseEntity<>(savedMemory, HttpStatus.CREATED);
////    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Memory postMemory(
            @RequestPart(value="image", required=false) List<MultipartFile> files,
            @RequestPart(value = "requestDto") CreateMemoryDto createMemoryDto
    ) throws Exception {
        String userName = jwtAuthenticationFilter.getUserName();
        return memoryService.save(userName, createMemoryDto, files);
    }
//    @PutMapping("/{id}")
//    public ResponseEntity<?> putMemory(@PathVariable("id") Long id, @RequestBody CreateMemoryDto memory) {
//        Memory persistMemory = memoryService.findMemoryById(id);
//        persistMemory.update(memory.create());
//        Memory savedMemory = memoryService.save(persistMemory);
//        return new ResponseEntity<>(savedMemory, HttpStatus.OK);
//    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Memory putMemory(
            @RequestPart(value="id") Long id,
            @RequestPart(value="image", required=false) List<MultipartFile> files,
            @RequestPart(value = "requestDto") CreateMemoryDto createMemoryDto
    ) throws Exception {
        String userName = jwtAuthenticationFilter.getUserName();
        Memory persistMemory = memoryService.findMemoryById(id);
        persistMemory.update(createMemoryDto.create());
        Memory savedMemory = memoryService.save(userName, createMemoryDto, files);
        return savedMemory;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMemory(@PathVariable("id") Long id) {
        memoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
