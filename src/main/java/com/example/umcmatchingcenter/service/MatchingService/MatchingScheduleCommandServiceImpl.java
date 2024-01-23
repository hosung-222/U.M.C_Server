package com.example.umcmatchingcenter.service.MatchingService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MatchingHandler;
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

    @Override
    public MatchingSchedule updateSchedule(Long scheduleId, MatchingScheduleRequestDTO.MatchingScheduleDTO request, Branch branch) {
        try {
            MatchingSchedule findSchedule = matchingScheduleRepository.findScheduleById(scheduleId);

            // 수정할 일정이 현재 관리자의 지부 내용 일정인지 확인
            if (!branch.getId().equals(findSchedule.getBranch().getId())) {
                throw new MatchingHandler(ErrorStatus.JWT_FORBIDDEN);
            }

            findSchedule.setName(request.getTitle());
            findSchedule.setDescription(request.getDescription());
            findSchedule.setScheduleColor(request.getScheduleColor());
            findSchedule.setStartDate(request.getStartDate());
            findSchedule.setEndDate(request.getEndDate());
        } catch (NullPointerException e){
            throw new MatchingHandler(ErrorStatus.MATCHINGSCHEDULE_NOT_EXIST);
        }
        return null;
    }
}
