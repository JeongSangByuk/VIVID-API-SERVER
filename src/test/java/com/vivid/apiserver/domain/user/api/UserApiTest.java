package com.vivid.apiserver.domain.user.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vivid.apiserver.domain.individual_video.dao.repository.IndividualVideoRepository;
import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.individual_video.service.query.IndividualVideoQueryService;
import com.vivid.apiserver.domain.user.dao.UserRepository;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video_space.dao.VideoSpaceParticipantRepository;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.test.IntegrationTest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Sql("/data-test.sql")
public class UserApiTest extends IntegrationTest {

    @Autowired
    private IndividualVideoRepository individualVideoRepository;

    @Autowired
    private VideoSpaceParticipantRepository videoSpaceParticipantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IndividualVideoQueryService individualVideoQueryService;

    @BeforeEach
    public void setUp() {
        header = createAuthHeader();
    }

    @Test
    @DisplayName("User 삭제 Api")
    public void delete() throws Exception {

        // given
        User user = userRepository.findByEmail(NOW_USER_EMAIL).get();

        List<VideoSpaceParticipant> videoSpaceParticipants = user.getVideoSpaceParticipants();
        List<Long> videoParticipantIds = getVideoParticipantIds(videoSpaceParticipants);
        List<UUID> individualVideoIds = getIndividualVideoIds(videoSpaceParticipants);

        // when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.delete("/api/user")
                .header(HttpHeaders.AUTHORIZATION, header));

        // then
        resultActions.andExpect(status().isOk());
        assertThat(userRepository.findById(user.getId()).isPresent()).isFalse();
        assertThat(videoSpaceParticipantRepository.findAllById(videoParticipantIds)).isEmpty();
        assertThat(individualVideoRepository.findAllById(individualVideoIds)).isEmpty();
    }

    private List<Long> getVideoParticipantIds(List<VideoSpaceParticipant> videoSpaceParticipants) {
        return videoSpaceParticipants.stream()
                .map(VideoSpaceParticipant::getId)
                .collect(Collectors.toList());
    }

    private List<UUID> getIndividualVideoIds(List<VideoSpaceParticipant> videoSpaceParticipants) {
        return individualVideoQueryService.findAllWithVideoByVideoSpaceParticipant(videoSpaceParticipants).stream()
                .map(IndividualVideo::getId)
                .collect(Collectors.toList());
    }

}
