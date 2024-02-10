package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.dto.evaluationDTO.EvaluationRequestDTO;
import com.example.umcmatchingcenter.dto.evaluationDTO.EvaluationResponseDTO;
import com.example.umcmatchingcenter.service.evaluationService.EvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation")
@Tag(name = "상호평가 API")
public class EvaluationController {

    private final EvaluationService evaluationService;

    @GetMapping("/getTeammates")
    @Operation(summary = "내 프로젝트 팀원 보기 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "EVALUATION4001", description = "평가가 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "EVALUATION4002", description = "해당 프로젝트의 멤버가 아닙니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "EVALUATION4003", description = "이미 평가가 완료된 멤버입니다."),
    })
    public ApiResponse<List<EvaluationResponseDTO>> getEvaluations() {
        return ApiResponse.onSuccess(evaluationService.getTeammates());
    }

    @PostMapping("/save/{memberId}")
    @Operation(summary = "상호평가 저장하기 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "EVALUATION4001", description = "평가가 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "EVALUATION4002", description = "해당 프로젝트의 멤버가 아닙니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "EVALUATION4003", description = "이미 평가가 완료된 멤버입니다."),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "피평가자 아이디")
    })
    public ApiResponse<String> saveEvaluation(@RequestBody EvaluationRequestDTO dto,
                                              @PathVariable Long memberId) {
        evaluationService.saveEvaluation(dto, memberId);
        return ApiResponse.onSuccess("평가 저장 완료");
    }

}
