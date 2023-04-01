package com.vivid.apiserver.domain.video_space.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vivid.apiserver.domain.individual_video.service.query.IndividualVideoQueryService;
import com.vivid.apiserver.domain.video.dao.VideoRepository;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video_space.dao.VideoSpaceParticipantRepository;
import com.vivid.apiserver.domain.video_space.dao.VideoSpaceRepository;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.dto.request.VideoSpaceSaveRequest;
import com.vivid.apiserver.test.IntegrationTest;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

@Sql("/data-test.sql")
class VideoSpaceApiTest extends IntegrationTest {

    @Autowired
    private VideoSpaceRepository videoSpaceRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoSpaceParticipantRepository videoSpaceParticipantRepository;

    @Autowired
    private IndividualVideoQueryService individualVideoQueryService;

    private String header;

    @BeforeEach
    public void setUp() {
        header = createAuthHeader();
    }

    @Test
    @DisplayName("VideoSpace 리스트 조회 Api")
    public void getAllByUserApiTest() throws Exception {

        // when
        ResultActions resultActions = mvc.perform(get("/api/video-space")
                .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data[0].id").isNotEmpty())
                .andExpect(jsonPath("data[0].videos").isNotEmpty());
    }

    @Test
    @DisplayName("VideoSpace 단건 조회 Api")
    public void getByIdApiTest() throws Exception {

        // given
        final long videoSpaceId = 7L;

        // when
        ResultActions resultActions = mvc.perform(get("/api/video-space/" + videoSpaceId)
                .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.id").isNotEmpty())
                .andExpect(jsonPath("data.videos").isNotEmpty())
                .andExpect(jsonPath("data.videos[0].id").isNotEmpty());
    }

    @Test
    @DisplayName("VideoSpace 생성 Api")
    public void save() throws Exception {

        // given
        final VideoSpaceSaveRequest request = VideoSpaceSaveRequest.builder()
                .name("test video space")
                .description("test01")
                .build();

        // when
        ResultActions resultActions = mvc.perform(post("/api/video-space")
                .header(HttpHeaders.AUTHORIZATION, header)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.id").isNotEmpty());
    }

    @Test
    @DisplayName("VideoSpace 삭제 Api")
    public void deleteById() throws Exception {

        // given
        final long videoSpaceId = 7L;
        final VideoSpace videoSpace = videoSpaceRepository.findById(videoSpaceId).get();
        final List<Long> videoIds = getVideoIds(videoSpace);
        final List<Long> videoSpaceParticipantIds = getVideoSpaceParticipantIds(videoSpace);

        // when
        ResultActions resultActions = mvc.perform(delete("/api/video-space/" + videoSpaceId)
                .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions.andExpect(status().isOk());
        assertThat(videoSpaceRepository.findById(videoSpaceId).isPresent()).isFalse();
        assertThat(videoRepository.findAllById(videoIds)).isEmpty();
        assertThat(videoSpaceParticipantRepository.findAllById(videoSpaceParticipantIds)).isEmpty();
        assertThat(individualVideoQueryService.findAllWithVideoByVideoSpaceParticipant(
                videoSpace.getVideoSpaceParticipants())).isEmpty();
    }

    private List<Long> getVideoIds(VideoSpace videoSpace) {
        return videoSpace.getVideos()
                .stream()
                .map(Video::getId)
                .collect(Collectors.toList());
    }

    private List<Long> getVideoSpaceParticipantIds(VideoSpace videoSpace) {
        return videoSpace.getVideoSpaceParticipants().stream()
                .map(VideoSpaceParticipant::getId)
                .collect(Collectors.toList());
    }
}