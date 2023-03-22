package com.vivid.apiserver.domain.video_space.application;

import com.vivid.apiserver.domain.individual_video.application.command.IndividualVideoCommandService;
import com.vivid.apiserver.domain.user.application.query.UserQueryService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video_space.application.command.VideoSpaceParticipantCommandService;
import com.vivid.apiserver.domain.video_space.application.query.VideoSpaceParticipantQueryService;
import com.vivid.apiserver.domain.video_space.application.query.VideoSpaceQueryService;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.dto.response.VideoSpaceParticipantSaveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class VideoSpaceParticipantService {

    private final UserQueryService userQueryService;
    private final VideoSpaceParticipantQueryService videoSpaceParticipantQueryService;
    private final VideoSpaceQueryService videoSpaceQueryService;

    private final VideoSpaceParticipantCommandService videoSpaceParticipantCommandService;
    private final IndividualVideoCommandService individualVideoCommandService;

    private final VideoSpaceValidateService videoSpaceValidateService;

    /**
     * video space에 유저 추가
     */
    public VideoSpaceParticipantSaveResponse addVideoSpaceParticipantToVideoSpace(Long videoSpaceId, String email) {

        User user = userQueryService.findByEmail(email);

        VideoSpace videoSpace = videoSpaceQueryService.findWithVideoSpaceParticipantsById(videoSpaceId);

        videoSpaceValidateService.checkHostUserAccess(videoSpace, videoSpace.getHostEmail());
        videoSpaceValidateService.checkDuplicatedParticipant(email, videoSpace.getVideoSpaceParticipants());

        VideoSpaceParticipant videoSpaceParticipant = videoSpaceParticipantCommandService.save(videoSpace, user);
        individualVideoCommandService.saveAllByVideos(videoSpace.getVideos(), videoSpaceParticipant);

        return VideoSpaceParticipantSaveResponse.of(videoSpaceParticipant);
    }

    /**
     * video space에서 유저 삭제, 개인 영상 또한 삭제
     */
    public void deleteVideoSpaceParticipantFromVideoSpace(Long videoSpaceId, String email) {

        User user = userQueryService.findByEmail(email);
        VideoSpaceParticipant videoSpaceParticipant =
                videoSpaceParticipantQueryService.findWithVideoSpaceByUserIdAndVideoSpaceId(user.getId(), videoSpaceId);
        VideoSpace videoSpace = videoSpaceParticipant.getVideoSpace();

        videoSpaceValidateService.checkHostUserAccess(videoSpace, videoSpace.getHostEmail());
        videoSpaceValidateService.checkVideoSpaceHostDelete(videoSpace, email);

        individualVideoCommandService.deleteAll(videoSpaceParticipant);
        videoSpaceParticipantCommandService.delete(videoSpaceParticipant);
    }
}
