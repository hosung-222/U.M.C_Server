package com.example.umcmatchingcenter.service.memberService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MemberHandler;
import com.example.umcmatchingcenter.converter.MemberConverter;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.University;
import com.example.umcmatchingcenter.dto.MemberDTO.LoginRequestDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberRequestDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO;
import com.example.umcmatchingcenter.jwt.JwtFilter;
import com.example.umcmatchingcenter.jwt.TokenProvider;
import com.example.umcmatchingcenter.repository.MemberRepository;
import com.example.umcmatchingcenter.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandService {

    private final MemberRepository memberRepository;
    private final UniversityRepository universityRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public Member join(MemberRequestDTO.JoinDTO request){
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Optional<University> university = universityRepository.findById(request.getUniversityId());

        if(memberRepository.findByMemberName(request.getMemberName()).isPresent()){
            throw new MemberHandler(ErrorStatus.MEMBER_ALREADY_EXIST);
        }

        Member newMember = MemberConverter.toMember(request, university.get());
        return memberRepository.save(newMember);
    }

    //로그인
    public ResponseEntity login(LoginRequestDTO request){

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getMemberName(), request.getPassword());

        Authentication authentication = getAuthentication(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createToken(authentication,1);
        String refreshToken = tokenProvider.createToken(authentication,24);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);


        return new ResponseEntity<>(MemberConverter.toLoginResponseDto(request.getMemberName(), accessToken, refreshToken),
                httpHeaders, HttpStatus.OK);
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

    public MemberResponseDTO.DepartResultDTO memberDepart(String name) {
        Optional<Member> member  = memberRepository.findByMemberName(name);
        if (member.isEmpty()){
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }
        member.get().depart();

        return MemberConverter.toDepartResultDTO(memberRepository.save(member.get()));
    }
}
