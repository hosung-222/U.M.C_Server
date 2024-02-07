package com.example.umcmatchingcenter.service.qnaService;

import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.QnA;
import com.example.umcmatchingcenter.dto.QnADTO.QnARequestDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QnAQueryService {
    List<QnA> getQnAList(Project project);
}
