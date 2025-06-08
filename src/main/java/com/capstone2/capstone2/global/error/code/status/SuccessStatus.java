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
    GROUP_PURCHASE_FETCH_ALL_OK(HttpStatus.OK, "GROUP_PURCHASE2002", "공동 구매 전체 조회가 완료되었습니다."),
    GROUP_PURCHASE_FETCH_DETAIL_OK(HttpStatus.OK, "GROUP_PURCHASE2003", "공동 구매 상세 조회가 완료되었습니다."),
    GROUP_PURCHASE_UPDATE_OK(HttpStatus.OK, "GROUP_PURCHASE2004", "공동 구매 정보 수정이 완료되었습니다."),
    GROUP_PURCHASE_DELETE_OK(HttpStatus.OK, "GROUP_PURCHASE2005", "공동 구매 삭제가 완료되었습니다."),
    GROUP_PURCHASE_FETCH_RANKING_OK(HttpStatus.OK, "GROUP_PURCHASE2006", "인기 공동 구매 조회가 완료되었습니다."),
    GROUP_PURCHASE_FETCH_BY_CATEGORY(HttpStatus.OK, "GROUP_PURCHASE2007", "카테고리 별 공동 구매 목록 조회가 완료되었습니다."),
    GROUP_PURCHASE_PARTICIPATE_OK(HttpStatus.OK, "GROUP_PURCHASE2008", "공동 구매 참여에 성공했습니다."),
    GROUP_PURCHASE_PARTICIPATE_CANCEL_OK(HttpStatus.OK, "GROUP_PURCHASE2009", "공동 구매 참여 취소에 성공했습니다."),

    // 카카오 유저 확인 - 테스트용
    KAKAO_USER_FETCH_OK(HttpStatus.OK, "KAKAO_SE2001", "사용자 인증이 완료되었습니다.."),

    // 멤버
    GET_MEMBER_SUCCESS(HttpStatus.OK, "MEMBER_SE2001", "회원 정보 조회 성공되었습니다.."),
    MEMBER_UPDATE_OK(HttpStatus.OK, "MEMBER_SE2002", "회원정보 변경이 성공되었습니다.."),
    MEMBER_DELETE_OK(HttpStatus.OK, "MEMBER_SE2002", "회원이 탈퇴되었습니다..")
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
