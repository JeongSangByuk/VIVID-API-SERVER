package com.vivid.apiserver.test;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.ClassRule;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class ContainerBaseTest extends IntegrationTest {

    private static final String DOCKER_REDIS_IMAGE = "redis:6-alpine";

    private static final String DOCKER_MONGO_IMAGE = "mongo:5";

    @ClassRule
    static final GenericContainer REDIS_CONTAINER;

    @ClassRule
    static final GenericContainer MONGO_CONTAINER;

    static {

        REDIS_CONTAINER = new GenericContainer<>(DOCKER_REDIS_IMAGE)
                .withExposedPorts(6379)
                .withReuse(true);

        MONGO_CONTAINER = new GenericContainer<>(DOCKER_MONGO_IMAGE)
                .withExposedPorts(27017)
                .withReuse(true);

        MONGO_CONTAINER.setEnv(createMongEvnList());

        REDIS_CONTAINER.start();
        MONGO_CONTAINER.start();
    }

    @NotNull
    private static List<String> createMongEvnList() {
        List<String> mongoEnvList = new ArrayList<>();
        mongoEnvList.add("MONGO_INITDB_ROOT_USERNAME=test");
        mongoEnvList.add("MONGO_INITDB_ROOT_PASSWORD=test");
        mongoEnvList.add("MONGO_INITDB_DATABASE=inventory");
        return mongoEnvList;
    }


    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {

        registry.add("spring.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.redis.port", () -> "" + REDIS_CONTAINER.getMappedPort(6379));

        registry.add("spring.data.mongodb.host", MONGO_CONTAINER::getHost);
        registry.add("spring.data.mongodb.port", () -> "" + MONGO_CONTAINER.getMappedPort(27017));
    }
}
