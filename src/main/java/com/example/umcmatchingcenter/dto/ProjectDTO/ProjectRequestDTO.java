package com.example.umcmatchingcenter.dto.ProjectDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class ProjectRequestDTO {
    @Getter
    @Setter
    public static class AddProjectRequestDto{
        private String body;
        private String image;
        private String introduction;
        private String name;

    }
}
