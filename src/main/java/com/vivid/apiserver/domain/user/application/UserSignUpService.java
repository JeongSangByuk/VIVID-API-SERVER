package com.vivid.apiserver.domain.user.application;

import com.vivid.apiserver.domain.user.application.command.UserCommandService;
import com.vivid.apiserver.domain.user.application.query.UserQueryService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.dto.UserLoginRequest;
import com.vivid.apiserver.domain.user.dto.UserSignUpResponse;
import com.vivid.apiserver.domain.video_space.application.command.VideoSpaceCommandService;
import com.vivid.apiserver.domain.video_space.application.command.VideoSpaceParticipantCommandService;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserSignUpService {

    private final UserQueryService userQueryService;

    private final UserCommandService userCommandService;
    private final VideoSpaceParticipantCommandService videoSpaceParticipantCommandService;
    private final VideoSpaceCommandService videoSpaceCommandService;

    /*
     * user signup 메소드
     */
    public UserSignUpResponse signUp(final UserLoginRequest userLoginRequest) {

        userQueryService.checkDuplicatedUserByEmail(userLoginRequest.getEmail());
        User user = userLoginRequest.toEntity();
        
        createIndividualVideoSpace(user);
        userCommandService.save(user);

        return UserSignUpResponse.from(user);
    }

    /**
     * 개인 video space 생성
     */
    private void createIndividualVideoSpace(User user) {
        VideoSpace videoSpace = videoSpaceCommandService.save(user);
        videoSpaceParticipantCommandService.save(videoSpace, user);
    }
}
