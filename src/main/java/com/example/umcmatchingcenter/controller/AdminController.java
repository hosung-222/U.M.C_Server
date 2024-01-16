package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.ChallengerInfoDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.ApplyTeamDTO;
import com.example.umcmatchingcenter.service.memberService.MemberCommandService;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "관리자 API")
public class AdminController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @Operation(summary = "챌린저 관리용 조회 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4001", description = "JWT 토큰을 주세요!",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4002", description = "JWT 토큰 만료",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "page", description = "페이징 넘버 입니다."),
            @Parameter(name = "matchingStatus", description = "조회할 챌린저의 매칭 상태입니다.")
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/challenger/manage")
    public ApiResponse<List<MemberResponseDTO.ChallengerInfoDTO>> challengerList(@RequestParam("matchingStatus") MemberMatchingStatus memberMatchingStatus, @RequestParam("page") int page){
        List<ChallengerInfoDTO> challengerList = memberQueryService.getChallengerList(memberMatchingStatus, page);

        return ApiResponse.onSuccess(challengerList);
    }

    @Operation(summary = "매칭 차수 별 지원 팀 조회 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4001", description = "JWT 토큰을 주세요!",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4002", description = "JWT 토큰 만료",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "name", description = "조회할 챌린저의 name(membername = id) 입니다.")
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/challenger/manage/{name}")
    public ApiResponse<List<ApplyTeamDTO>> matchRoundList(@PathVariable(name = "name") String name){
        List<ApplyTeamDTO> matchingRoundDTOList = memberQueryService.getMatcingRoundList(name);

        return ApiResponse.onSuccess(matchingRoundDTOList);
    }
}
