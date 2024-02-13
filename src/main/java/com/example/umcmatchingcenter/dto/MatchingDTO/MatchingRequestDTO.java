package com.example.umcmatchingcenter.dto.MatchingDTO;

import com.example.umcmatchingcenter.domain.enums.MemberPart;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public class MatchingRequestDTO {
    @Getter
    @Setter
    public static class AddMatchingProjectRequestDTO {
        private Long profileImageId;
        private String body;
        private String introduction;
        private String name;
        private Map<MemberPart, Integer> partCounts;
        private List<Long> imageIdList;


    }

    @Getter
    @Setter
    public static class UpdateMatchingProjectRequestDTO {
        private String body;
        private String introduction;
        private String name;
        private Map<MemberPart, Integer> partCounts;
        private List<Long> imageIdList;
        private List<Long> deleteImageIdList;
    }
}
