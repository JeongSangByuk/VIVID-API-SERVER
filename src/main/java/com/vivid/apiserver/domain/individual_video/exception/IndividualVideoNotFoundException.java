package com.vivid.apiserver.domain.individual_video.exception;

import com.vivid.apiserver.global.error.exception.NotFoundException;

public class IndividualVideoNotFoundException extends NotFoundException {

    public IndividualVideoNotFoundException() {
        super("video is not found");
    }

    public IndividualVideoNotFoundException(String id) {
        super(id.toString() + " is not found");
    }
}
