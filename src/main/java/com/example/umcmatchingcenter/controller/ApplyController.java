package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.service.applyService.ApplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "지원 API")
@RequestMapping("/apply")
public class ApplyController {

    private final ApplyService applyService;

    @PostMapping("/{projectId}")
    @Operation(summary = "해당 프로젝트에 지원하기")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "APPLY4001", description = "해당 파트는 모집 완료입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "APPLY4002", description = "이미 지원하였습니다."),
    })
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<String> apply(@PathVariable Long projectId) {
        applyService.apply(projectId);
        return ApiResponse.onSuccess("지원 완료");
    }
}
