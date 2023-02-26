package com.vivid.apiserver.domain.user.dao;

import com.vivid.apiserver.domain.video.dao.VideoRepository;
import com.vivid.apiserver.test.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest extends IntegrationTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Test
    @DisplayName("[AccountDaoTest] account_find_by_id")
    public void account_find_by_id() {


    }


}