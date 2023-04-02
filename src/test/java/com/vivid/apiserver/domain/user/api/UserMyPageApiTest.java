package com.vivid.apiserver.domain.user.api;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
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
public class UserMyPageApiTest extends IntegrationTest {

    @BeforeEach
    public void setUp() {
        header = createAuthHeader();
    }

    @Test
    @DisplayName("User 마이페이지 조회 Api")
    public void getUserMyPageDashboardData() throws Exception {

        // when
        ResultActions resultActions = mvc.perform(get("/api/my-page/dashboard")
                .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.email").isNotEmpty())
                .andExpect(jsonPath("data.dashboardIndividualVideos").isNotEmpty())
                .andExpect(jsonPath("data.dashboardIndividualVideos[0].videoSpaceId").isNotEmpty())
                .andExpect(jsonPath("data.dashboardIndividualVideos[0].individualVideoId").isNotEmpty())
                .andExpect(jsonPath("data.videoSpaceCount").value(not(equalTo(0))))
                .andExpect(jsonPath("data.totalIndividualVideoCount").value(not(equalTo(0))))
                .andExpect(jsonPath("data.completedIndividualVideoCount").value(not(equalTo(0))));
    }

}
