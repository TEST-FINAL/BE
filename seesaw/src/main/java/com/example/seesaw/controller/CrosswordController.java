package com.example.seesaw.controller;

import com.example.seesaw.dto.CrossWordResponseDto;
import com.example.seesaw.service.CrosswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CrosswordController {

    private final CrosswordService crosswordService;

    @GetMapping("/api/crossword")
    public ResponseEntity<List<CrossWordResponseDto>> test(){
        List<CrossWordResponseDto> crossWordResponseDtos = crosswordService.getWord();

        return ResponseEntity.ok()
                .body(crossWordResponseDtos);
    }

}
