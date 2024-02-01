package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.NoticeConverter;
import com.example.umcmatchingcenter.domain.Notice;
import com.example.umcmatchingcenter.dto.noticeDTO.NoticeRequestDTO;
import com.example.umcmatchingcenter.dto.noticeDTO.NoticeResponseDTO;
import com.example.umcmatchingcenter.service.noticeService.NoticeCommandService;
import com.example.umcmatchingcenter.service.noticeService.NoticeQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "공지 API")
public class NoticeController {

    private final NoticeCommandService noticeCommandService;
    private final NoticeQueryService noticeQueryService;

    @Operation(summary = "공지 등록 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @PostMapping(value = "/notices", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<NoticeResponseDTO.AddNoticeDTO> addNotices(@RequestPart NoticeRequestDTO.AddNoticeDTO request,
                                                                  @RequestPart(required = false) List<MultipartFile> imageList,
                                                                  Principal principal){
        Notice notice = noticeCommandService.addNotice(request,imageList, principal.getName());
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

    @Operation(summary = "공지 상세 조회 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTICE4001", description = "공지사항이 없습니다.",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class)))
    })
    @GetMapping("/notices/{noticeId}")
    @Parameters({
            @Parameter(name = "noticeId", description = "공지사항 아이디(PathVariable)")
    })
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<NoticeResponseDTO.NoticeDetailsDTO> notice(@PathVariable(name = "noticeId") Long noticeId){
        Notice notice = noticeQueryService.getNoticeDetails(noticeId);
        return ApiResponse.onSuccess(NoticeConverter.toNoticeDetailsDTO(notice));
    }

    @Operation(summary = "공지 수정 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTICE4001", description = "공지사항이 없습니다.",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class)))
    })
    @PatchMapping("/notices/{noticeId}")
    @Parameters({
            @Parameter(name = "noticeId", description = "공지사항 아이디(PathVariable)")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<NoticeResponseDTO.UpdateNoticeDetailsDTO> updateNotice(@PathVariable(name = "noticeId") Long noticeId,@RequestBody NoticeRequestDTO.UpdateNoticeDTO request){
        Notice notice = noticeCommandService.updateNotice(noticeId, request);
        return ApiResponse.onSuccess(NoticeConverter.toUpdateNoticeDetailsDTO(notice));
    }
}
