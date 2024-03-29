package com.vivid.apiserver.domain.video_space.dto.response;

import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.video.dto.response.VideoGetResponse;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoSpaceGetResponse {

    private Long id;

    private String name;

    private String description;

    private List<VideoGetResponse> videos = new ArrayList<>();

    public static VideoSpaceGetResponse of(VideoSpaceParticipant videoSpaceParticipant,
            List<IndividualVideo> individualVideos) {

        VideoSpace videoSpace = videoSpaceParticipant.getVideoSpace();

        return getVideoSpaceGetResponse(individualVideos, videoSpace);
    }

    public static VideoSpaceGetResponse of(VideoSpaceParticipant videoSpaceParticipant,
            Map<Long, List<IndividualVideo>> individualVideosMap) {

        VideoSpace videoSpace = videoSpaceParticipant.getVideoSpace();

        List<IndividualVideo> individualVideos = individualVideosMap.getOrDefault(
                videoSpaceParticipant.getVideoSpace().getId(), Collections.emptyList());

        return getVideoSpaceGetResponse(individualVideos, videoSpace);
    }

    private static VideoSpaceGetResponse getVideoSpaceGetResponse(List<IndividualVideo> individualVideos,
            VideoSpace videoSpace) {

        List<VideoGetResponse> videoGetResponses = individualVideos
                .stream()
                .map(individualVideo -> VideoGetResponse.of(individualVideo.getVideo(), individualVideo))
                .collect(Collectors.toList());

        return VideoSpaceGetResponse.builder()
                .id(videoSpace.getId())
                .name(videoSpace.getName())
                .description(videoSpace.getDescription())
                .videos(videoGetResponses)
                .build();
    }
}
