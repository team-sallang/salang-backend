package com.salang.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ErrorCode Convention
 * - 도메인 별로 나누어 관리
 * - [주체_이유] 형태로 생성
 * - 코드는 도메인명 앞에서부터 1~2글자로 사용
 * - 메시지는 "~~다."로 마무리
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Global
    INTERNAL_SERVER_ERROR(500, "G001", "내부 서버 오류입니다."),
    METHOD_NOT_ALLOWED(405, "G002", "허용되지 않은 HTTP method입니다."),
    INPUT_VALUE_INVALID(400, "G003", "유효하지 않은 입력입니다."),
    INPUT_TYPE_INVALID(400, "G004", "입력 타입이 유효하지 않습니다."),
    HTTP_MESSAGE_NOT_READABLE(400, "G005", "request message body가 없거나, 값 타입이 올바르지 않습니다."),
    HTTP_HEADER_INVALID(400, "G006", "request header가 유효하지 않습니다."),
    IMAGE_TYPE_NOT_SUPPORTED(400, "G007", "지원하지 않는 이미지 타입입니다."),
    FILE_CONVERT_FAIL(500, "G008", "변환할 수 없는 파일입니다."),
    ENTITY_TYPE_INVALID(500, "G009", "올바르지 않은 entity type 입니다."),
    FILTER_MUST_RESPOND(500, "G010", "필터에서 처리해야 할 요청이 Controller에 접근하였습니다."),

    // User
    USER_NOT_FOUND(404, "U001", "로그인 유저를 찾을 수 없습니다."),
    USER_NICKNAME_ALREADY_EXIST(400, "U002", "이미 사용중인 닉네임입니다."),

    // Hobby
    HOBBY_NOT_FOUND(404, "H001", "취미를 찾을 수 없습니다."),
    INVALID_HOBBY_SIZE(400, "H002", "취미의 개수는 3개를 넘을 수 없습니다."),
    DUPLICATE_HOBBY(400, "H003", "중복된 취미를 등록할 수 없습니다."),

    // AWS
    BAD_EXTENSION(400, "A001", "허용되지 않은 형식의 파일입니다."),
    EMPTY_EXTENSION(400, "A002", "파일의 확장자가 없습니다."),
    S3_UPLOAD_FAILED(500, "A003", "S3에 업로드를 실패하였습니다."),
    S3_DELETE_FAILED(500, "A004", "기존 프로필 이미지 삭제 실패하였습니다."),
    BAD_FILE_NAME(400, "A005", "비어있거나 잘못된 파일 이름입니다.");

    private final int status;
    private final String code;
    private final String message;
}
