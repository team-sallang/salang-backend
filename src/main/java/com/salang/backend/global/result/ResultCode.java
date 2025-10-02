package com.salang.backend.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ResultCode Convention
 * - 도메인 별로 나누어 관리
 * - [동사_목적어_SUCCESS] 형태로 생성
 * - 코드는 도메인명 앞에서부터 1~2글자로 사용
 * - 메시지는 "~~다."로 마무리
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // User
    GET_USER_PROFILE_SUCCESS(200, "U001", "회원 프로필을 조회하였습니다."),
    EDIT_PROFILE_IMAGE_SUCCESS(200, "U002", "회원 프로필 사진을 수정하였습니다."),
    EDIT_NICKNAME_SUCCESS(200, "U003", "회원 닉네임을 수정하였습니다.");

    private final int status;
    private final String code;
    private final String message;
}
