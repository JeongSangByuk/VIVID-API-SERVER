package com.vivid.apiserver.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE("C01", "Invalid Input Value.", HttpStatus.BAD_REQUEST.value()),
    METHOD_NOT_ALLOWED("C02", "Invalid Input Value.", HttpStatus.METHOD_NOT_ALLOWED.value()),
    ENTITY_NOT_FOUND("C03", "Entity Not Found.", HttpStatus.BAD_REQUEST.value()),
    INTERNAL_SERVER_ERROR("C04", "Server Error.", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    INVALID_TYPE_VALUE("C05", " Invalid Type Value.", HttpStatus.BAD_REQUEST.value()),

    //Auth
    LOGIN_INFO_NOT_FOUND("A01", "Login Info Not Found", HttpStatus.BAD_REQUEST.value()),

    // User
    USER_ACCESS_DENIED("U01", "User Access is Denied.", HttpStatus.UNAUTHORIZED.value()),
    USER_NOT_FOUND("U02", "User is not Found.", HttpStatus.BAD_REQUEST.value()),
    PASSWORD_FAILED_EXCEEDED("U03", "Password attempts exceeded.", HttpStatus.BAD_REQUEST.value()),
    EMAIL_DUPLICATION("U04", "Email is Duplication", HttpStatus.BAD_REQUEST.value()),

    // User - Token
    ACCESS_TOKEN_EXPIRED("AT01", "Access Token is Expired", HttpStatus.UNAUTHORIZED.value()),
    ACCESS_TOKEN_INVALID("AT02", "Access Token is Invalid.", HttpStatus.UNAUTHORIZED.value()),
    ACCESS_TOKEN_NOT_FOUND_IN_HEADER("AT03", "Access Token is not Found in Header.", HttpStatus.UNAUTHORIZED.value()),
    WEBEX_ACCESS_TOKEN_NOT_FOUND_IN_HEADER("AT03-1", "Webex Access Token is not Found.",
            HttpStatus.UNAUTHORIZED.value()),
    ACCESS_TOKEN_NOT_FOUND_IN_COOKIE("AT04", "Access Token is not Found in Cookie.", HttpStatus.UNAUTHORIZED.value()),
    REFRESH_TOKEN_NOT_FOUND("AT05", "Refreshed Token is not Found.", HttpStatus.UNAUTHORIZED.value()),
    REFRESH_TOKEN_EXPIRED("AT06", "Refreshed Token is Expired.", HttpStatus.UNAUTHORIZED.value()),

    // Video
    VIDEO_ACCESS_DENIED("V01", "Video Access is Denied.", HttpStatus.UNAUTHORIZED.value()),
    VIDEO_NOT_FOUND("V02", "Video is not Found.", HttpStatus.BAD_REQUEST.value()),
    VIDEO_UPLOAD_FAILED("V03", "Video Upload is failed.", HttpStatus.INTERNAL_SERVER_ERROR.value()),

    // Individual Video
    INDIVIDUAL_VIDEO_ACCESS_DENIED("IV01", "Individual Video Access is Denied.", HttpStatus.UNAUTHORIZED.value()),
    INDIVIDUAL_VIDEO_NOT_FOUND("IV02", "Individual Video is not Found.", HttpStatus.BAD_REQUEST.value()),
    IMAGE_UPLOAD_FAILED("IV03", "Image Upload is failed.", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    TEXT_MEMO_NOT_EXIST("IV04", "TextMemo is not Exist", HttpStatus.BAD_REQUEST.value()),

    // Video Space
    VIDEO_SPACE_ACCESS_DENIED("VS01", "Video Space Access is Denied.", HttpStatus.UNAUTHORIZED.value()),
    VIDEO_SPACE_NOT_FOUND("VS02", "Video Space is not Found.", HttpStatus.BAD_REQUEST.value()),
    VIDEO_SPACE_USER_DUPLICATION("VS03", "User is Already Added in Video Space.", HttpStatus.BAD_REQUEST.value()),
    VIDEO_SPACE_PARTICIPANT_NOT_FOUND("VS04", "Video Space Participant is not Found.", HttpStatus.BAD_REQUEST.value()),
    VIDEO_SPACE_HOST_ACCESS_REQUIRED("VS05", "Video Space Hosted Access is Required.", HttpStatus.BAD_REQUEST.value()),
    VIDEO_SPACE_HOST_DELETE_NOT_ALLOWED("VS06", "Host Email can't be Deleted.", HttpStatus.BAD_REQUEST.value()),

    // external API
    EXTERNAL_API_FAILED("E01", "External API Request is failed.", HttpStatus.INTERNAL_SERVER_ERROR.value());

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
