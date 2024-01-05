package com.example.umcmatchingcenter.service.memberService;

import com.example.umcmatchingcenter.converter.MemberConverter;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.dto.MemberDto.LoginRequestDto;
import com.example.umcmatchingcenter.dto.MemberDto.LoginResponseDto;
import com.example.umcmatchingcenter.dto.MemberDto.MemberRequestDto;
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

    //회원가입
    public Member join(MemberRequestDto.JoinDto request){
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Member newMember = MemberConverter.toMember(request);
        return memberRepository.save(newMember);
    }

    //로그인
    public ResponseEntity login(LoginRequestDto request){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getMemberName(), request.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createToken(authentication,1);
        String refreshToken = tokenProvider.createToken(authentication,24);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);


        return new ResponseEntity<>(MemberConverter.toLoginResponseDto(request.getMemberName(), accessToken, refreshToken),
                httpHeaders, HttpStatus.OK);
    }
}
