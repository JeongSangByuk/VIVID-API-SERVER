package com.vivid.apiserver.global.auth.application;

import com.vivid.apiserver.domain.user.domain.Role;
import com.vivid.apiserver.domain.user.domain.UserAuthToken;
import com.vivid.apiserver.domain.user.exception.AccessTokenInvalidException;
import com.vivid.apiserver.domain.user.exception.AccessTokenNotFoundException;
import com.vivid.apiserver.domain.user.exception.RefreshTokenNotFoundException;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtProviderService {

    private final HttpSession httpSession;

    @Value("${jwt.password}")
    private String secretKey;

    private Key key;

    //private final long tokenPeriod = 1000L * 60L * 120L;     // 120분
    private final long tokenPeriod = 1000L * 60L * 120L;     // 10분

    private final long refreshPeriod = 1000L * 60L * 60L * 24L * 14L;      // 14일

    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.getBytes()));
    }

    public UserAuthToken generateToken(String email, String role) {

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);

        Date now = new Date();

        return new UserAuthToken(
                email,
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + tokenPeriod))
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact(),
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + refreshPeriod))
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact());
    }

    public UserAuthToken generateTokenFromRefreshToken(String refreshToken) {

        // 유효한 토큰 일 경우 re-issue access token return
        if (validateToken(refreshToken)) {

            String email = getEmail(refreshToken);
            UserAuthToken userAuthToken = generateToken(email, Role.USER.name());

            return userAuthToken;
        }

        throw new RefreshTokenNotFoundException();
    }


    public String getEmailFromHeaderAccessToken() {

        String accessToken = getAccessToken();

        String email = getEmail(accessToken);

        return email;

    }

    public String getAccessToken() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        throw new AccessTokenNotFoundException(ErrorCode.ACCESS_TOKEN_NOT_FOUND_IN_HEADER);
    }

    public String getEmail(String token) {

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException expiredJwtException) {

            return expiredJwtException.getClaims().getSubject();
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expiredJwtException) {
            throw expiredJwtException;
        } catch (JwtException jwtException) {
            throw new AccessTokenInvalidException();
        }
    }
}
