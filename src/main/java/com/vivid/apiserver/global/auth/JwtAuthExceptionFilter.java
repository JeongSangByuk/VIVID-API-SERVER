package com.vivid.apiserver.global.auth;

import com.vivid.apiserver.domain.user.exception.AccessTokenInvalidException;
import com.vivid.apiserver.domain.user.exception.AccessTokenNotFoundException;
import com.vivid.apiserver.global.error.ErrorResponse;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthExceptionFilter extends OncePerRequestFilter {

    //인증 오류가 아닌, JWT 관련 오류는 이 필터에서 따로 잡아냄.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // move to next filter
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException expiredJwtException) {

            // access token 만료 exception
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ErrorCode.ACCESS_TOKEN_EXPIRED);
        } catch (AccessTokenInvalidException accessTokenInvalidException) {

            // access token invalid exception
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ErrorCode.ACCESS_TOKEN_INVALID);
        } catch (JwtException jwtException) {

            // access token invalid exception
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ErrorCode.ACCESS_TOKEN_INVALID);
        } catch (AccessTokenNotFoundException accessTokenNotFoundException) {

            // access token not found exception
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ErrorCode.ACCESS_TOKEN_NOT_FOUND_IN_HEADER);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, ErrorCode errorCode) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(
                ErrorResponse.from(errorCode)
        ));
    }
}
