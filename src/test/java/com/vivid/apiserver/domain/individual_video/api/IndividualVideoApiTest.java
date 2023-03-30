package com.vivid.apiserver.domain.individual_video.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vivid.apiserver.domain.individual_video.dao.repository.IndividualVideoRepository;
import com.vivid.apiserver.test.IntegrationTest;
import java.io.FileInputStream;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

@Sql("/data-test.sql")
class IndividualVideoApiTest extends IntegrationTest {

    @Autowired
    private IndividualVideoRepository individualVideoRepository;

    private String header;

    @BeforeEach
    public void setUp() {
        header = createAuthHeader();
    }


    @Test
    @DisplayName("IndividualVideo 상세 정보 조회 Api")
    public void getDetailsApiTest() throws Exception {

        // given
        String individualVideoId = "25edc4c9-28a9-4733-b0d5-3ebb6425b7ad";

        // when
        ResultActions resultActions = mvc.perform(
                get("/api/individual-videos/" + individualVideoId)
                        .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.title").isNotEmpty());
    }


    @Test
    @DisplayName("IndividualVideo 삭제 Api")
    public void deleteApiTest() throws Exception {

        // given
        String individualVideoId = "25edc4c9-28a9-4733-b0d5-3ebb6425b7ad";

        // when
        ResultActions resultActions = mvc.perform(
                delete("/api/individual-videos/" + individualVideoId)
                        .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions.andExpect(status().isOk());
        assertThat(individualVideoRepository.findById(UUID.randomUUID()).isPresent()).isFalse();
    }

    @Test
    @DisplayName("IndividualVideo 이미지 업로드 Api")
    public void uploadSnapshotImageApiTest() throws Exception {

        // given
        String individualVideoId = "25edc4c9-28a9-4733-b0d5-3ebb6425b7ad";
        MockMultipartFile file = new MockMultipartFile("image", "test.png", "image/png",
                new FileInputStream("src/test/resources/test.png"));

        // when
        ResultActions resultActions = mvc.perform(
                multipart("/api/individual-videos/" + individualVideoId + "/snapshot")
                        .file(file)
                        .header(HttpHeaders.AUTHORIZATION, header)
                        .param("video-time", String.valueOf(12)));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.filePath").isNotEmpty());
    }

    @Test
    @DisplayName("IndividualVideo 진행도 변경 Api")
    public void updateProgressRateApiTest() throws Exception {

        // given
        String individualVideoId = "25edc4c9-28a9-4733-b0d5-3ebb6425b7ad";

        // when
        ResultActions resultActions = mvc.perform(
                put("/api/individual-videos/" + individualVideoId + "/progress-rate/23")
                        .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("IndividualVideo 진행도 변경 Api")
    public void updateLastAccessTimeApiTest() throws Exception {

        // given
        String individualVideoId = "25edc4c9-28a9-4733-b0d5-3ebb6425b7ad";

        // when
        ResultActions resultActions = mvc.perform(
                put("/api/individual-videos/" + individualVideoId + "/accessed")
                        .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions.andExpect(status().isOk());
    }

//    @Test
//    @DisplayName("[IndividualVideoApi] individualVideoList_get")
//    public void individualVideoList_get() throws Exception {
//
//        //given
//        String individualVideoId = TextMemoStateBuilder.getRandomIndividualVideoId();
//        TextMemoStateRedisSaveRequest textMemoStateRedisSaveRequest = TextMemoStateBuilder.redisSaveRequestBuilder(
//                individualVideoId);
//
//        // when
//        ResultActions resultActions = mvc.perform(post("/api/videos")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(textMemoStateRedisSaveRequest)));
//
//        //then
//        resultActions.andExpect(status().isOk());
//    }

}