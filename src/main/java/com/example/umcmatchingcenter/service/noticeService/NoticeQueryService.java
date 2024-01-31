package com.example.umcmatchingcenter.service.noticeService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.AlarmHandler;
import com.example.umcmatchingcenter.apiPayload.exception.handler.NoticeHandler;
import com.example.umcmatchingcenter.converter.NoticeConverter;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Notice;
import com.example.umcmatchingcenter.dto.noticeDTO.NoticeResponseDTO;
import com.example.umcmatchingcenter.repository.MemberRepository;
import com.example.umcmatchingcenter.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeQueryService {

    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;

    public List<Notice> getNoticeList(String memberName){
        Optional<Member> member = memberRepository.findByMemberName(memberName);
        Branch branch = member.get().getBranch();

        List<Notice> noticeList = noticeRepository.findAllByBranch(branch);

        if (noticeList == null || noticeList.isEmpty())
            throw new NoticeHandler(ErrorStatus.NOTICE_NOT_EXIST);
        return noticeList;
    }

    public Notice getNoticeDetails(Long noticeId){
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(()->new NoticeHandler(ErrorStatus.NOTICE_NOT_EXIST));
        return notice;
    }
}
