package com.example.umcmatchingcenter.jwt.jwtHandler;

import com.example.umcmatchingcenter.apiPayload.exception.handler.MemberHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus.JWT_FORBIDDEN;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        /**
         * 403에러 핸들러(토큰이 잘못되었거나 없을시) 핸들러
         */
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
