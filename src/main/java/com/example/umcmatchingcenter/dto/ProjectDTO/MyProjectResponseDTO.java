package com.example.umcmatchingcenter.dto.ProjectDTO;

import com.example.umcmatchingcenter.domain.University;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyProjectResponseDTO {

    private TotalMatchingResponseDTO totalMatchingResponseDTO;
    private List<PartMatchingResponseDTO> partMatchingDTO;
    private double competitionRate;
    private List<ApplicantInfoResponseDTO> applicantInfoList;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LandingPageResponseDTO {
        private Long Id;
        private String title;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LandingPageDetailsResponseDTO {
        private Long landingPageId;
        private String title;
        private Long profileImageId;
        private String profileImageUrl;
        private String introduction;
        private String body;
        private Map<Long, String> Images;
        private List<MyProjectResponseDTO.LandingPageMemberListDTO> memberList;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LandingPageMemberListDTO{
        private String profileImage;
        private MemberPart memberPart;
        private int generation;
        private String university;
        private String nameNickname;
    }

}
