package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.matching.MatchingConverter;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingRequestDTO;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingResponseDTO;
import com.example.umcmatchingcenter.service.matchingService.MatchingCommandService;
import com.example.umcmatchingcenter.service.matchingService.MatchingQueryService;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import com.example.umcmatchingcenter.validation.annotation.ExistMember;
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

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Matching API")
@RequestMapping("/matchings")
public class MatchingController {

    private final MatchingQueryService matchingQueryService;
    private final MemberQueryService memberQueryService;
    private final MatchingCommandService matchingCommandService;

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
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<MatchingResponseDTO.MatchingProjectListDTO> getProjectList(@Valid @ExistMember Principal principal, @RequestParam(name = "page") Integer page){
        Branch branch = memberQueryService.findMemberByName(principal.getName()).getUniversity().getBranch();
        List<Project> projectList = matchingQueryService.getProjectList(branch, ProjectStatus.PROCEEDING, page - 1);
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

    @PostMapping(value = "/projects", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "아이디어 페이지 작성")
    @ApiResponses({ // API 응답
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @PreAuthorize("hasRole('ROLE_PM')")
    public ApiResponse<MatchingResponseDTO.AddMatchingProjectResponseDTO> addMatchingProjects(@RequestPart MatchingRequestDTO.AddMatchingProjectRequestDto request, @RequestPart(required = false) MultipartFile image,
                                                   Principal principal) {
        Project project = matchingCommandService.addMatchingProjects(request, principal.getName(),image);
        return ApiResponse.onSuccess(MatchingConverter.toAddMatchingProjectResponseDTO(project));
    }

    @PatchMapping(value = "/projects/{projectId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "아이디어 페이지 수정")
    @ApiResponses({ // API 응답
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @PreAuthorize("hasRole('ROLE_PM')")
    public ApiResponse<String> updateMatchingProjects(@PathVariable(name = "projectId") Long projectId, @RequestPart MatchingRequestDTO.UpdateMatchingProjectRequestDto request,
                                                      @RequestPart(required = false) MultipartFile image) {
        matchingCommandService.updateMatchingProjects(projectId,request,image);
        return ApiResponse.onSuccess("아이디어 수정에 성공하였습니다.");
    }
}
