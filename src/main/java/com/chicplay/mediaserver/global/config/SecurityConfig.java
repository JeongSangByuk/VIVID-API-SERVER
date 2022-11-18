package com.chicplay.mediaserver.global.config;

import com.chicplay.mediaserver.domain.user.domain.Role;
import com.chicplay.mediaserver.global.auth.JwtAuthExceptionFilter;
import com.chicplay.mediaserver.global.auth.JwtAuthFilter;
import com.chicplay.mediaserver.global.auth.application.JwtProviderService;
import com.chicplay.mediaserver.global.auth.application.OAuthSuccessHandler;
import com.chicplay.mediaserver.global.auth.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService auth2UserService;

    private final OAuthSuccessHandler oAuthSuccessHandler;

    private final JwtProviderService jwtProviderService;


    @Bean
    public WebSecurityCustomizer configure() {

        // filter 안타게끔
        return (web) -> web.ignoring().mvcMatchers(
                "/api/auth/token/**", "/swagger-ui/**","/api/test/**"
                , "/v3/api-docs/**","/login/oauth2/code", "/api/login/webex",
                "/api/videos/{video_id}/uploaded"
        );
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(List.of("https://edu-vivid.com","https://dev.edu-vivid.com","http://localhost:8081"));
        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        cors.setAllowedHeaders(List.of("*"));
        cors.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // cors 관련 설정
        http.cors().configurationSource(configurationSource());

        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable() // 로그인 폼 미사용
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint()) // 인증,인가가 되지 않은 요청 시 발생시
                .and()
                .authorizeRequests()
                .antMatchers("/"
                        , "/css/**"
                        , "/images/**"
                        , "/js/**"
                        , "/h2-console/**"
                ).permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // 추가
                .antMatchers("/api/login/**", "/api/auth/**").permitAll() // Security 허용 url
                .antMatchers("/api/**").hasRole(Role.USER.name())   // 모든 api 요청에 대해 user 권한
                .anyRequest().authenticated()   // 나머지 요청에 대해서 권한이 있어야함
                .and()
                .oauth2Login()
                .successHandler(oAuthSuccessHandler)
                .userInfoEndpoint().userService(auth2UserService)
                .and()
                .authorizationEndpoint().baseUri("/login"); // 소셜 로그인 url

        http
                .addFilterBefore(new JwtAuthFilter(jwtProviderService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthExceptionFilter(), JwtAuthFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
