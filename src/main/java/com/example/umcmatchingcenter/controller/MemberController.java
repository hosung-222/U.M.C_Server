package com.example.umcmatchingcenter.controller;


import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.MemberConverter;
import com.example.umcmatchingcenter.domain.Member;

import com.example.umcmatchingcenter.dto.MemberDTO.LoginRequestDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.LoginResponseDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberRequestDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberRequestDTO.UpdateMyInfoDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.MyInfoDTO;
import com.example.umcmatchingcenter.service.memberService.MemberCommandService;

import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import com.example.umcmatchingcenter.validation.annotation.ExistMember;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

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
        MyInfoDTO info =  memberQueryService.getMyInfo(principal.getName());
        return ApiResponse.onSuccess(info);
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

    @Operation(summary = "내 정보 수정 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "사용자가 없습니다.", content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4005", description = "사진 업로드에 실패했습니다.", content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "415 Unsupported Media Type", description = "UpdateMyInfoDTO JSON 전송시 Content-Type에 application/json 을 명시해 주세요", content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @PreAuthorize("isAuthenticated()")
    @PatchMapping(value = "/members/mypage" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<String> updateMyInfo(@RequestPart @Valid UpdateMyInfoDTO updateMyInfoDTO,
                                            @RequestPart(required = false) MultipartFile file,
                                            Principal principal) {
        memberCommandService.updateMyInfo(updateMyInfoDTO, file, principal.getName());
        return ApiResponse.onSuccess("내 정보 수정에 성공했습니다.");
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/members/depart")
    public ApiResponse<String> depart(Principal principal){
        memberCommandService.memberDepart(principal.getName());
        return ApiResponse.onSuccess("탈부처리되었습니다.");
    }

}
