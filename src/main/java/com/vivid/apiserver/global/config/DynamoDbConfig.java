package com.vivid.apiserver.global.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.vivid.apiserver.global.util.BaseDateTimeFormatter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDbConfig {


    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${cloud.aws.region.static}")
    private String region;
    @Value("${cloud.aws.dynamodb.endpoint}")
    private String dynamoDbEndpoint;


    @Bean
    public DynamoDBMapper dynamoDBMapper() {

        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.CLOBBER)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .withTableNameOverride(null)
                .withPaginationLoadingStrategy(DynamoDBMapperConfig.PaginationLoadingStrategy.EAGER_LOADING)
                .build();

        return new DynamoDBMapper(amazonDynamoDb(), mapperConfig);
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDb() {
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(dynamoDbEndpoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    public static class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {

        @Override
        public String convert(LocalDateTime object) {
            return BaseDateTimeFormatter.getLocalDateTimeFormatter().format(object);
        }

        @Override
        public LocalDateTime unconvert(String object) {
            return LocalDateTime.parse(object, BaseDateTimeFormatter.getLocalDateTimeFormatter());
        }
    }

    public static class LocalTimeConverter implements DynamoDBTypeConverter<String, LocalTime> {

        @Override
        public String convert(LocalTime object) {
            return BaseDateTimeFormatter.getLocalTimeFormatter().format(object);
        }

        @Override
        public LocalTime unconvert(String object) {
            return LocalTime.parse(object, BaseDateTimeFormatter.getLocalTimeFormatter());
        }
    }
}
