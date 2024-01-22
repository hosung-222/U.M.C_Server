package com.example.umcmatchingcenter.controller;


import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.MemberConverter;
import com.example.umcmatchingcenter.domain.Member;

import com.example.umcmatchingcenter.dto.MemberDTO.LoginRequestDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.LoginResponseDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberRequestDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO;
import com.example.umcmatchingcenter.service.memberService.MemberCommandService;

import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import com.example.umcmatchingcenter.validation.annotation.ExistMember;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @Operation(summary = "회원가입 API")
    @PostMapping("/members")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody @Valid MemberRequestDTO.JoinDTO request){
        Member member = memberCommandService.join(request);
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }

    @Operation(summary = "로그인 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "사용자가 없습니다.",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4002", description = "잘못된 비밀번호 입니다",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @PostMapping("/members/login")
    public ApiResponse<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request, HttpServletResponse response){
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
    @Operation(summary = "닉네임 중복확인 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER301",description = "사용 가능한 닉네임입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4004", description = "이미 등록된 사용자 입니다.",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class)))
    })
    @Parameter(name = "memberName", description = "로그인용 닉네임")
    public ApiResponse<String> duplicationMemberName(@RequestParam String memberName){
        return memberCommandService.duplicationMemberName(memberName);
    }

    @PostMapping("/members/renewal/accessToken")
    @Operation(summary = "access토큰 갱신 API(자동로그인)")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER301",description = "사용 가능한 닉네임입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4005", description = "잘못된 refresh 토큰입니다.",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4001", description = "권한이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class)))
    })
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<LoginResponseDTO.RenewalAccessTokenResponseDTO> renewalAccessToken(Principal principal, HttpServletRequest request, HttpServletResponse response){

        return ApiResponse.onSuccess(memberCommandService.renewalAccessToken(principal.getName(), request, response));
    }



}
