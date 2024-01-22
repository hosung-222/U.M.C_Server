package com.example.umcmatchingcenter.apiPayload.code.status;

import com.example.umcmatchingcenter.apiPayload.code.BaseErrorCode;
import com.example.umcmatchingcenter.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),


    // 멤버 관려 에러
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "존재하지 않는 사용자입니다."),
    MEMBER_WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER4002", "잘못된 비밀번호 입니다"),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4003", "닉네임은 필수 입니다."),
    MEMBER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4004", "이미 등록된 사용자 입니다."),

    WRONG_AUTH_CODE(HttpStatus.BAD_REQUEST, "EMAIL4002", "잘못된 인증 코드 입니다"),
    FAIL_CREATE_CODE(HttpStatus.BAD_REQUEST, "EMAIL4003", "코드 생성에 실패했습니다"),
    FAIL_CREATE_EMAIL(HttpStatus.BAD_REQUEST, "EMAIL4004", "이메일 전송에 실패했습니다"),

    //jwt
    JWT_FORBIDDEN(HttpStatus.FORBIDDEN, "JWT4001", "권한이 존재하지 않습니다."),
    JWT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "JWT4002", "자격증명이 유효하지 않습니다."),
    JWT_EXPIRATION(HttpStatus.UNAUTHORIZED, "JWT4003", "만료된 jwt 토큰입니다"),
    JWT_WRONG_SIGNATURE(HttpStatus.UNAUTHORIZED, "JWT4004", "잘못된 jwt 서명입니다"),
    JWT_WRONG_REFRESHTOKEN(HttpStatus.UNAUTHORIZED, "JWT4005", "잘못된 refresh 토큰입니다."),

    //알림
    NO_DELETE_ALARM(HttpStatus.BAD_REQUEST, "ALARM4001", "삭제할 알림이 존재하지 않습니다."),
    NO_ALARM_LIST(HttpStatus.BAD_REQUEST, "ALARM4002", "알림이 존재하지 않습니다."),



    // 예시,,,
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다."),

    // Ror test
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "이거는 테스트");



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
