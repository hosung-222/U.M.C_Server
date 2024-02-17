package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Notice;
import com.example.umcmatchingcenter.dto.noticeDTO.NoticeRequestDTO;
import com.example.umcmatchingcenter.dto.noticeDTO.NoticeResponseDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NoticeConverter {

    public static Notice toNotice(NoticeRequestDTO.AddNoticeDTO request){
        return Notice.builder()
                .title(request.getTitle())
                .body(request.getBody())
                .build();
    }

    public static NoticeResponseDTO.AddNoticeDTO toAddNoticeDTO(Notice notice){
        return NoticeResponseDTO.AddNoticeDTO.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .body(notice.getBody())
                .createdAt(notice.getCreatedAt())
                .build();
    }

    public static NoticeResponseDTO.NoticeDetailsDTO toNoticeDetailsDTO(Notice notice, Map<Long, String> images){
        return NoticeResponseDTO.NoticeDetailsDTO.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .body(notice.getBody())
                .images(images)
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }

    public static NoticeResponseDTO.NoticeViewDTO toNoticeViewDTO(Notice notice){
        return  NoticeResponseDTO.NoticeViewDTO.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .body(notice.getBody())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }

    public static NoticeResponseDTO.NoticeListDTO toNoticeListDTO(List<Notice> noticeList){
        List<NoticeResponseDTO.NoticeViewDTO> noticeDetailsDTOList = noticeList.stream()
                .map(NoticeConverter::toNoticeViewDTO).collect(Collectors.toList());

        return NoticeResponseDTO.NoticeListDTO.builder()
                .noticeList(noticeDetailsDTOList)
                .listSize(noticeDetailsDTOList.size())
                .build();
    }

    public static NoticeResponseDTO.UpdateNoticeDetailsDTO toUpdateNoticeDetailsDTO(Notice notice){
        return NoticeResponseDTO.UpdateNoticeDetailsDTO.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .body(notice.getBody())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }
}
