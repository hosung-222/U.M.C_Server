package com.example.umcmatchingcenter.dto.AlarmDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDTO {
    String id;
    String memberName;
    String body;
    String createdAt;
}
