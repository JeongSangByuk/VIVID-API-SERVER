package com.vivid.apiserver.domain.auth.application.command;

import com.vivid.apiserver.domain.auth.dao.RefreshTokenDao;
import com.vivid.apiserver.global.error.exception.AccessDeniedException;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenCommandService {

    private final RefreshTokenDao refreshTokenDao;

    public void save(String email, String refreshToken) {
        refreshTokenDao.saveRefreshToken(email, refreshToken);
    }

    public String findById(String email) {
        return refreshTokenDao.getRefreshToken(email)
                .orElseThrow(() -> {
                    throw new AccessDeniedException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
                }).toString();
    }

    public void delete(String email) {
        refreshTokenDao.removeRefreshToken(email);
    }


}
