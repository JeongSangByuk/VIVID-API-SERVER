package com.vivid.apiserver.domain.individual_video.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
