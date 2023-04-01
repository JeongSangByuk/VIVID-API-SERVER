package com.vivid.apiserver.domain.video.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vivid.apiserver.domain.individual_video.dao.repository.IndividualVideoRepository;
import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.video.dao.VideoRepository;
import com.vivid.apiserver.test.IntegrationTest;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

@Sql("/data-test.sql")
class VideoApiTest extends IntegrationTest {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private IndividualVideoRepository individualVideoRepository;

    private String header;

    @BeforeEach
    public void setUp() {
        header = createAuthHeader();
    }

    @Test
    @DisplayName("Video 직접 업로드 Api")
    public void upload() throws Exception {

        // given
        final long videoSpaceId = 7L;
        final MockMultipartFile videoFile = new MockMultipartFile("video", "test.mp4", "mp4",
                new FileInputStream("src/test/resources/test.mp4"));

        // when
        ResultActions resultActions = mvc.perform(multipart("/api/videos/" + videoSpaceId)
                .file(videoFile)
                .part(new MockPart("title", "test_title".getBytes(StandardCharsets.UTF_8)))
                .part(new MockPart("description", "test_description".getBytes(StandardCharsets.UTF_8)))
                .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.id").isNotEmpty());
    }

    @Test
    @DisplayName("Video 삭제 Api")
    public void deleteById() throws Exception {

        // given
        final long videoId = 1L;
        List<UUID> individualVideoIds = getIndividualVideoIds(videoId);

        // when
        ResultActions resultActions = mvc.perform(delete("/api/videos/" + videoId)
                .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions.andExpect(status().isOk());
        assertThat(individualVideoRepository.findAllById(individualVideoIds)).isEmpty();
    }

    @Test
    @DisplayName("Video 업로드 상태 변경 Api")
    public void changeUploadStateAfterUploaded() throws Exception {

        // given
        final long videoId = 1L;

        // when
        ResultActions resultActions = mvc.perform(put("/api/videos/" + videoId + "/uploaded")
                .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions.andExpect(status().isOk());
    }

    @NotNull
    private List<UUID> getIndividualVideoIds(long videoId) {
        return videoRepository.findById(videoId).get().getIndividualVideos()
                .stream()
                .map(IndividualVideo::getId)
                .collect(Collectors.toList());
    }

}