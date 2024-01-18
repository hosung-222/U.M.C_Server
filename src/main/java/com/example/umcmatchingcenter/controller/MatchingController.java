package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.MatchingConverter;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;
import com.example.umcmatchingcenter.dto.matchingDto.MatchingResponseDTO;
import com.example.umcmatchingcenter.service.matchingService.MatchingQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Matching API")
@RequestMapping("/matchings")
public class MatchingController {
    private final MatchingQueryService matchingQueryService;

    // 프로젝트 목록 조회
    @GetMapping("")
    @Operation(summary = "특정 지부의 현재 매칭 중인 프로젝트 목록 조회 API")
    @ApiResponses({ // API 응답
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호 (1부터 시작, 15개를 기준으로 페이징)")
    })
    public ApiResponse<MatchingResponseDTO.MatchingProjectListDTO> getProjectList(@RequestParam(name = "page") Integer page){
        List<Project> projectList = matchingQueryService.getProjectList(ProjectStatus.PROCEEDING, page - 1);
        return ApiResponse.onSuccess(MatchingConverter.toProjectPreViewListDTO(projectList));
    }

    // 프로젝트 상세 조회
    @GetMapping("/{projectId}")
    @Operation(summary = "매칭 프로젝트 상세 조회 API")
    @ApiResponses({ // API 응답
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "projectId", description = "프로젝트 아이디")
    })
    public ApiResponse<MatchingResponseDTO.MatchingProjectDTO> getProject(@PathVariable(name = "projectId") Long projectId){
        Project project = matchingQueryService.getProjectDetail(projectId);
        return ApiResponse.onSuccess(MatchingConverter.toMatchingProjectDetailDTO(project));
    }
}
