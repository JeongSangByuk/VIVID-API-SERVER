package com.vivid.apiserver.domain.user.dao;

import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.domain.AccountBuilder;
import com.vivid.apiserver.test.RepositoryTest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest extends RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDao userDao;

    @BeforeEach
    public void setUp() {

    }

    @Test
    @DisplayName("[AccountDao] save 성공 테스트")
    public void save_성공() {


        //given
        User user = AccountBuilder.build();

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser.getName()).isEqualTo(user.getName());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUser.getPassword()).isNotNull();
        assertThat(savedUser.getCreatedDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(savedUser.getUpdatedDate()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    @DisplayName("[AccountRepository] findByEmail 성공 테스트")
    public void findByEmail_성공() {

        // given
        User user = AccountBuilder.build();
        User savedUser = userRepository.save(user);

        // when
        Optional<User> accountByEmail = userRepository.findByEmail(user.getEmail());

        //then
        assertThat(savedUser.getId()).isEqualTo(accountByEmail.get().getId());

    }

    @Test
    @DisplayName("[AccountRepository] existsByEmail_존재하는경우_true")
    public void existsByEmail_존재하는경우_true() {

        // given
        User user = AccountBuilder.build();
        userRepository.save(user);

        // when
        final boolean existsByEmail = userRepository.existsByEmail(user.getEmail());

        // then
        assertThat(existsByEmail).isTrue();
    }

    @Test
    @DisplayName("[AccountRepository] existsByEmail_존재하는경우_false")
    public void existsByEmail_존재하지않은_경우_false() {

        // given
        User user = AccountBuilder.build();
        userRepository.save(user);

        // when
        final boolean existsByEmail = userRepository.existsByEmail("thisissnotemail@lol.kr");

        // then
        assertThat(existsByEmail).isFalse();
    }





}