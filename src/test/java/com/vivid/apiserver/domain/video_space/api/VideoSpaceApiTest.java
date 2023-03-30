package com.vivid.apiserver.domain.video_space.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vivid.apiserver.test.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

@Sql("/data-test.sql")
class VideoSpaceApiTest extends IntegrationTest {

    private String header;

    @BeforeEach
    public void setUp() {
        header = createAuthHeader();
    }


    @Test
    @DisplayName("VideoSpace 리스트 조회 Api")
    public void getDetailsApiTest() throws Exception {

        // when
        ResultActions resultActions = mvc.perform(
                get("/api/video-space")
                        .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.title").isNotEmpty());
    }


}