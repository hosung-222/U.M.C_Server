package com.example.umcmatchingcenter.service.noticeService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MatchingHandler;
import com.example.umcmatchingcenter.apiPayload.exception.handler.NoticeHandler;
import com.example.umcmatchingcenter.converter.ImageConverter;
import com.example.umcmatchingcenter.converter.NoticeConverter;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Image;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Notice;
import com.example.umcmatchingcenter.domain.enums.AlarmType;
import com.example.umcmatchingcenter.domain.mapping.NoticeImage;
import com.example.umcmatchingcenter.domain.mapping.ProjectImage;
import com.example.umcmatchingcenter.dto.noticeDTO.NoticeRequestDTO;
import com.example.umcmatchingcenter.repository.NoticeImageRepository;
import com.example.umcmatchingcenter.repository.NoticeRepository;
import com.example.umcmatchingcenter.service.AlarmService.AlarmCommandService;
import com.example.umcmatchingcenter.service.ImageQueryService;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import com.example.umcmatchingcenter.service.s3Service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NoticeCommandService {

    private final NoticeRepository noticeRepository;
    private final AlarmCommandService alarmCommandService;
    private final S3UploadService s3UploadService;
    private final MemberQueryService memberQueryService;
    private final NoticeQueryService noticeQueryService;
    private final NoticeImageRepository noticeImageRepository;
    private final ImageQueryService imageQueryService;

    public Notice addNotice(NoticeRequestDTO.AddNoticeDTO request, List<MultipartFile> imageIdList, String memberName){

        Notice notice = NoticeConverter.toNotice(request);

        Member member = memberQueryService.findMemberByName(memberName);

        List<NoticeImage> noticeImageList = getNoticeImageList(imageIdList, notice);

        noticeImageRepository.saveAll(noticeImageList);

        Branch branch = member.getUniversity().getBranch();
        alarmCommandService.sendToBranch(branch, AlarmType.NOTICE, "새로운 공지사항이 등록되었습니다.");

        notice.setBranch(branch);
        return noticeRepository.save(notice);
    }

    public Notice updateNotice(Long noticeId,NoticeRequestDTO.UpdateNoticeDTO request, List<MultipartFile> imageIdList){
        Notice notice = noticeQueryService.findNotice(noticeId);
        deleteImages(request.getDeleteImageIdList());

        System.out.println(imageIdList);

        if(imageIdList!=null){
            List<NoticeImage> noticeImageList = getNoticeImageList(imageIdList, notice);
            noticeImageRepository.saveAll(noticeImageList);
        }

        notice.update(request.getTitle(), request.getBody());
        return notice;
    }

    private List<NoticeImage> getNoticeImageList(List<MultipartFile> imageIdList, Notice notice){
        List<NoticeImage> noticeImageList = imageIdList.stream()
                .map(s3UploadService::saveFile)
                .map(image -> ImageConverter.toNoticeImage(image, notice))
                .collect(Collectors.toList());
        return noticeImageList;
    }

    private void deleteImages(List<Long> deleteImageIdList){
        List<Image> deleteImageList = imageQueryService.findAllImageById(deleteImageIdList);

        List<NoticeImage> deleteNoticeImageList = deleteImageList.stream()
                .peek(image -> s3UploadService.delete(image.getS3ImageUrl()))
                .map(image -> noticeImageRepository.findByImage(image)
                        .orElseThrow(()-> new NoticeHandler(ErrorStatus.IMAGE_NOT_EXIST)))
                .collect(Collectors.toList());

        noticeImageRepository.deleteAll(deleteNoticeImageList);
    }
}
