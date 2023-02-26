package com.vivid.apiserver.domain.individual_video.domain;


import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@SuperBuilder
@Getter
@Setter         // used in com.amazonaws.services.dynamodbv2
@NoArgsConstructor()
@DynamoDBTable(tableName = "text_memo_state_history")
public class TextMemoStateHistory extends TextMemoState{

    // state history entity는 dynamoDB에 자동생성된 uuid로 들어가게 된다.

    @DynamoDBRangeKey(attributeName="id")
    private String id;

    @DynamoDBHashKey(attributeName = "individual_video_id")
    private UUID individualVideoId;

    public TextMemoStateHistory(String id, UUID individualVideoId, String stateJson, LocalTime videoTime, LocalDateTime createdAt) {
        super(id, individualVideoId, stateJson, videoTime, createdAt);
        this.id = id;
        this.individualVideoId = individualVideoId;
    }
}
