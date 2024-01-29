package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.NoticeConverter;
import com.example.umcmatchingcenter.domain.Notice;
import com.example.umcmatchingcenter.dto.MemberDTO.LoginRequestDTO;
import com.example.umcmatchingcenter.dto.noticeDTO.NoticeRequestDTO;
import com.example.umcmatchingcenter.dto.noticeDTO.NoticeResponseDTO;
import com.example.umcmatchingcenter.service.noticeService.NoticeCommandService;
import com.example.umcmatchingcenter.service.noticeService.NoticeQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeCommandService noticeCommandService;
    private final NoticeQueryService noticeQueryService;

    @Operation(summary = "공지 등록 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @PostMapping("/notices")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<NoticeResponseDTO.AddNoticeDTO> addNotices(@RequestBody NoticeRequestDTO request, Principal principal){
        Notice notice = noticeCommandService.addNotice(request, principal.getName());
        return ApiResponse.onSuccess(NoticeConverter.toAddNoticeDTO(notice));
    }

    @Operation(summary = "공지 목록 조회 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTICE4001", description = "공지사항이 없습니다.",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class)))
    })
    @GetMapping("/notices")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<NoticeResponseDTO.NoticeListDTO> noticeList(Principal principal){
        List<Notice> noticeList = noticeQueryService.getNoticeList(principal.getName());
        return ApiResponse.onSuccess(NoticeConverter.toNoticeListDTO(noticeList));
    }

    @Operation(summary = "공지 싱세 조회 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTICE4001", description = "공지사항이 없습니다.",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class)))
    })
    @GetMapping("/notices/{noticeId}")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<NoticeResponseDTO.NoticeDetailsDTO> notice(@PathVariable(name = "noticeId") Long noticeId){
        Notice notice = noticeQueryService.getNoticeDetails(noticeId);
        return ApiResponse.onSuccess(NoticeConverter.toNoticeDetailsDTO(notice));
    }

    @Operation(summary = "공지 수정 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공")
    })
    @PatchMapping("/notices/{noticeId}")
    public ApiResponse<NoticeResponseDTO> updateNotice(@PathVariable(name = "noticeId") Long noticeId){
        return null;
    }
}
