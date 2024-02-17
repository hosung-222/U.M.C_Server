package com.example.umcmatchingcenter.controller;


import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.AlarmConverter;
import com.example.umcmatchingcenter.dto.AlarmDTO.AlarmResponseDTO;
import com.example.umcmatchingcenter.service.AlarmService.AlarmCommandService;
import com.example.umcmatchingcenter.service.AlarmService.AlarmQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Tag(name = "알림 API")
public class AlarmController {

    private final AlarmCommandService alarmCommandService;
    private final AlarmQueryService alarmQueryService;
    /*
    @Operation(summary = "실시간 알림 통신 연결",description = "특정 멤버의 실시간 알림 통신을 위한 연결 api")
    @GetMapping(value = "/alarms/subscribe", produces = "text/event-stream")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CHALLENGER')")
    public SseEmitter subscribe(Principal principal, HttpServletResponse response) {
        response.setHeader("X-Accel-Buffering", "no");
        return alarmCommandService.subscribe(principal.getName());
    }
    */

    @Operation(summary = "특정 멤버의 알림 목록 조회",description = "특정 멤버의 알림 목록 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NO_ALARM_LIST", description = "알림이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class)))
    })
    @GetMapping("/alarms")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CHALLENGER')")
    public ApiResponse<AlarmResponseDTO.AlarmViewListDTO> getAlarmList(Principal principal){
        return ApiResponse.onSuccess(alarmQueryService.getAlarmList(principal.getName()));
    }


    @Operation(summary = "특정 멤버의 알림 삭제",description = "특정 멤버의 확인 완료된 알림 삭제 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NO_DELETE_ALARM", description = "삭제할 알림이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class)))
    })
    @DeleteMapping("/alarms")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CHALLENGER')")
    public ApiResponse<AlarmResponseDTO.DeleteAlarmDTO> deleteAlarms(Principal principal){
        int deleteCount = alarmCommandService.deleteAlarms(principal.getName());
        return ApiResponse.onSuccess(AlarmConverter.toDeleteAlarmDTO(principal.getName(), deleteCount));
    }






}
