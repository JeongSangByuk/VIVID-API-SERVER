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
class VideoSpaceHostApiTest extends IntegrationTest {

    @BeforeEach
    public void setUp() {
        header = createAuthHeader();
    }

    @Test
    @DisplayName("VideoSpace Host 리스트 조회 Api")
    public void getHostedAllApiTest() throws Exception {

        // when
        ResultActions resultActions = mvc.perform(get("/api/video-space/hosted")
                .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data[0].id").isNotEmpty())
                .andExpect(jsonPath("data[0].videos").isNotEmpty())
                .andExpect(jsonPath("data[0].users").isNotEmpty());
    }

    @Test
    @DisplayName("VideoSpace Host 단건 조회 Api")
    public void getHostedApiTest() throws Exception {

        // given
        final long videoSpaceId = 7L;

        // when
        ResultActions resultActions = mvc.perform(get("/api/video-space/hosted/" + videoSpaceId)
                .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.id").isNotEmpty())
                .andExpect(jsonPath("data.videos").isNotEmpty())
                .andExpect(jsonPath("data.users").isNotEmpty());

    }

}