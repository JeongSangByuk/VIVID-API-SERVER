package com.vivid.apiserver.domain.individual_video.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;
import com.vivid.apiserver.global.config.DynamoDbConfig;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter // used in com.amazonaws.services.dynamodbv2
@RedisHash(value = "text_memo")
@DynamoDBDocument
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class TextMemo {

    // 다이노모 디비의 id 칼럼는 상속 받은 TexMemoStateHistory, Latest 클래스에 정의,
    // 때문에 DynamoDBIgnore
    @Id
    @Indexed
    @DynamoDBIgnore
    private String id;

    // 다이노모 디비의 individualVideoId 칼럼는 상속 받은 TexMemoStateHistory, Latest 클래스에 정의,
    // 때문에 DynamoDBIgnore
    @DynamoDBIgnore
    private UUID individualVideoId;

    @DynamoDBAttribute(attributeName = "state_json")
    protected String stateJson;

    @DynamoDBAttribute(attributeName = "video_time")
    @DynamoDBTypeConverted(converter = DynamoDbConfig.LocalTimeConverter.class)
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    protected LocalTime videoTime;

    @DynamoDBAttribute(attributeName = "created_at")
    @DynamoDBTypeConverted(converter = DynamoDbConfig.LocalDateTimeConverter.class)
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    protected LocalDateTime createdAt;

    public TextMemo(String id, UUID individualVideoId, String stateJson, LocalTime videoTime,
            LocalDateTime createdAt) {
        this.id = id;
        this.individualVideoId = individualVideoId;
        this.stateJson = stateJson;
        this.videoTime = videoTime;
        this.createdAt = createdAt;
    }
}
