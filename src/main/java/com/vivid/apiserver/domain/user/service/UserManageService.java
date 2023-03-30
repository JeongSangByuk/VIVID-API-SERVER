package com.vivid.apiserver.domain.user.service;

import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.dto.request.UserLoginRequest;
import com.vivid.apiserver.domain.user.dto.response.UserSignUpResponse;
import com.vivid.apiserver.domain.user.service.command.UserCommandService;
import com.vivid.apiserver.domain.user.service.query.UserQueryService;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.service.VideoSpaceManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserManageService {

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
