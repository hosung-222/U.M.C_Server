package com.example.umcmatchingcenter.controller;


import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.dto.AlarmDTO.AlarmResponseDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO;
import com.example.umcmatchingcenter.service.AlarmService.AlarmCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Principal;

@RestController
@RequestMapping("/alarms")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmCommandService alarmCommandService;

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public SseEmitter subscribe(Principal principal) {
        return alarmCommandService.subscribe(principal.getName());
    }

}
