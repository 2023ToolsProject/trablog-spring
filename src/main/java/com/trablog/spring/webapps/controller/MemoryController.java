package com.trablog.spring.webapps.controller;

import com.trablog.spring.webapps.data.dto.CreateMemoryDto;
import com.trablog.spring.webapps.data.entity.Memory;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memories")
public class MemoryController {

    private final MemoryService memoryService;

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


    @PostMapping
    public ResponseEntity<?> postMemory(@RequestBody CreateMemoryDto memory) {
        Memory savedMemory = memoryService.save(memory.create());
        return new ResponseEntity<>(savedMemory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putMemory(@PathVariable("id") Long id, @RequestBody CreateMemoryDto memory) {
        Memory persistMemory = memoryService.findMemoryById(id);
        persistMemory.update(memory.create());
        Memory savedMemory = memoryService.save(persistMemory);
        return new ResponseEntity<>(savedMemory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMemory(@PathVariable("id") Long id) {
        memoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
