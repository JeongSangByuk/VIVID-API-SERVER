package com.vivid.apiserver.domain.user.application;

import com.vivid.apiserver.domain.user.dao.UserAuthTokenDao;
import com.vivid.apiserver.domain.user.domain.Role;
import com.vivid.apiserver.domain.user.domain.UserAuthToken;
import com.vivid.apiserver.domain.user.dto.UserLoginRequest;
import com.vivid.apiserver.domain.user.dto.UserNewTokenDto;
import com.vivid.apiserver.domain.user.exception.AccessTokenInvalidException;
import com.vivid.apiserver.domain.user.exception.RefreshTokenExpiredException;
import com.vivid.apiserver.global.auth.application.JwtProviderService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserLoginService {

    private final UserService userService;

    private final UserAuthTokenDao userAuthTokenDao;

    private final JwtProviderService jwtProviderService;

    private final UserAccessTokenCookieService userAccessTokenCookieService;

    // access token re-issue, access token 만료된 경우 호출.
    public UserNewTokenDto reIssueAccessToken() {

        // 쿠키에 access token을 get, 이 때 쿠키에 값 없으면 throw exception
        //String accessToken = userAccessTokenCookieService.getAccessTokenFromCookie();
        String accessToken = jwtProviderService.getAccessToken();

        String email = jwtProviderService.getEmail(accessToken);

        try {
            // access token validation 판별.
            jwtProviderService.validateToken(accessToken);

            // 쿠키의 access token이 유효할 경우, 그대로 return
            return UserNewTokenDto.builder().accessToken(accessToken).build();
        } catch (ExpiredJwtException expiredJwtException) {
            //만료됐을 경우, refresh token 확인.
            return reIssueAccessTokenFromRefreshToken(email, accessToken);
        }
    }

    // refresh token으로부터 access token을 재발급 받는 메소드입니다.
    public UserNewTokenDto reIssueAccessTokenFromRefreshToken(String email, String accessToken) {

        // refresh token get, 쿠키가 있어도 ip를 통해 2차 판별.
        String refreshToken = userAuthTokenDao.getRefreshToken(email, userService.getUserIp());

        // refresh token validation
        try {
            jwtProviderService.validateToken(refreshToken);
        } catch (ExpiredJwtException expiredJwtException){
            throw new RefreshTokenExpiredException();
        };

        // token의 email이 같은지 여부 확인
        if (!jwtProviderService.getEmail(accessToken).equals(jwtProviderService.getEmail(refreshToken)))
            throw new AccessTokenInvalidException();

        // refresh token을 통해 새로운 토큰 generate
        UserAuthToken reIssuedToken = jwtProviderService.generateTokenFromRefreshToken(refreshToken);

        // refresh token save in redis
        saveRefreshToken(email, reIssuedToken.getRefreshToken());

        // access token save in cookie
        userAccessTokenCookieService.saveAccessTokenCookie(reIssuedToken.getAccessToken());

        return UserNewTokenDto.builder().accessToken(reIssuedToken.getAccessToken()).build();
    }


    // refresh token redis에 저장.
    public void saveRefreshToken(String email, String refreshToken) {

        userAuthTokenDao.saveRefreshToken(email, userService.getUserIp(), refreshToken);
    }

    // logout으로 인한 모든 token 삭제.
    public void removeTokensByLogout() {

        // email get
        String email = jwtProviderService.getEmailFromHeaderAccessToken();

        // remove refresh token
        userAuthTokenDao.removeRefreshToken(email);

        // remove access token in http only cookie
        userAccessTokenCookieService.makeAccessTokenCookie(null, 1);

    }


    // test login용
    public List<UserNewTokenDto> loginByTestUser() {

        List<String> testEmail = new ArrayList<>();
        testEmail.add("test01@gmail.com");
        testEmail.add("test02@gmail.com");
        testEmail.add("test03@gmail.com");

        List<UserNewTokenDto> userNewTokenDtoList = new ArrayList<>();

        testEmail.forEach(email -> {

            UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                    .email(email)
                    .name("테스트")
                    .picture("https://lh3.googleusercontent.com/a/ALm5wu30vxzpsZnUhBIzgRdhl7FeR-dEt0WTCQxaLNUC=s96-c")
                    .build();

            // token 생성
            UserAuthToken userAuthToken = jwtProviderService.generateToken(userLoginRequest.getEmail(), Role.USER.name());

            userNewTokenDtoList.add(UserNewTokenDto.builder().accessToken(userAuthToken.getAccessToken()).build());
        });

        return userNewTokenDtoList;
    }


//    // 유저 생성 및 수정 서비스 로직
//    private User saveOrUpdate(OAuthAttributes attributes){
//        User user = userRepository.findByEmail(attributes.getEmail())
//                .orElse(attributes.toEntity());
//
//        return userRepository.save(user);
//    }

    // access token re-isuue from silent refresh
//    public UserNewTokenDto getNewAccessTokenFromSilentRefresh() {
//
//        String accessToken = jwtProviderService.getAccessToken();
//
//        // silent refresh 시, access token 검사.
//        if (!jwtProviderService.validateToken(accessToken)) {
//            throw new JwtException("Access Token Invalid");
//        }
//
//        // refresh token을 이용해서 access token re-issue
//        UserAuthToken tokenFromRedisSession = jwtProviderService.generateTokenFromRefreshToken(userService.getUserIp());
//
//        return UserNewTokenDto.builder().accessToken(tokenFromRedisSession.getAccessToken()).build();
//    }

}


