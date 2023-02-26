package com.vivid.apiserver.domain.video_space.dto;

import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VideoSpaceParticipantSaveResponse {

    private String userEmail;

    private Long id;

    @Builder
    public VideoSpaceParticipantSaveResponse(VideoSpaceParticipant videoSpaceParticipant) {
        this.userEmail = videoSpaceParticipant.getUser().getEmail();
        this.id = videoSpaceParticipant.getVideoSpace().getId();
    }
}
