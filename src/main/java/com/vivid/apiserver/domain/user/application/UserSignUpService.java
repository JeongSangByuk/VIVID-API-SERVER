package com.vivid.apiserver.domain.user.application;

import com.vivid.apiserver.domain.user.application.command.UserCommandService;
import com.vivid.apiserver.domain.user.application.query.UserQueryService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.dto.request.UserLoginRequest;
import com.vivid.apiserver.domain.user.dto.response.UserSignUpResponse;
import com.vivid.apiserver.domain.video_space.application.VideoSpaceManageService;
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

    private final VideoSpaceManageService videoSpaceManageService;

    /*
     * user signup 메소드
     */
    public UserSignUpResponse signUp(final UserLoginRequest userLoginRequest) {

        userQueryService.checkDuplicatedUserByEmail(userLoginRequest.getEmail());
        User user = userLoginRequest.toEntity();

        videoSpaceManageService.createInitialVideoSpace(user, VideoSpace.from(user));
        userCommandService.save(user);

        return UserSignUpResponse.from(user);
    }
}
