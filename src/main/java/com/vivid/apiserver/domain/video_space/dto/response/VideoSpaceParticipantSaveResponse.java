package com.vivid.apiserver.domain.video_space.dto.response;

import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class VideoSpaceParticipantSaveResponse {

    private String userEmail;

    private Long id;

    public static VideoSpaceParticipantSaveResponse of(VideoSpaceParticipant videoSpaceParticipant) {

        return VideoSpaceParticipantSaveResponse.builder()
                .id(videoSpaceParticipant.getVideoSpace().getId())
                .userEmail(videoSpaceParticipant.getUser().getEmail())
                .build();
    }
}
