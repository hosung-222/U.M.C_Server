package com.example.umcmatchingcenter.controller;


import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.AlarmConverter;
import com.example.umcmatchingcenter.dto.AlarmDTO.AlarmResponseDTO;
import com.example.umcmatchingcenter.service.AlarmService.AlarmCommandService;
import com.example.umcmatchingcenter.service.AlarmService.AlarmQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Principal;

@RestController
@RequestMapping("/alarms")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmCommandService alarmCommandService;
    private final AlarmQueryService alarmQueryService;

    @Operation(summary = "실시간 알림 통신 연결",description = "특정 멤버의 실시간 알림 통신을 위한 연결 api")
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CHALLENGER')")
    public SseEmitter subscribe(Principal principal) {
        return alarmCommandService.subscribe(principal.getName());
    }

    @Operation(summary = "특정 멤버의 알림 목록 조회",description = "특정 멤버의 알림 목록 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "memberName", description = "멤버의 로그인 아이디")
    })
    @GetMapping("/{memberName}/alarms")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CHALLENGER')")
    public ApiResponse<AlarmResponseDTO.AlarmViewListDTO> getAlarmList(@PathVariable(name = "memberName") String memberName){
        return ApiResponse.onSuccess(alarmQueryService.getAlarmList(memberName));
    }

    @Operation(summary = "특정 멤버의 알림 삭제",description = "특정 멤버의 확인 완료된 알림 삭제 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NO_DELETE_ALARM", description = "삭제할 알림이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class)))
    })
    @Parameters({
            @Parameter(name = "memberName", description = "멤버의 로그인 아이디")
    })
    @DeleteMapping("/{memberName}/alarms")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CHALLENGER')")
    public ApiResponse<AlarmResponseDTO.DeleteAlarmDTO> deleteAlarms(@PathVariable(name = "memberName") String memberName){
        int deleteCount = alarmCommandService.deleteAlarms(memberName);
        return ApiResponse.onSuccess(AlarmConverter.toDeleteAlarmDTO(memberName, deleteCount));
    }






}
