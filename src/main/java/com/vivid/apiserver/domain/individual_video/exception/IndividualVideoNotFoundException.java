package com.vivid.apiserver.domain.individual_video.exception;

import com.vivid.apiserver.global.error.exception.EntityNotFoundException;

public class IndividualVideoNotFoundException extends EntityNotFoundException {

    public IndividualVideoNotFoundException() {
        super("video is not found");
    }

    public IndividualVideoNotFoundException(String id) {
        super(id.toString() + " is not found");
    }
}
