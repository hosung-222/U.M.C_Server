package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO;
import com.example.umcmatchingcenter.service.memberService.MemberCommandService;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/challenger/manage")
    public ApiResponse<List<MemberResponseDTO.ChallengerInfoDTO>> challengerList(){
//        List<ChallengerInfoDTO> challengerList = memberQueryService.getChallengerList();

        return ApiResponse.onSuccess(null);
    }
}
