package com.example.umcmatchingcenter.dto.projectDto;

import com.example.umcmatchingcenter.domain.enums.MemberPart;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PartMatchingResponseDto {

    MemberPart part;
    int matchingNum;
    int totalNum;

}
