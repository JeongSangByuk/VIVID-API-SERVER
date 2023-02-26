package com.vivid.apiserver.domain.individual_video.api;

import com.vivid.apiserver.test.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class IndividualVideoApiTest extends IntegrationTest {

    @Test
    @DisplayName("[IndividualVideoApi] individualVideoList_get")
    public void individualVideoList_get() throws Exception {

//        //given
//        String individualVideoId = TextMemoStateBuilder.getRandomIndividualVideoId();
//        TextMemoStateRedisSaveRequest textMemoStateRedisSaveRequest = TextMemoStateBuilder.redisSaveRequestBuilder(individualVideoId);
//
//        // when
//        ResultActions resultActions = mvc.perform(post("/api/videos")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(textMemoStateRedisSaveRequest)));
//
//        //then
//        resultActions.andExpect(status().isOk());
    }


}