package com.vivid.apiserver.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivid.apiserver.global.auth.application.TokenProvider;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test2")
@Transactional
public class IntegrationTest {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected TokenProvider tokenProvider;

    public final String NOW_USER_EMAIL = "jsb100800@gmail.com";

    public String header;

    protected String createAuthHeader() {

        String header = tokenProvider.generateToken(NOW_USER_EMAIL, "USER", true).getToken();
        return "Bearer " + header;
    }

    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }
}
