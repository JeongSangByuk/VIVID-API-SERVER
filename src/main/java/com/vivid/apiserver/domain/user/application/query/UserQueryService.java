package com.vivid.apiserver.domain.user.application.query;

import com.vivid.apiserver.domain.user.dao.UserRepository;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.global.error.exception.AccessDeniedException;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.NotFoundException;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserQueryService {


    private final UserRepository userRepository;

    public User findById(UUID id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public User findByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public void checkDuplicatedUserByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AccessDeniedException(ErrorCode.EMAIL_DUPLICATION);
        }
    }

    public boolean isDuplicatedUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
