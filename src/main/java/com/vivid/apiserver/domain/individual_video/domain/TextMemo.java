package com.vivid.apiserver.domain.individual_video.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.vivid.apiserver.global.config.DynamoDbConfig;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter // used in com.amazonaws.services.dynamodbv2
@Builder
@RedisHash(value = "text_memo")
@DynamoDBDocument
@NoArgsConstructor
@AllArgsConstructor
public class TextMemo {

    @Id
    @Indexed
    @DynamoDBRangeKey(attributeName = "id")
    private String id;

    @DynamoDBAttribute(attributeName = "state_json")
    private String stateJson;

    @DynamoDBAttribute(attributeName = "video_time")
    private Long videoTime;

    @DynamoDBAttribute(attributeName = "created_at")
    @DynamoDBTypeConverted(converter = DynamoDbConfig.LocalDateTimeConverter.class)
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    private List<TextMemo> textMemos;
}
