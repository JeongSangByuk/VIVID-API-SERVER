package com.vivid.apiserver.domain.video_space.dto.response;

import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoSpaceParticipantSaveResponse {

    private Long id;

    public static VideoSpaceParticipantSaveResponse of(VideoSpaceParticipant videoSpaceParticipant) {

        return VideoSpaceParticipantSaveResponse.builder()
                .id(videoSpaceParticipant.getId())
                .build();
    }
}
