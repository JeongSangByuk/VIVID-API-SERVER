package com.vivid.apiserver.domain.video_space.dto.response;

import com.vivid.apiserver.domain.user.dto.UserGetResponse;
import com.vivid.apiserver.domain.video.dto.response.HostedVideoGetResponse;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HostedVideoSpaceGetResponse {

    private Long id;

    private String name;

    private String description;

    private List<HostedVideoGetResponse> videos = new ArrayList<>();

    private List<UserGetResponse> users = new ArrayList<>();

    @Builder
    public HostedVideoSpaceGetResponse(VideoSpace videoSpace, List<HostedVideoGetResponse> videos,
            List<UserGetResponse> users) {

        this.id = videoSpace.getId();
        this.name = videoSpace.getName();
        this.description = videoSpace.getDescription();
        this.videos = videos;
        this.users = users;
    }
}
