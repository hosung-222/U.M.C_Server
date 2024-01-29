package com.example.umcmatchingcenter.controller;


import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.EmailConverter;
import com.example.umcmatchingcenter.dto.MemberDTO.EmailRequestDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.EmailResponseDTO;
import com.example.umcmatchingcenter.service.EmailService.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
@Tag(name = "이메일인증 API")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/auth-code")
    @Operation(summary = "이메일 인증코드 요청 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "email", description = "인증할 이메일 주소")
    })
    public ApiResponse<EmailResponseDTO.AuthCodeResultDTO> sendAuthCode(@RequestBody @Valid EmailRequestDTO.AuthCodeDTO request){
        String authCode = emailService.sendAuthCode(request);
        return ApiResponse.onSuccess(EmailConverter.toEmailAuthCodeResultDto(request.getEmail(), authCode));
    }

    @PostMapping("/auth-code/certification")
    @Operation(summary = "이메일 인증 코드 검증 요청 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "EMAIL4002", description = "잘못된 인증 코드 입니다",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),

    })
    @Parameters({
            @Parameter(name = "email", description = "인증할 이메일 주소"),
            @Parameter(name = "authCode", description = "인증코드")
    })
    public ApiResponse<EmailResponseDTO.AuthCodeCertificaitionResultDTO> AuthCodeCertification(@RequestBody EmailRequestDTO.CertificationDTO request) {
        emailService.AuthCodeCertification(request);
        return ApiResponse.onSuccess(EmailConverter.toEmailCertificaitionResultDto(request.getEmail()));

    }

}
