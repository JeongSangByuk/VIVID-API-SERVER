package com.vivid.apiserver.domain.user.application;

import com.vivid.apiserver.domain.user.exception.AccessTokenNotFoundException;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserAccessTokenCookieService {

    @Value("${cookie-domain}")
    private String cookieDomain;

    private String accessTokenSecureCookieKey = "vivid-at";
    private String accessTokenCookieKey = "access-token";

    public String getAccessTokenFromCookie() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // throw cookie null exception
        if(request.getCookies() == null)
            throw new AccessTokenNotFoundException(ErrorCode.ACCESS_TOKEN_NOT_FOUND_IN_COOKIE);

        for (Cookie c : request.getCookies()) {

            if (c.getName().equals(accessTokenCookieKey))
                return c.getValue();
        }

        // throw access token not found exception
        throw new AccessTokenNotFoundException(ErrorCode.ACCESS_TOKEN_NOT_FOUND_IN_COOKIE);
    }

    // access token을 http only cookie에 저장.
    public void saveAccessTokenCookie(String accessToken) {

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        // add cookie to response, 3h
        response.addCookie(makeAccessTokenCookie(accessToken, 3 * 60 * 60));
    }

    // cookie를 생성하는 메소드
    public Cookie makeAccessTokenCookie(String accessToken, int time) {

        // create a cookie
        Cookie cookie = new Cookie(accessTokenSecureCookieKey,accessToken);

        // expires in 30m
        cookie.setMaxAge(time);
        cookie.setDomain(cookieDomain);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }

}
