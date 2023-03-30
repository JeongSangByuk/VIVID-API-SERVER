package com.vivid.apiserver.domain.user.service;

import com.vivid.apiserver.domain.auth.service.command.RefreshTokenCommandService;
import com.vivid.apiserver.domain.individual_video.service.command.IndividualVideoCommandService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.service.command.UserCommandService;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.service.command.VideoSpaceParticipantCommandService;
import com.vivid.apiserver.global.auth.util.CookieUtil;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final VideoSpaceParticipantCommandService videoSpaceParticipantCommandService;
    private final IndividualVideoCommandService individualVideoCommandService;
    private final RefreshTokenCommandService refreshTokenCommandService;
    private final CurrentUserService currentUserService;

    private final UserCommandService userCommandService;

    public void delete(HttpServletRequest request, HttpServletResponse response) {

        User currentUser = currentUserService.getCurrentUser();
        List<VideoSpaceParticipant> videoSpaceParticipants = currentUser.getVideoSpaceParticipants();

        refreshTokenCommandService.delete(currentUser.getEmail());
        CookieUtil.deleteAccessTokenCookie(request, response);

        individualVideoCommandService.deleteAll(Collections.emptyList(), videoSpaceParticipants);
        videoSpaceParticipantCommandService.deleteAll(videoSpaceParticipants);
        userCommandService.delete(currentUser);
    }
}
