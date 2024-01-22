package com.example.umcmatchingcenter.controller;


import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.MemberConverter;
import com.example.umcmatchingcenter.domain.Member;

import com.example.umcmatchingcenter.dto.MemberDTO.LoginRequestDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberRequestDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO;
import com.example.umcmatchingcenter.service.memberService.MemberCommandService;

import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import com.example.umcmatchingcenter.validation.annotation.ExistMember;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @PostMapping("/members")
    @Operation(summary = "회원가입 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody @Valid MemberRequestDTO.JoinDTO request){
        Member member = memberCommandService.join(request);
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }

    @PostMapping("/members/login")
    @Operation(summary = "로그인 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "사용자가 없습니다.",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4002", description = "잘못된 비밀번호 입니다",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "로그인 dto")
    public ApiResponse login(@RequestBody @Valid LoginRequestDTO request, HttpServletResponse response){
        return ApiResponse.onSuccess(memberCommandService.login(request, response));
    }

    @Operation(summary = "내 정보 조회 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "사용자가 없습니다.",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/members/mypage")
    public ApiResponse<MemberResponseDTO.MyInfoDTO> myPage(@Valid @ExistMember Principal principal){
        Member member = memberQueryService.getMyInfo(principal.getName());
        return ApiResponse.onSuccess(MemberConverter.toMyInfoDTO(member));
    }

    @PostMapping("/members/duplication")
    @Operation(summary = "닉네임 중복확인 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER301",description = "사용 가능한 닉네임입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4004", description = "이미 등록된 사용자 입니다.",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class)))

    })
    @Parameter(name = "memberName", description = "로그인용 닉네임")
    public ApiResponse duplicationMemberName(@RequestParam String memberName){
        return memberCommandService.duplicationMemberName(memberName);
    }


}
