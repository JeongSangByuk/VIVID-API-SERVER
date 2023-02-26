package com.vivid.apiserver.domain.video_space.application;

import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.user.application.UserService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.exception.UserAccessDeniedException;
import com.vivid.apiserver.domain.video_space.dao.VideoSpaceParticipantRepository;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.dto.VideoSpaceParticipantSaveResponse;
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

    private final VideoSpaceParticipantRepository videoSpaceParticipantRepository;

    private final VideoSpaceParticipantFindService videoSpaceParticipantFindService;

    private final UserService userService;

    private final VideoSpaceFindService videoSpaceFindService;

    // 이미 생성돼 있는 videoSpace에 유저 추가 : VideoSpaceParticipant save
    public VideoSpaceParticipantSaveResponse save(Long videoSpaceId, String targetEmail) {

        // account get by email
        User targetUser = userService.findByEmail(targetEmail);

        // video space get by videoId
        VideoSpace videoSpace = videoSpaceFindService.findById(videoSpaceId);

        // video space hosted check
        checkHostUserAccess(videoSpace.getHostEmail());

        // exception by duplicated user
        videoSpace.getVideoSpaceParticipants().forEach(videoSpaceParticipant -> {
            if (videoSpaceParticipant.getUser().getEmail().equals(targetEmail))
                throw new VideoSpaceParticipantDuplicatedException();
        });

        // Video participant save
        VideoSpaceParticipant videoSpaceParticipant = VideoSpaceParticipant.builder().videoSpace(videoSpace).user(targetUser).build();
        VideoSpaceParticipant savedVideoSpaceParticipant = videoSpaceParticipantRepository.save(videoSpaceParticipant);

        // list에 individulaVideo 객체를 각각 생성해서 add
        videoSpace.getVideos().forEach(video -> {
            savedVideoSpaceParticipant.getIndividualVideos().add(IndividualVideo.builder()
                            .video(video)
                            .videoSpaceParticipant(savedVideoSpaceParticipant)
                            .build());
        });

        VideoSpaceParticipantSaveResponse videoSpaceParticipantSaveResponse = VideoSpaceParticipantSaveResponse.builder().videoSpaceParticipant(savedVideoSpaceParticipant).build();

        return videoSpaceParticipantSaveResponse;
    }

    // delete vide space participant
    public void deleteVideoSpaceParticipant(Long videoSpaceId, String targetEmail) {

        // find video space by id
        VideoSpace videoSpace = videoSpaceFindService.findById(videoSpaceId);

        // user get
        User user = userService.findByEmail(targetEmail);

        // video space hosted check
        checkHostUserAccess(videoSpace.getHostEmail());

        // host 삭제 불가
        if (targetEmail.equals(videoSpace.getHostEmail()))
            throw new VideoSpaceHostDeleteDeniedException();

        // find video space participant
        VideoSpaceParticipant videoSpaceParticipant = videoSpaceParticipantFindService.findByUserAndVideoSpace(user, videoSpace);

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


}
