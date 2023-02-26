package com.vivid.apiserver.global.auth.application;

import com.vivid.apiserver.domain.user.application.UserAccessTokenCookieService;
import com.vivid.apiserver.domain.user.application.UserLoginService;
import com.vivid.apiserver.domain.user.application.UserService;
import com.vivid.apiserver.domain.user.domain.Role;
import com.vivid.apiserver.domain.user.domain.UserAuthToken;
import com.vivid.apiserver.domain.user.dto.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProviderService jwtProviderService;

    private final UserService userService;

    private final UserLoginService userLoginService;

    private final UserAccessTokenCookieService userAccessTokenCookieService;

    @Value("${root-url}")
    private String rootUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                .picture((String) attributes.get("picture"))
                .build();

        // 최초 로그인이라면 회원가입 처리.
        if (!userService.existsByEmail(userLoginRequest.getEmail())) {
            userService.signUp(userLoginRequest);
        }

        UserAuthToken userAuthToken = jwtProviderService.generateToken(userLoginRequest.getEmail(), Role.USER.name());

        // then, token 발급 후 -> access token, refresh token
        //writeTokenResponse(response, userAuthToken);'

        String targetUrl = UriComponentsBuilder.fromUriString(rootUrl)
                .queryParam("token", userAuthToken.getAccessToken())
                .queryParam("name", URLEncoder.encode(userLoginRequest.getName(),"UTF-8"))
                .queryParam("picture", userLoginRequest.getPicture())
                .build().toUriString();

        // redis - refresh token save
        userLoginService.saveRefreshToken(userLoginRequest.getEmail(), userAuthToken.getRefreshToken());

        // access token save in cookie
        userAccessTokenCookieService.saveAccessTokenCookie(userAuthToken.getAccessToken());

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

//    private void writeTokenResponse(HttpServletResponse response, UserAuthToken userAuthToken) throws IOException {
//
//        response.setContentType("text/html;charset=UTF-8");
//
//        response.addHeader("Auth", userAuthToken.getToken());
//        //response.addHeader("Refresh", userAuthToken.getRefreshToken());
//        response.setContentType("application/json;charset=UTF-8");
//
//        PrintWriter writer = response.getWriter();
//        writer.println(objectMapper.writeValueAsString(userAuthToken));
//        writer.flush();
//    }
}
