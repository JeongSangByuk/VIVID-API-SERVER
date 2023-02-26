package com.vivid.apiserver.domain.user.application;

import com.vivid.apiserver.domain.user.dao.UserRepository;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.domain.AccountBuilder;
import com.vivid.apiserver.domain.user.dto.UserLoginRequest;
import com.vivid.apiserver.domain.user.exception.EmailDuplicateException;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.test.ServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class UserServiceTest extends ServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("[AccountService] signup 성공 테스트")
    public void signup_성공() throws Exception {

        //given
        UserLoginRequest userLoginRequest = new UserLoginRequest(AccountBuilder.USER_EMAIL, AccountBuilder.USER_NAME);

        given(userRepository.save(any())).willReturn(userLoginRequest.toEntity());

        //when
        User user = userRepository.save(userLoginRequest.toEntity());

        //then
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(AccountBuilder.USER_EMAIL);
        assertThat(user.getName()).isEqualTo(AccountBuilder.USER_NAME);
        assertThat(user.getPassword()).isNotEqualTo(AccountBuilder.USER_PASSWORD);
    }

    @Test
    @DisplayName("[AccountService] signup 이메일 중복")
    public void signUp_이메일_중복() {

        //given
        UserLoginRequest userLoginRequest = new UserLoginRequest(AccountBuilder.USER_EMAIL, AccountBuilder.USER_NAME);
        given(userRepository.existsByEmail(any())).willReturn(true);

        //when
        EmailDuplicateException exception = Assertions.assertThrows(EmailDuplicateException.class, () ->{
            userService.signUp(userLoginRequest);
        });

        //then
        assertEquals(ErrorCode.EMAIL_DUPLICATION,exception.getErrorCode());
    }
}