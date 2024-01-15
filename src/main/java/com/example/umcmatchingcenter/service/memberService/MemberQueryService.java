package com.example.umcmatchingcenter.service.memberService;


import static com.example.umcmatchingcenter.domain.enums.MemberRole.*;

import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.domain.enums.MemberRole;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.ChallengerInfoDTO;
import com.example.umcmatchingcenter.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<ChallengerInfoDTO> getChallengerList(MemberMatchingStatus memberMatchingStatus, int page) {
        Page<Member> members = memberRepository.findByGenerationAndRoleAndMatchingStatus(5, ROLE_CHALLENGER,
                memberMatchingStatus, PageRequest.of(page, 10));


        return null;
    }
}
