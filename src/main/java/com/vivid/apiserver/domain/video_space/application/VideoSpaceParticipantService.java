package com.vivid.apiserver.domain.video_space.application;

import com.vivid.apiserver.domain.individual_video.application.command.IndividualVideoCommandService;
import com.vivid.apiserver.domain.user.application.UserService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.exception.UserAccessDeniedException;
import com.vivid.apiserver.domain.video_space.application.command.VideoSpaceParticipantCommandService;
import com.vivid.apiserver.domain.video_space.application.query.VideoSpaceParticipantQueryService;
import com.vivid.apiserver.domain.video_space.application.query.VideoSpaceQueryService;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.dto.response.VideoSpaceParticipantSaveResponse;
import com.vivid.apiserver.domain.video_space.exception.VideoSpaceHostDeleteDeniedException;
import com.vivid.apiserver.domain.video_space.exception.VideoSpaceHostedAccessRequiredException;
import com.vivid.apiserver.domain.video_space.exception.VideoSpaceParticipantDuplicatedException;
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
    private final VideoSpaceParticipantCommandService videoSpaceParticipantCommandService;

    private final IndividualVideoCommandService individualVideoCommandService;

    private final UserService userService;

    private final VideoSpaceQueryService videoSpaceQueryService;

    // video space에 유저 추가
    public VideoSpaceParticipantSaveResponse addParticipantToVideoSpace(Long videoSpaceId, String email) {

        User user = userService.findByEmail(email);

        // todo 이부분 fetch join
        VideoSpace videoSpace = videoSpaceQueryService.findById(videoSpaceId);

        checkHostUserAccess(videoSpace.getHostEmail());
        checkDuplicatedUser(email, videoSpace);

        VideoSpaceParticipant videoSpaceParticipant = videoSpaceParticipantCommandService.save(videoSpace, user);

        individualVideoCommandService.saveAll(videoSpace.getVideos(), videoSpaceParticipant);

        return VideoSpaceParticipantSaveResponse.of(videoSpaceParticipant);
    }

    // video space에서 유저 삭제
    public void deleteVideoSpaceParticipant(Long videoSpaceId, String targetEmail) {

        VideoSpace videoSpace = videoSpaceQueryService.findById(videoSpaceId);

        // user get
        User user = userService.findByEmail(targetEmail);

        // video space hosted check
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

    // host 권한이 필요한 접근일 때 유효한 접근인지 판단.
    private void checkHostUserAccess(String hostEmail) {

        try {
            userService.checkValidUserAccess(hostEmail);
        } catch (UserAccessDeniedException userAccessDeniedException) {
            throw new VideoSpaceHostedAccessRequiredException();
        }
    }

    private void checkDuplicatedUser(String targetEmail, VideoSpace videoSpace) {
        videoSpace.getVideoSpaceParticipants().forEach(videoSpaceParticipant -> {
            if (videoSpaceParticipant.getUser().getEmail().equals(targetEmail)) {
                throw new VideoSpaceParticipantDuplicatedException();
            }
        });
    }


}
