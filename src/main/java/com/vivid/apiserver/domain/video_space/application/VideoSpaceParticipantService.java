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

    private final VideoSpaceParticipantQueryService videoSpaceParticipantQueryService;
    private final UserQueryService userQueryService;
    private final VideoSpaceQueryService videoSpaceQueryService;

    private final VideoSpaceParticipantCommandService videoSpaceParticipantCommandService;
    private final IndividualVideoCommandService individualVideoCommandService;

    private final VideoSpaceValidationService videoSpaceValidationService;


    // video space에 유저 추가
    public VideoSpaceParticipantSaveResponse addVideoSpaceParticipantToVideoSpace(Long videoSpaceId, String email) {

        User user = userQueryService.findByEmail(email);

        // todo 이부분 fetch join
        VideoSpace videoSpace = videoSpaceQueryService.findById(videoSpaceId);

        videoSpaceValidationService.checkHostUserAccess(videoSpace.getHostEmail());
        videoSpaceValidationService.checkDuplicatedParticipant(email, videoSpace.getVideoSpaceParticipants());

        VideoSpaceParticipant videoSpaceParticipant = videoSpaceParticipantCommandService.save(videoSpace, user);

        individualVideoCommandService.saveAll(videoSpace.getVideos(), videoSpaceParticipant);

        return VideoSpaceParticipantSaveResponse.of(videoSpaceParticipant);
    }

    // video space에서 유저 삭제
    public void deleteVideoSpaceParticipantFromVideoSpace(Long videoSpaceId, String targetEmail) {

        VideoSpace videoSpace = videoSpaceQueryService.findById(videoSpaceId);
        User user = userQueryService.findByEmail(targetEmail);

        checkHostUserAccess(videoSpace.getHostEmail());

        // host 삭제 불가
        if (targetEmail.equals(videoSpace.getHostEmail())) {
            throw new VideoSpaceHostDeleteDeniedException();
        }

        // find video space participant
        VideoSpaceParticipant videoSpaceParticipant = videoSpaceParticipantQueryService.findByUserAndVideoSpace(user,
                videoSpace);

        // 연관 관계 매팡 제거
        videoSpaceParticipant.delete();

        // delete
        videoSpaceParticipantRepository.deleteById(videoSpaceParticipant.getId());

    }

}
