package com.example.umcmatchingcenter.dto.projectDto;

import com.example.umcmatchingcenter.domain.enums.MemberPart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartMatchingResponseDto {

    private MemberPart part;
    private int matchingNum;
    private int totalNum;

}
