package com.example.umcmatchingcenter.dto.MatchingDTO;

import com.example.umcmatchingcenter.domain.enums.MemberPart;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class MatchingRequestDTO {
    @Getter
    @Setter
    public static class AddMatchingProjectRequestDto{
        private String body;
        private String introduction;
        private String name;
        private Map<MemberPart, Integer> partCounts;


    }
}
