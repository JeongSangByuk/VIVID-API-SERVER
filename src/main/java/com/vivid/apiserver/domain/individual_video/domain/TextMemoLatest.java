package com.vivid.apiserver.domain.individual_video.domain;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter     // used in com.amazonaws.services.dynamodbv2
@NoArgsConstructor()
@DynamoDBTable(tableName = "text_memo_latest")
public class TextMemoLatest extends TextMemo {

    // latest state에서는 indivualVideoId가 uuid가 된다.
    @DynamoDBHashKey(attributeName = "individual_video_id")
    private UUID individualVideoId;

    public TextMemoLatest(String id, UUID individualVideoId, String stateJson, LocalTime videoTime,
            LocalDateTime createdAt) {
        super(id, individualVideoId, stateJson, videoTime, createdAt);
        this.individualVideoId = individualVideoId;
    }
}
