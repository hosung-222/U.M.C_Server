package com.example.umcmatchingcenter.service.noticeService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.NoticeHandler;
import com.example.umcmatchingcenter.converter.NoticeConverter;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Notice;
import com.example.umcmatchingcenter.dto.noticeDTO.NoticeRequestDTO;
import com.example.umcmatchingcenter.repository.MemberRepository;
import com.example.umcmatchingcenter.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeCommandService {

    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;

    public Notice addNotice(NoticeRequestDTO.AddNoticeDTO request, String memberName){

        Notice notice = NoticeConverter.toNotice(request);

        Optional<Member> member = memberRepository.findByMemberName(memberName);

        notice.setBranch(member.get().getUniversity().getBranch());

        return noticeRepository.save(notice);
    }

    public Notice updateNotice(Long noticeId,NoticeRequestDTO.UpdateNoticeDTO request){
        Notice notice = noticeRepository.findById(noticeId)
                        .orElseThrow(()->new NoticeHandler(ErrorStatus.NOTICE_NOT_EXIST));
        notice.update(request.getTitle(), request.getBody());
        return notice;
    }
}
