package com.vivid.apiserver.domain.video.exception;

import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class ImageUploadFailedException extends InvalidValueException {

    public ImageUploadFailedException() {
        super(ErrorCode.IMAGE_UPLOAD_FAILED);
    }
}
