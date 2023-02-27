package com.vivid.apiserver.global.success;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    /**
     * 200 OK
     */
    OK_SUCCESS("S01", "성공입니다.", HttpStatus.OK.value()),

    /**
     * 201 CREATED
     */
    CREATED_SUCCESS("S01", "생성 성공입니다.", HttpStatus.CREATED.value()),

    /**
     * 202 ACCEPTED
     */

    /**
     * 204 NO_CONTENT
     */
    ;

    private final String code;
    private final String message;
    private final int status;
}
