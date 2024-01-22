package com.example.umcmatchingcenter.service.memberService;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.code.status.SuccessStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MemberHandler;
import com.example.umcmatchingcenter.converter.MemberConverter;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.University;
import com.example.umcmatchingcenter.domain.enums.AlarmType;
import com.example.umcmatchingcenter.domain.enums.MemberRole;
import com.example.umcmatchingcenter.dto.MemberDTO.LoginRequestDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.LoginResponseDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberRequestDTO;
import com.example.umcmatchingcenter.jwt.JwtFilter;
import com.example.umcmatchingcenter.jwt.TokenProvider;
import com.example.umcmatchingcenter.repository.MemberRepository;
import com.example.umcmatchingcenter.repository.UniversityRepository;
import com.example.umcmatchingcenter.service.AlarmService.AlarmCommandService;
import com.example.umcmatchingcenter.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandService {

    private final MemberRepository memberRepository;
    private final UniversityRepository universityRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final AlarmCommandService alarmCommandService;
    private final RedisService redisService;

    //회원가입
    public Member join(MemberRequestDTO.JoinDTO request){
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Optional<University> university = universityRepository.findById(request.getUniversityId());

        Member adminMember = memberRepository.findByUniversityAndRole(university.get(), MemberRole.ROLE_ADMIN);
        alarmCommandService.send(adminMember, AlarmType.JOIN, "새로운 챌린저의 가입 신청이 등록되었습니다.");

        Member newMember = MemberConverter.toMember(request, university.get());
        return memberRepository.save(newMember);
    }

    //로그인
    public LoginResponseDTO login(LoginRequestDTO request, HttpServletResponse response){

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getMemberName(), request.getPassword());

        Authentication authentication = getAuthentication(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createToken(authentication,1);
        String refreshToken = tokenProvider.createToken(authentication,24);

        redisService.setData(request.getMemberName(),refreshToken, 3600L);

        response.setHeader(JwtFilter.AUTHORIZATION_ACCESSS, "Bearer " + accessToken);
        response.setHeader(JwtFilter.AUTHORIZATION_REFRESH, "Bearer " + refreshToken);
        String memberRole = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return MemberConverter.toLoginResponseDto(request.getMemberName(),
                accessToken, refreshToken,memberRole);
    }

    public Authentication getAuthentication(UsernamePasswordAuthenticationToken authenticationToken){
        try{
            return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            throw new MemberHandler(ErrorStatus.MEMBER_WRONG_PASSWORD);
        }catch (InternalAuthenticationServiceException e){
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }
    }

    public ApiResponse duplicationMemberName(String memberName){
        if(memberRepository.findByMemberName(memberName).isPresent()){
            throw new MemberHandler(ErrorStatus.MEMBER_ALREADY_EXIST);
        }

        return ApiResponse.of(SuccessStatus._MEMBERNICKNAME_OK,memberName);
    }

}
