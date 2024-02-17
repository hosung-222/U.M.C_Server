package com.example.umcmatchingcenter.service.matchingService;

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
        checkDateValidation(request);
        return matchingScheduleRepository.save(newSchedule);
    }

    @Override
    public void updateSchedule(Long scheduleId, MatchingScheduleRequestDTO.MatchingScheduleDTO request, Branch branch) {
        try {

            // 수정할 일정이 현재 관리자의 지부 내용 일정인지 확인
            MatchingSchedule findSchedule = checkIsAuthorized(scheduleId, branch);
            checkDateValidation(request);
            // 업데이트 진행
            findSchedule.updateSchedule(request);

            matchingScheduleRepository.save(findSchedule);
        } catch (NullPointerException e){
            throw new MatchingHandler(ErrorStatus.MATCHINGSCHEDULE_NOT_EXIST);
        }
    }

    @Override
    public void deleteSchedule(Long scheduleId, Branch branch) {
        try {
            // 수정할 일정이 현재 관리자의 지부 내용 일정인지 확인
            checkIsAuthorized(scheduleId, branch);

            matchingScheduleRepository.deleteById(scheduleId);
        }catch (NullPointerException e){
            throw new MatchingHandler(ErrorStatus.MATCHINGSCHEDULE_NOT_EXIST);
        }
    }

    @Override
    public MatchingSchedule checkIsAuthorized(Long scheduleId, Branch branch) {
        MatchingSchedule findSchedule = matchingScheduleRepository.findScheduleById(scheduleId);

        // 수정할 일정이 현재 관리자의 지부 내용 일정인지 확인
        if (!branch.getId().equals(findSchedule.getBranch().getId())) {
            throw new MatchingHandler(ErrorStatus.MATCHINGSCHEDULE_UNAUTHORIZED);
        }

        return findSchedule;
    }

    @Override
    public void checkDateValidation(MatchingScheduleRequestDTO.MatchingScheduleDTO request) {
        // 시작일 < 종료일인지 확인
        String startDate = MatchingSchedule.combineDate(request.getStartYear(), request.getStartMonth(), request.getStartDay());
        String endDate = MatchingSchedule.combineDate(request.getEndYear(), request.getEndMonth(), request.getEndDay());
        if (startDate.compareTo(endDate) > 0) { // start > end
            throw new MatchingHandler(ErrorStatus.MATCHINGSCHEDULE_DATE_NOT_VALIDATE);
        }
    }

}
