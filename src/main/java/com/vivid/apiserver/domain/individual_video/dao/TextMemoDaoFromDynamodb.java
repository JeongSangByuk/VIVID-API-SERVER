package com.vivid.apiserver.domain.individual_video.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

//@Service
@Transactional
@AllArgsConstructor
public class TextMemoDaoFromDynamodb implements TextMemoDao {

    private final DynamoDBMapper dynamoDBMapper;

    public Optional<TextMemo> findLatestByIndividualId(String individualVideoId) {

        return Optional.ofNullable(dynamoDBMapper.load(TextMemo.class, UUID.fromString(individualVideoId)));
    }

    public List<TextMemo> findAllByIndividualId(String individualVideoId) {

        Map<String, AttributeValue> map = new HashMap<>();
        map.put(":val", new AttributeValue().withS(individualVideoId));

        DynamoDBQueryExpression<TextMemo> queryExpression = new DynamoDBQueryExpression<TextMemo>()
                .withKeyConditionExpression("individual_video_id = :val")
                .withExpressionAttributeValues(map);

        return dynamoDBMapper.query(TextMemo.class, queryExpression);
    }

    public void save(TextMemo textMemo, String individualVideoId) {
        dynamoDBMapper.save(textMemo);
    }

    public void saveAll(List<TextMemo> textMemos, String individualVideoId) {
        dynamoDBMapper.batchSave(textMemos);
    }

}
