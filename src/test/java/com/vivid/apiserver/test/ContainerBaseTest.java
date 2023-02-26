package com.vivid.apiserver.test;

import org.junit.ClassRule;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class ContainerBaseTest extends IntegrationTest {

    /*
    * TestContainers 라이브러리를 이용해서 레디스 테스트.
    * 이 때, 로컬의 도커 데스크탑이 실행 중에 있어야 한다!
    * */

    private static final String DOCKER_REDIS_IMAGE = "redis:6-alpine";
    private static final String DOCKER_DYNAMODB_IMAGE = "amazon/dynamodb-local:latest";

    @ClassRule
    static final GenericContainer REDIS_CONTAINER;

    @ClassRule
    public static GenericContainer DYNAMODB_CONTAINER;

    static {

        REDIS_CONTAINER = new GenericContainer<>(DOCKER_REDIS_IMAGE)
                .withExposedPorts(6379)
                .withReuse(true);

        DYNAMODB_CONTAINER = new GenericContainer<>(DOCKER_DYNAMODB_IMAGE)
                        .withExposedPorts(8000);

        REDIS_CONTAINER.start();
        DYNAMODB_CONTAINER.start();
    }


    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry){

        // redis
        registry.add("spring.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.redis.port", () -> "" + REDIS_CONTAINER.getMappedPort(6379));

        // dynamo
        final String endpoint = String.format("http://%s:%s", DYNAMODB_CONTAINER.getHost(),
                DYNAMODB_CONTAINER.getMappedPort(8000));
        registry.add("cloud.aws.dynamodb.endpoint", () -> endpoint);
    }
}
