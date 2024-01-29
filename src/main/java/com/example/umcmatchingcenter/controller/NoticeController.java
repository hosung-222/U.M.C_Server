package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.dto.MemberDTO.LoginRequestDTO;
import com.example.umcmatchingcenter.dto.noticeDTO.NoticeRequestDTO;
import com.example.umcmatchingcenter.dto.noticeDTO.NoticeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    @Operation(summary = "공지 등룍 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @PostMapping("/notices")
    public ApiResponse<NoticeRequestDTO> addNotices(@RequestBody NoticeRequestDTO request, Principal principal){
        return null;
    }

    @Operation(summary = "공지 목록 조회 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTICE4001", description = "공지사항이 없습니다.",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @GetMapping("/notices")
    public ApiResponse<NoticeResponseDTO> noticeList(){
        return null;
    }

    @Operation(summary = "공지 싱세 조회 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공")
    })
    @GetMapping("/notices/{noticeId}")
    public ApiResponse<NoticeResponseDTO> notice(@PathVariable(name = "noticeId") Long noticeId){
        return null;
    }

    @Operation(summary = "공지 싱세 조회 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공")
    })
    @PatchMapping("/notices/{noticeId}")
    public ApiResponse<NoticeResponseDTO> updateNotice(@PathVariable(name = "noticeId") Long noticeId){
        return null;
    }
}
