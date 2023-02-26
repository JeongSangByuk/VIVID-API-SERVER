package com.vivid.apiserver.global.auth;

import com.vivid.apiserver.domain.user.domain.UserSessionDetails;
import com.vivid.apiserver.global.auth.application.JwtProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProviderService jwtProviderService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtProviderService.getAccessToken();

        // Validation Access Token
        if (jwtProviderService.validateToken(accessToken)) {

            String email = jwtProviderService.getEmail(accessToken);

            UserSessionDetails userSessionDetails = UserSessionDetails.builder().email(email).build();

            Authentication authentication = getAuthentication(userSessionDetails, request);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 필터 실행
        filterChain.doFilter(request, response);
    }

    public Authentication getAuthentication(UserSessionDetails user, HttpServletRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null,
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authenticationToken;

    }


}
