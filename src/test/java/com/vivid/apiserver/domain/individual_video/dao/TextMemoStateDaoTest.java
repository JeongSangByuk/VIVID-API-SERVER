package com.vivid.apiserver.domain.individual_video.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoState;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoStateBuilder;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoStateHistory;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoStateLatest;
import com.vivid.apiserver.domain.individual_video.dto.TextMemoStateDynamoSaveRequest;
import com.vivid.apiserver.domain.individual_video.dto.TextMemoStateRedisSaveRequest;
import com.vivid.apiserver.test.ContainerBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TextMemoStateDaoTest extends ContainerBaseTest {

    @Autowired
    private TextMemoStateDao textMemoStateDao;

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDb;

    @BeforeEach
    void setUp() {

        // test container 기반, dynamoDB table 생성

        CreateTableRequest createTextMemoStateLatestTableRequest = dynamoDBMapper.generateCreateTableRequest(TextMemoStateLatest.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));


        CreateTableRequest createTextMemoStateHistoryTableRequest = dynamoDBMapper.generateCreateTableRequest(TextMemoStateHistory.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));


        TableUtils.createTableIfNotExists(amazonDynamoDb, createTextMemoStateLatestTableRequest);
        TableUtils.createTableIfNotExists(amazonDynamoDb, createTextMemoStateHistoryTableRequest);
    }

    @Test
    @DisplayName("[TextMemoStateDaoTest] textMemoState_latestState_객체_change")
    public void textMemoState_latestState_객체_change() {

        // given
        String individualVideoId = TextMemoStateBuilder.getRandomIndividualVideoId();
        TextMemoStateDynamoSaveRequest textMemoStateDynamoSaveRequest = TextMemoStateBuilder.dynamoSaveRequestBuilder(individualVideoId);

        // when
        TextMemoState textMemoStateLatest = textMemoStateDynamoSaveRequest.toLatestEntity();

        //then
        assertThat(textMemoStateLatest.getClass()).isEqualTo(TextMemoStateLatest.class);
        assertThat(textMemoStateLatest.getStateJson()).isEqualTo(textMemoStateDynamoSaveRequest.getStateJson());
        assertThat(textMemoStateLatest.getIndividualVideoId()).isEqualTo(UUID.fromString(textMemoStateDynamoSaveRequest.getIndividualVideoId()));
    }

    @Test
    @DisplayName("[TextMemoStateDaoTest] textMemoState_레디스_save")
    public void textMemoState_레디스_save() {

        // given
        String individualVideoId = TextMemoStateBuilder.getRandomIndividualVideoId();
        TextMemoStateRedisSaveRequest redisSaveRequest = TextMemoStateBuilder.redisSaveRequestBuilder(individualVideoId);

        // when

        // save 한후,
        TextMemoState textMemoState = textMemoStateDao.saveToRedis(redisSaveRequest.toEntity());

        // individualVideoId를 통한 검색.
        TextMemoStateLatest savedTextMemoState= textMemoStateDao.getLatestFromRedis(textMemoState.getIndividualVideoId().toString());

        //then
        assertThat(savedTextMemoState.getId()).isEqualTo(textMemoState.getId());
        assertThat(savedTextMemoState.getIndividualVideoId()).isEqualTo(textMemoState.getIndividualVideoId());
        assertThat(savedTextMemoState.getStateJson()).isEqualTo(textMemoState.getStateJson());

    }

    @Test
    @DisplayName("[TextMemoStateDaoTest] textMemoStateHistory_레디스_get")
    public void textMemoStateHistory_레디스_get() {

        // given
        String individualVideoId = TextMemoStateBuilder.getRandomIndividualVideoId();
        TextMemoStateRedisSaveRequest redisSaveRequest = TextMemoStateBuilder.redisSaveRequestBuilder(individualVideoId);

        // when
        // state 두개 저장.
        TextMemoState textMemoState1 = textMemoStateDao.saveToRedis(redisSaveRequest.toEntity());
        TextMemoState textMemoState2 = textMemoStateDao.saveToRedis(redisSaveRequest.toEntity());

        // then
        List<TextMemoStateHistory> stateHistoryList = textMemoStateDao.getTextMemoStateHistoryFromRedis(individualVideoId);
        assertThat(stateHistoryList.size()).isEqualTo(2);
        assertThat(stateHistoryList.get(0).getId()).isNotNull();
        assertThat(stateHistoryList.get(1).getId()).isNotNull();
        assertThat(stateHistoryList.get(0).getIndividualVideoId()).isEqualTo(textMemoState1.getIndividualVideoId());
        assertThat(stateHistoryList.get(1).getIndividualVideoId()).isEqualTo(textMemoState2.getIndividualVideoId());

    }

    @Test
    @DisplayName("[TextMemoStateDaoTest] textMemoStateLatest_다이나모_get")
    public void textMemoStateLatest_다이나모_get() {

        // given
        String individualVideoId = TextMemoStateBuilder.getRandomIndividualVideoId();
        TextMemoStateDynamoSaveRequest textMemoStateDynamoSaveRequest = TextMemoStateBuilder.dynamoSaveRequestBuilder(individualVideoId);

        // when
        textMemoStateDao.saveLatestToDynamo(textMemoStateDynamoSaveRequest.toLatestEntity());
        TextMemoStateLatest textMemoStateLatest = textMemoStateDao.getLatestFromDynamo(textMemoStateDynamoSaveRequest.getIndividualVideoId());

        // then
        assertThat(textMemoStateLatest.getIndividualVideoId().toString()).isEqualTo(textMemoStateDynamoSaveRequest.getIndividualVideoId());
        assertThat(textMemoStateLatest.getStateJson()).isEqualTo(textMemoStateDynamoSaveRequest.getStateJson());
    }

    @Test
    @DisplayName("[TextMemoStateDaoTest] textMemoStateHistoryList_다이나모_save_and_get")
    public void textMemoStateHistoryList_다이나모_save_and_get() {

        // given
        String individualVideoId = TextMemoStateBuilder.getRandomIndividualVideoId();

        // textMemoState 두개 저장
        TextMemoStateDynamoSaveRequest textMemoStateDynamoSaveRequest1 = TextMemoStateBuilder.dynamoSaveRequestBuilder(individualVideoId);
        TextMemoStateDynamoSaveRequest textMemoStateDynamoSaveRequest2 = TextMemoStateBuilder.dynamoSaveRequestBuilder(individualVideoId);

        // input list 만들기
        TextMemoStateHistory textMemoStateHistory = textMemoStateDynamoSaveRequest1.toHistoryEntity();
        TextMemoStateHistory textMemoStateHistory2 = textMemoStateDynamoSaveRequest2.toHistoryEntity();
        List<TextMemoStateHistory> list = new ArrayList<>();
        list.add(textMemoStateHistory);
        list.add(textMemoStateHistory2);

        //when

        // 리스트 저장 후,
        textMemoStateDao.saveHistoryListToDynamo(list);
        List<TextMemoStateHistory> historyListFromDynamo = textMemoStateDao.getHistoryListFromDynamo(individualVideoId);

        // then
        assertThat(historyListFromDynamo.size()).isEqualTo(list.size());  //객체 두개 저장.
        assertThat(historyListFromDynamo.get(0).getIndividualVideoId().toString()).isEqualTo(individualVideoId);
        assertThat(historyListFromDynamo.get(1).getIndividualVideoId().toString()).isEqualTo(individualVideoId);
    }

    @Test
    @DisplayName("[TextMemoStateDaoTest] textMemoStateLatest_다이나모_hashKey_update")
    public void textMemoStateLatest_다이나모_hashKey_update() throws InterruptedException {

        // given
        String individualVideoId = TextMemoStateBuilder.getRandomIndividualVideoId();

        // update하기 위해 request 두개 생성.
        TextMemoStateDynamoSaveRequest textMemoStateDynamoSaveRequest = TextMemoStateBuilder.dynamoSaveRequestBuilder(individualVideoId);
        Thread.sleep(10);
        TextMemoStateDynamoSaveRequest updatedTextMemoStateDynamoSaveRequest = TextMemoStateBuilder.dynamoSaveRequestBuilder(individualVideoId);

        // when

        // 처음 저장에 대한 latest state get
        textMemoStateDao.saveLatestToDynamo(textMemoStateDynamoSaveRequest.toLatestEntity());
        TextMemoStateLatest textMemoStateLatest = textMemoStateDao.getLatestFromDynamo(individualVideoId);

        // updated latest state
        // 똑같은 pk에 save하면 update 된다.
        textMemoStateDao.saveLatestToDynamo(updatedTextMemoStateDynamoSaveRequest.toLatestEntity());
        TextMemoStateLatest updatedTextMemoStateLatest = textMemoStateDao.getLatestFromDynamo(individualVideoId);

        // then
        assertThat(textMemoStateLatest.getCreatedAt()).isNotEqualTo(updatedTextMemoStateLatest.getCreatedAt());
    }
}