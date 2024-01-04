package com.example.umcmatchingcenter.service.memberService;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.MemberConverter;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.dto.LoginRequestDto;
import com.example.umcmatchingcenter.dto.LoginResponseDto;
import com.example.umcmatchingcenter.dto.MemberRequestDto;
import com.example.umcmatchingcenter.jwt.JwtFilter;
import com.example.umcmatchingcenter.jwt.TokenProvider;
import com.example.umcmatchingcenter.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    public Member join(MemberRequestDto.JoinDto request){
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Member newMember = MemberConverter.toMember(request);
        return memberRepository.save(newMember);
    }

    public ResponseEntity login(LoginRequestDto request){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createToken(authentication);
        String refreshToken = tokenProvider.createToken(authentication);


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);

        LoginResponseDto loginResponseDto = MemberConverter.toLoginResponseDto(request.getEmail(), accessToken, refreshToken);

        return new ResponseEntity<>(loginResponseDto, httpHeaders, HttpStatus.OK);
    }
}
