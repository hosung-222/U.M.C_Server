package com.example.umcmatchingcenter.service.memberService;


import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {


    private final MemberRepository memberRepository;

    public Optional<Member> findMember(Long id){
        return memberRepository.findById(id);
    }

    public Member getMyInfo(String name) {
        Optional<Member> target = memberRepository.findByMemberName(name);
        return target.get();
    }

}
