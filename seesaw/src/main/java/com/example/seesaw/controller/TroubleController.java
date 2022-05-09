package com.example.seesaw.controller;

import com.example.seesaw.dto.TroubleRequestDto;
import com.example.seesaw.dto.TroubleResponseDto;
import com.example.seesaw.repository.TroubleRepository;
import com.example.seesaw.security.UserDetailsImpl;
import com.example.seesaw.service.TroubleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TroubleController {
    private final TroubleService troubleService;
    private final TroubleRepository troubleRepository;

    //고민글 등재
    @PostMapping("/api/trouble")
    public ResponseEntity<String> registerTrouble(
            @RequestPart(value = "troubleRequestDto") TroubleRequestDto troubleRequestDto,
            @RequestPart(value = "files") List<MultipartFile> files,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        troubleService.registerTrouble(troubleRequestDto, files, userDetails.getUser());
        return ResponseEntity.ok()
                .body("고민글 등재완료");
    }

    //고민글 수정 시 고민글 조회
    @GetMapping("api/trouble/{troubleId}")
    public ResponseEntity<TroubleResponseDto> updateTrouble(@PathVariable Long troubleId){
        TroubleResponseDto troubleResponseDto = troubleService.findTrouble(troubleId);
        return ResponseEntity.ok()
                .body(troubleResponseDto);
    }
    //고민글 수정
    @PutMapping("api/trouble/{troubleId}")
    public ResponseEntity<String> updateTrouble(
            @RequestPart(value = "troubleRequestDto") TroubleRequestDto troubleRequestDto,
            @RequestPart(value = "files") List<MultipartFile> files,
            @PathVariable Long troubleId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){

        troubleService.updateTrouble(troubleRequestDto, files, troubleId, userDetails.getUser());
        return ResponseEntity.ok()
                .body("고민글 수정완료");
    }
    //고민글 삭제
    @DeleteMapping("api/trouble/{troubleId}")
    public ResponseEntity<String> deleteTrouble(@PathVariable Long troubleId){
        troubleRepository.deleteById(troubleId);
        return ResponseEntity.ok()
                .body("고민글 삭제완료");
    }
}
