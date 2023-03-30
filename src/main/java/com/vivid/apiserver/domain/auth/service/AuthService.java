package com.vivid.apiserver.domain.auth.service;

import com.vivid.apiserver.domain.auth.dto.response.AccessTokenResponse;
import com.vivid.apiserver.domain.auth.service.command.RefreshTokenCommandService;
import com.vivid.apiserver.domain.user.domain.Role;
import com.vivid.apiserver.global.auth.application.TokenProvider;
import com.vivid.apiserver.global.auth.token.AuthToken;
import com.vivid.apiserver.global.auth.util.CookieUtil;
import com.vivid.apiserver.global.auth.util.TokenUtil;
import com.vivid.apiserver.global.error.exception.AccessDeniedException;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;

    private final RefreshTokenCommandService refreshTokenCommandService;

    /**
     * access token 만료된 경우, access token 재발급
     */
    public AccessTokenResponse getAccessToken(HttpServletRequest request, HttpServletResponse response) {

        // 쿠키에 access token을 get, 이 때 쿠키에 없으면 throw exception
        String accessTokenValue = getAccessTokenFromCookie(request);

        AuthToken accessToken = tokenProvider.convertAuthToken(accessTokenValue);
        String email = accessToken.getEmail();

        /**
         * cookie에 있던 access token을 validate 한 후, 이상없으면 token을 내려준다.
         * access token이 존재하지만, 만료된 경우에는 reissue한다.
         */
        try {
            accessToken.validateToken();
            return new AccessTokenResponse(accessTokenValue);
        } catch (ExpiredJwtException expiredJwtException) {
            return new AccessTokenResponse(reIssueAccessTokenFromRefreshToken(response, email));
        }
    }

    /**
     * 쿠키에서 AccessToken Get 메소드
     */
    private String getAccessTokenFromCookie(HttpServletRequest request) {
        return CookieUtil.getAccessTokenCookie(request).orElseThrow(() -> {
            throw new AccessDeniedException(ErrorCode.LOGIN_INFO_NOT_FOUND);
        }).getValue();
    }

    /**
     * refresh token으로부터 access token을 재발급 받는 메소드
     */
    public String reIssueAccessTokenFromRefreshToken(HttpServletResponse response, String email) {

        String refreshTokenValue = refreshTokenCommandService.findById(email);
        AuthToken refreshToken = tokenProvider.convertAuthToken(refreshTokenValue);
        checkRefreshTokenValidation(refreshToken, refreshTokenValue, email);

        AuthToken accessToken = tokenProvider.generateToken(email, Role.USER.name(), true);
        CookieUtil.addAccessTokenCookie(response, accessToken.getToken());

        return accessToken.getToken();
    }

    /**
     * refresh token validation 메소드
     */
    private void checkRefreshTokenValidation(AuthToken refreshToken, String refreshTokenValue, String email) {
        try {
            tokenProvider.convertAuthToken(refreshTokenValue).validateToken();

            if (!email.equals(refreshToken.getEmail())) {
                throw new AccessDeniedException(ErrorCode.ACCESS_TOKEN_INVALID);
            }

        } catch (ExpiredJwtException expiredJwtException) {
            throw new AccessDeniedException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
    }

    /*
     * 로그아웃 처리 메소드
     */
    public void removeTokensByLogout(HttpServletRequest request, HttpServletResponse response) {

        String email = TokenUtil.getCurrentUserEmail();

        refreshTokenCommandService.delete(email);
        CookieUtil.deleteAccessTokenCookie(request, response);
    }

}


