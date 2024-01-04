package com.example.umcmatchingcenter.service.EmailService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MemberHandler;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.dto.EmailRequestDto;
import com.example.umcmatchingcenter.repository.MemberRepository;
import com.example.umcmatchingcenter.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final MemberRepository memberRepository;

    private final SendEmailService sendMailService;

    private final RedisService redisService;

    private long authCodeExpirationMillis;


    public String sendAuthCode(EmailRequestDto.AuthCodeDto request) {
        this.checkDuplicatedEmail(request.getEmail());
        String title = "UmcMatchingCenter 이메일 인증 번호";
        String authCode = this.createAuthCode();
        sendMailService.sendEmail(request.getEmail(), title, authCode);
        redisService.setData(request.getEmail(),authCode, 300L);
        return authCode;
    }

    private void checkDuplicatedEmail(String email) {
        Optional<Member> user = memberRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new MemberHandler(ErrorStatus.EMAIL_ALREADY_EXIST);
        }
    }

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

    public EmailRequestDto.CertificationDto certificateAuthCode(EmailRequestDto.CertificationDto request) {
        this.checkDuplicatedEmail(request.getEmail());
        String redisAuthCode = redisService.getData(request.getEmail());
        System.out.println(request.getEmail());
        System.out.println(redisAuthCode);
        System.out.println(request.getAuthCode());

        if(request.getAuthCode().equals(redisAuthCode)){
            return request;
        }else{
            throw new MemberHandler(ErrorStatus.EMAIL_WRONG_CODE);
        }
    }
}

