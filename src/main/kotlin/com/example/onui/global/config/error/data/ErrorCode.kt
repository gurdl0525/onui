package com.example.onui.global.config.error.data

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {

    // 400
    FEIGN_CLIENT(HttpStatus.BAD_REQUEST, "Feign Client 오류"),
    INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 확장자입니다."),
    INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "date가 올비르지 않습니다."),
    TYPE_COAST_MISS_MATCHED(HttpStatus.BAD_REQUEST, "type이 ASSIGN일 때 반드시 coast를 설정해야합니다."),
    CAN_NOT_BUY_THEME(HttpStatus.BAD_REQUEST, "쌀이 부족합니다."),
    INVALID_IMG_URL(HttpStatus.BAD_REQUEST, "잘못된 이미지 URL입니다."),

    // 401
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),

    // 403
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "권한이 거부 되었습니다."),

    // 404
    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "감정 기록을 찾을 수 없습니다."),
    TIMELINE_NOT_FOUND(HttpStatus.NOT_FOUND, "타임라인을 찾을 수 없습니다."),
    THEME_NOT_FOUND(HttpStatus.NOT_FOUND, "테마를 찾을 수 없습니다."),
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "미션을 찾을 수 없습니다."),

    // 409
    ALREADY_WROTE_DIARY(HttpStatus.CONFLICT, "이미 감정을 기록하셨습니다."),
    ALREADY_POSTED_TIMELINE(HttpStatus.CONFLICT, "이미 타임라인을 업로드하셨습니다."),
    ALREADY_POSTED_THEME(HttpStatus.CONFLICT, "이미 테마를 업로드하셨습니다."),
    ALREADY_CREATED_MISSION(HttpStatus.CONFLICT, "이미 미션을 등록하셨습니다."),
    ALREADY_FINISHED_MISSION(HttpStatus.CONFLICT, "이미 미션을 완료하셨습니다."),
    NOT_BOUGHT_THEME(HttpStatus.CONFLICT, "테마를 구매하지 않았습니다."),
    ALREADY_BOUGHT_THEME(HttpStatus.CONFLICT, "이미 테마를 구매했습니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러")
}
