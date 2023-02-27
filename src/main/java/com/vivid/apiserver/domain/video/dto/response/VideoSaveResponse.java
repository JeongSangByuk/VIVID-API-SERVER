package com.vivid.apiserver.domain.video.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoSaveResponse {

    private Long id;

    @Builder
    public VideoSaveResponse(Long id) {
        this.id = id;
    }
}
