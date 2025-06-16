package com.capstone2.capstone2.global.error.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // Auth 관련
    AUTH_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_4001", "토큰이 만료되었습니다."),
    AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_4002", "토큰이 유효하지 않습니다."),
    INVALID_LOGIN_REQUEST(HttpStatus.UNAUTHORIZED, "AUTH_4003", "올바른 아이디나 패스워드가 아닙니다."),
    NOT_EQUAL_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_4004", "리프레시 토큰이 다릅니다."),
    NOT_CONTAIN_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_4005", "해당하는 토큰이 저장되어있지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "AUTH_4006", "비밀번호가 일치하지 않습니다."),
    AUTH_INVALID_CODE(HttpStatus.UNAUTHORIZED, "AUTH_4008", "코드가 유효하지 않습니다."),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "AUTH_4009", "로그인이 필요합니다."),

    // Member 관련
    MEMBER_ID_NULL(HttpStatus.BAD_REQUEST, "MEMBER_4001", "사용자 아이디는 필수 입니다."),
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER_4002", "사용자가 없습니다."),

    // GroupPurchase 관련
    GROUP_PURCHASE_ID_NULL(HttpStatus.BAD_REQUEST, "GROUP_PURCHASE_4001", "공동구매 아이디는 필수 입니다."),
    GROUP_PURCHASE_NOT_FOUND(HttpStatus.BAD_REQUEST, "GROUP_PURCHASE_4002", "해당 공동구매가 없습니다."),

    // Participation 관련
    PARTICIPATION_UP_TO_MAX(HttpStatus.BAD_REQUEST, "PARTICIPATION_4001", "최대 참여 인원에 도달했습니다."),
    PARTICIPATION_NOT_ACTIVE(HttpStatus.BAD_REQUEST, "PARTICIPATION_4002", "해당 공동 구매는 마감되었습니다"),
    PARTICIPATION_ALREADY(HttpStatus.BAD_REQUEST, "PARTICIPATION_4003", "이미 해당 공동 구매에 참여중입니다."),
    PARTICIPATION_NOT_IN(HttpStatus.BAD_REQUEST, "PARTICIPATION_4004", "해당 공구에 참여 중이지 않습니다."),

    // 카테고리관련
    MAIN_CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "CATEGORY_4001", "해당 메인 카테고리가 없습니다."),
    CATEGORY_UPDATE_EXCEED_LIMIT(HttpStatus.BAD_REQUEST, "CATEGORY_4002", "최대 5개까지만 등록할 수 있습니다."),

    // Chat 관련
    CHAT_ROON_NOT_FOUND(HttpStatus.BAD_REQUEST, "CHATROOM_4001", "해당 채팅방은 존재하지 않습니다"),

    // 찜
    ALREADY_LIKED(HttpStatus.BAD_REQUEST, "MEMBERLIKED_4001", "이미 좋아요를 눌렀습니다."),
    LIKE_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBERLIKED_4002", "찜이 눌러져있지 않습니다."),


            ;


    private final HttpStatus httpStatus;
    private final String code;
    private String message;

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
                .build();
    }
}
