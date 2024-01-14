package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.dto.projectDto.MyProjectResponseDto;
import com.example.umcmatchingcenter.service.myProjectService.ProjectService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/myProject")
@RequiredArgsConstructor
@Slf4j
public class MyProjectController {

    private final ProjectService projectService;

    @GetMapping("")
    @Operation(summary = "내 프로젝트 관리 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public MyProjectResponseDto myProject() {
        return projectService.myProject();
    }

    @PostMapping("/pass/{memberId}")
    @Operation(summary = "지원자 합격 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MYPROJECT401", description = "해당프로젝트의 지원자가 없습니다",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @ApiParam(value = "유저 아이디")
    public HttpStatus pass(@PathVariable Long memberId) {
        String pass = projectService.pass(memberId);
        return HttpStatus.OK;
    }

    @PostMapping("/fail/{memberId}")
    @Operation(summary = "지원자 불합격 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MYPROJECT401", description = "해당프로젝트의 지원자가 없습니다",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @ApiParam(value = "유저 아이디")
    public HttpStatus fail(@PathVariable Long memberId) {
        String fail = projectService.fail(memberId);
        return HttpStatus.OK;
    }
}
