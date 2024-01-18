package com.example.umcmatchingcenter.service.queryService;

import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.jwt.SecurityUtil;
import com.example.umcmatchingcenter.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {

    private final MemberRepository memberRepository;
    public Member getMember(Long memberId) {
        Optional<Member> foundMember = memberRepository.findById(memberId);
        if (foundMember.isPresent() && foundMember != null) {
            return foundMember.get();
        }
        return null;
    }

    public Member getCurrentLoginMember() {
        String memberName = SecurityUtil.getCurrentMember();
        Optional<Member> foundMember = memberRepository.findMemberByEmail(memberName);
        if (foundMember.isPresent() && foundMember != null) {
            return foundMember.get();
        }
        return null;
    }
}
