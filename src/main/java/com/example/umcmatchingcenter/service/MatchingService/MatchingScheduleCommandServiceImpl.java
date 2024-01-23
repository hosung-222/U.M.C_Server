package com.example.umcmatchingcenter.service.MatchingService;

import com.example.umcmatchingcenter.converter.matching.MatchingScheduleConverter;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.MatchingSchedule;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingScheduleRequestDTO;
import com.example.umcmatchingcenter.repository.MatchingScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingScheduleCommandServiceImpl implements MatchingScheduleCommandService {

    private final MatchingScheduleRepository matchingScheduleRepository;

    @Override
    public MatchingSchedule postSchedule(MatchingScheduleRequestDTO.MatchingScheduleDTO request, Branch branch) {
        MatchingSchedule newSchedule = MatchingScheduleConverter.toSchedule(request, branch);

        return matchingScheduleRepository.save(newSchedule);
    }
}
