package com.vivid.apiserver.domain.video.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vivid.apiserver.domain.individual_video.dao.repository.IndividualVideoRepository;
import com.vivid.apiserver.domain.video.dao.VideoRepository;
import com.vivid.apiserver.domain.video.dto.request.VideoSaveRequest;
import com.vivid.apiserver.test.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

@Sql("/data-test.sql")
class WebexVideoApiTest extends IntegrationTest {

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
    @DisplayName("WebexVideo AccessToken 저장 Api")
    public void saveWebexAccessToken() throws Exception {

        // given
        final String code = "webex_test_code_020132";

        // when
        ResultActions resultActions = mvc.perform(post("/api/webex/token/" + code)
                .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("WebexVideo 직접 업로드 Api")
    public void upload() throws Exception {

        // given
        final long videoSpaceId = 7L;
        final long recordingId = 100L;
        VideoSaveRequest request = VideoSaveRequest.builder().title("test").description("test_des").build();

        // when
        ResultActions resultActions = mvc.perform(post("/api/webex/recordings/" + videoSpaceId + "/" + recordingId)
                .header(HttpHeaders.AUTHORIZATION, header)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.id").isNotEmpty());
    }

    @Test
    @DisplayName("WebexVideo 녹화본 리스트 조회 Api")
    public void getWebexRecordings() throws Exception {

        // when
        ResultActions resultActions = mvc.perform(get("/api/webex/recordings")
                .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("data[0].recordingId").isNotEmpty());
    }

    @Test
    @DisplayName("Webex 로그인 리다이렉트 Api")
    public void redirectWebexLoginUrl() throws Exception {

        // when
        ResultActions resultActions = mvc.perform(get("/api/login/webex")
                .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions.andExpect(status().is3xxRedirection());
    }

}