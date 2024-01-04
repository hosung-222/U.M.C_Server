package com.example.umcmatchingcenter.controller;


import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.MemberConverter;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.dto.*;
import com.example.umcmatchingcenter.service.memberService.MemberCommandService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

    private final MemberCommandService memberCommandService;

    @PostMapping("/user")
    @Operation(summary = "회원가입 api")
    public ApiResponse<MemberResponseDto.JoinResultDTO> join(@RequestBody @Valid MemberRequestDto.JoinDto request){
        Member member = memberCommandService.join(request);
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 api")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDto request){
        return memberCommandService.login(request);
    }

}
