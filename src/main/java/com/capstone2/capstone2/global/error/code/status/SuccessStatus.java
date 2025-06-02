package com.capstone2.capstone2.global.error.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 보안
    USER_REGISTER_OK(HttpStatus.OK, "AUTH2000", "회원 가입이 완료되었습니다."),
    USER_EMAIL_LOGIN_OK(HttpStatus.OK, "AUTH2001", "회원 이메일 로그인이 완료되었습니다."),
    USER_DELETE_OK(HttpStatus.OK, "AUTH2002", "회원 탈퇴가 완료되었습니다."),
    USER_REFRESH_OK(HttpStatus.OK, "AUTH2003", "토큰 재발급이 완료되었습니다."),

    // 네이버 검색 API
    NAVER_SEARCH_FETCH_OK(HttpStatus.OK, "NAVER_SEARCH2001", "네이버 검색 API 호출이 완료되었습니다."),

    // 공동 구매
    GROUP_PURCHASE_CREATE_OK(HttpStatus.OK, "GROUP_PURCHASE2001", "공동 구매 생성이 완료되었습니다."),
    GROUP_PURCHASE_FETCH_ALL_OK(HttpStatus.OK, "GROUP_PURCHASE2002", "공동 구매 전체 조회가 완료되었습니다.")

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}
