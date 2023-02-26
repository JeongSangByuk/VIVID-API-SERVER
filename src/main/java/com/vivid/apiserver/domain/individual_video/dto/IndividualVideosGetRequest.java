package com.vivid.apiserver.domain.individual_video.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class IndividualVideosGetRequest {

    @NotNull
    private Long videoSpaceParticipantId;

    @Builder
    public IndividualVideosGetRequest(Long videoSpaceParticipantId) {
        this.videoSpaceParticipantId = videoSpaceParticipantId;
    }
}
