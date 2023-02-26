package com.vivid.apiserver.domain.individual_video.domain;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@SuperBuilder
@Getter
@Setter     // used in com.amazonaws.services.dynamodbv2
@NoArgsConstructor()
@DynamoDBTable(tableName = "text_memo_state_latest")
public class TextMemoStateLatest extends TextMemoState{


    // latest state에서는 indivualVideoId가 uuid가 된다.
    @DynamoDBHashKey(attributeName = "individual_video_id")
    private UUID individualVideoId;

    public TextMemoStateLatest(String id, UUID individualVideoId, String stateJson, LocalTime videoTime, LocalDateTime createdAt) {
        super(id, individualVideoId,  stateJson, videoTime, createdAt);
        this.individualVideoId = individualVideoId;
    }
}
