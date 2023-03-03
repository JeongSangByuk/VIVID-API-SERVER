package com.vivid.apiserver.domain.individual_video.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoHistory;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoLatest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class TextMemoDao {

    private final DynamoDBMapper dynamoDBMapper;

    public Optional<TextMemoLatest> getLatest(String individualVideoId) {

        return Optional.ofNullable(dynamoDBMapper.load(TextMemoLatest.class, UUID.fromString(individualVideoId)));
    }

    public List<TextMemoHistory> getHistories(String individualVideoId) {

        Map<String, AttributeValue> map = new HashMap<>();
        map.put(":val", new AttributeValue().withS(individualVideoId));

        DynamoDBQueryExpression<TextMemoHistory> queryExpression = new DynamoDBQueryExpression<TextMemoHistory>()
                .withKeyConditionExpression("individual_video_id = :val")
                .withExpressionAttributeValues(map);

        return dynamoDBMapper.query(TextMemoHistory.class, queryExpression);
    }

    public void saveLatest(TextMemoLatest textMemoStateLatest) {
        dynamoDBMapper.save(textMemoStateLatest);
    }

    public void saveHistories(List<TextMemoHistory> textMemoStateHistories) {
        dynamoDBMapper.batchSave(textMemoStateHistories);
    }

}
