package com.example.onui.global.config.error.data

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {

    // 400
    FEIGN_CLIENT(HttpStatus.BAD_REQUEST, "Feign Client 오류"),
    ALREADY_WROTE_DIARY(HttpStatus.BAD_REQUEST, "이미 감정을 기록하셨습니다."),
    ALREADY_POSTED_TIMELINE(HttpStatus.BAD_REQUEST, "이미 타임라인을 업로드하셨습니다."),
    INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 확장자입니다."),

    // 401
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),

    // 403
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "권한이 거부 되었습니다."),

    // 404
    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "감정 기록을 찾을 수 없습니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러")
}
