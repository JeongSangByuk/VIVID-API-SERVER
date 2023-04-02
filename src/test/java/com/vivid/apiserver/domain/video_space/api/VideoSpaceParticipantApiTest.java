package com.vivid.apiserver.domain.video_space.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vivid.apiserver.domain.individual_video.dao.repository.IndividualVideoRepository;
import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.service.query.UserQueryService;
import com.vivid.apiserver.domain.video_space.dao.VideoSpaceParticipantRepository;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.service.query.VideoSpaceParticipantQueryService;
import com.vivid.apiserver.test.IntegrationTest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

@Sql("/data-test.sql")
class VideoSpaceParticipantApiTest extends IntegrationTest {

    @Autowired
    private VideoSpaceParticipantQueryService videoSpaceParticipantQueryService;

    @Autowired
    private UserQueryService userQueryService;

    @Autowired
    private IndividualVideoRepository individualVideoRepository;

    @Autowired
    private VideoSpaceParticipantRepository videoSpaceParticipantRepository;

    @BeforeEach
    public void setUp() {
        header = createAuthHeader();
    }

    @Test
    @DisplayName("VideoSpace 참가자 추가 Api")
    public void save() throws Exception {

        // given
        final long videoSpaceId = 7L;
        final String userEmail = "test02@gmail.com";

        // when
        ResultActions resultActions = mvc.perform(post("/api/video-space/" + videoSpaceId + "/" + userEmail)
                .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.id").isNotEmpty());
    }

    @Test
    @DisplayName("VideoSpace 참가자 삭제 Api")
    public void deleteById() throws Exception {

        // given
        final long videoSpaceId = 7L;
        final String userEmail = "test01@gmail.com";
        User user = userQueryService.findByEmail(userEmail);

        VideoSpaceParticipant videoSpaceParticipant = videoSpaceParticipantQueryService
                .findWithVideoSpaceByUserIdAndVideoSpaceId(user.getId(), videoSpaceId);

        List<UUID> individualVideoIds = getIndividualVideoIds(videoSpaceParticipant);

        // when
        ResultActions resultActions = mvc.perform(delete("/api/video-space/" + videoSpaceId + "/" + userEmail)
                .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions
                .andExpect(status().isOk());

        assertThat(videoSpaceParticipantRepository.findById(videoSpaceParticipant.getId()).isPresent()).isFalse();
        assertThat(individualVideoRepository.findAllById(individualVideoIds)).isEmpty();
    }

    @NotNull
    private List<UUID> getIndividualVideoIds(VideoSpaceParticipant videoSpaceParticipant) {
        return videoSpaceParticipant.getIndividualVideos()
                .stream()
                .map(IndividualVideo::getId)
                .collect(Collectors.toList());
    }


}