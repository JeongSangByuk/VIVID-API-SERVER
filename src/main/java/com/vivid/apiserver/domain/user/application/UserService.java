package com.vivid.apiserver.domain.user.application;

import com.vivid.apiserver.domain.user.application.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserQueryService userQueryService;

    private final CurrentUserService currentUserService;


}
