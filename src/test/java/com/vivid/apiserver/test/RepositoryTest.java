package com.vivid.apiserver.test;

import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@Disabled
@DataJpaTest
@ActiveProfiles("test")
public class RepositoryTest {
}
