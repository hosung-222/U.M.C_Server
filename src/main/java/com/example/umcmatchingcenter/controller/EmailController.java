package com.example.umcmatchingcenter.controller;


import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.EmailConverter;
import com.example.umcmatchingcenter.dto.EmailRequestDto;
import com.example.umcmatchingcenter.dto.EmailResponseDto;
import com.example.umcmatchingcenter.service.EmailService.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;
    @PostMapping("/AuthCode")
    public ApiResponse<EmailResponseDto.AuthCodeResultDto> sendEmailcCetification(@RequestBody @Valid EmailRequestDto.AuthCodeDto request){
        String authCode = emailService.sendAuthCode(request);
        return ApiResponse.onSuccess(EmailConverter.toEmailAuthCodeResultDto(request.getEmail(), authCode));
    }

    @PostMapping("/AuthCode/certification")
    public ApiResponse<EmailResponseDto.AuthCodeCertificaitionResultDto> cerificationEmail(@RequestBody EmailRequestDto.CertificationDto request) {
        emailService.certificateAuthCode(request);
        return ApiResponse.onSuccess(EmailConverter.toEmailCertificaitionResultDto(request.getEmail()));

    }

}
