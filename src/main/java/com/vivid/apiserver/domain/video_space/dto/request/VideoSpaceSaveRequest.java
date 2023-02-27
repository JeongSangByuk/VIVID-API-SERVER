package com.vivid.apiserver.domain.video_space.dto.request;

import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VideoSpaceSaveRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Builder
    public VideoSpaceSaveRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public VideoSpace toEntity(String hostEmail) {
        return VideoSpace.builder()
                .name(this.name)
                .description(this.description)
                .hostEmail(hostEmail)
                .isIndividualVideoSpace(false)
                .build();
    }
}
