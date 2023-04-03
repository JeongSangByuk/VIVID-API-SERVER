package com.vivid.apiserver.domain.individual_video.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vivid.apiserver.domain.individual_video.dao.TextMemoCacheDao;
import com.vivid.apiserver.domain.individual_video.dao.TextMemoDao;
import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
import com.vivid.apiserver.domain.individual_video.dto.request.TextMemoCacheSaveRequest;
import com.vivid.apiserver.test.ContainerBaseTest;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

@Sql("/data-test.sql")
class TextMemoApiTest extends ContainerBaseTest {

    @Autowired
    private TextMemoCacheDao textMemoCacheDao;

    @Autowired
    private TextMemoDao textMemoDao;

    @BeforeEach
    public void setUp() {
        header = createAuthHeader();
    }

    @Test
    @DisplayName("TextMemo 캐시 저장 Api")
    public void saveToCacheApiTest() throws Exception {

        final String individualVideoId = "25edc4c9-28a9-4733-b0d5-3ebb6425b7ad";

        //given
        TextMemoCacheSaveRequest request = TextMemoCacheSaveRequest.builder()
                .stateJson("test")
                .videoTime(123L).build();

        // when
        ResultActions resultActions = mvc.perform(
                post("/api/individual-videos/" + individualVideoId + "/cache/text-memo")
                        .header(HttpHeaders.AUTHORIZATION, header)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));

        //then
        resultActions.andExpect(status().isOk());
        assertThat(textMemoCacheDao.findLatestByIndividualVideoId((individualVideoId)).isPresent()).isTrue();
    }

    @Test
    @DisplayName("TextMemo 전체 저장 Api")
    public void saveAllApiTest() throws Exception {

        //given
        final String individualVideoId = "25edc4c9-28a9-4733-b0d5-3ebb6425b7ad";
        textMemoCacheDao.save(createMockTextMemo(), individualVideoId);
        textMemoCacheDao.save(createMockTextMemo(), individualVideoId);

        //when
        ResultActions resultActions = mvc.perform(post("/api/individual-videos/" + individualVideoId + "/text-memos")
                .header(HttpHeaders.AUTHORIZATION, header));

        //then
        resultActions.andExpect(status().isOk());
        assertThat(textMemoCacheDao.findLatestByIndividualVideoId(individualVideoId).isPresent()).isFalse();
        assertThat(textMemoDao.findAllByIndividualId(individualVideoId)).isNotEmpty();
    }


    @Test
    @DisplayName("TextMemo 최신 데이터 캐시 조회 Api")
    public void getLatestFromCache() throws Exception {

        // given
        final String individualVideoId = "25edc4c9-28a9-4733-b0d5-3ebb6425b7ad";
        textMemoCacheDao.save(createMockTextMemo(), individualVideoId);

        // when
        ResultActions resultActions = mvc.perform(
                get("/api/individual-videos/" + individualVideoId + "/cache/text-memo-latest")
                        .header(HttpHeaders.AUTHORIZATION, header));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.stateJson").isNotEmpty())
                .andExpect(jsonPath("data.videoTime").isNotEmpty())
                .andExpect(jsonPath("data.createdAt").isNotEmpty());
    }

    @Test
    @DisplayName("TextMemo 전체 데이터 조회 Api")
    public void getAllFromDb() throws Exception {

        //given
        final String individualVideoId = "25edc4c9-28a9-4733-b0d5-3ebb6425b7ad";
        textMemoDao.save(createMockTextMemo(), individualVideoId);
        textMemoDao.save(createMockTextMemo(), individualVideoId);

        // when
        ResultActions resultActions = mvc.perform(get("/api/individual-videos/" + individualVideoId + "/text-memos")
                .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("data[0].stateJson").isNotEmpty())
                .andExpect(jsonPath("data[0].videoTime").isNotEmpty())
                .andExpect(jsonPath("data[1].stateJson").isNotEmpty())
                .andExpect(jsonPath("data[1].videoTime").isNotEmpty());
    }

    private TextMemo createMockTextMemo() {
        return TextMemo.builder()
                .id(UUID.randomUUID().toString())
                .stateJson("test01")
                .videoTime(123L)
                .createdAt(LocalDateTime.now())
                .build();
    }
}