package com.example.umcmatchingcenter.service.EmailService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MemberHandler;
import com.example.umcmatchingcenter.dto.MemberDto.EmailRequestDto;
import com.example.umcmatchingcenter.repository.MemberRepository;
import com.example.umcmatchingcenter.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final MemberRepository memberRepository;

    private final SendEmailService sendMailService;

    private final RedisService redisService;

    public String sendAuthCode(EmailRequestDto.AuthCodeDto request) {
        String title = "UmcMatchingCenter 이메일 인증 번호";
        String authCode = this.createAuthCode();
        sendMailService.sendEmail(request.getEmail(), title, authCode);
        redisService.setData(request.getEmail(),authCode, 300L);
        return authCode;
    }

    //이메일 인증코드 생성
    private String createAuthCode() {
        int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new MemberHandler(ErrorStatus.FAIL_CREATE_CODE);
        }
    }

    //이메일 코드 인증
    public EmailRequestDto.CertificationDto AuthCodeCertification(EmailRequestDto.CertificationDto request) {
        String redisAuthCode = redisService.getData(request.getEmail());
        if(request.getAuthCode().equals(redisAuthCode)){
            return request;
        }else{
            throw new MemberHandler(ErrorStatus.WRONG_AUTH_CODE);
        }
    }
}

