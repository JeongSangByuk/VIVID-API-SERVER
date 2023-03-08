package com.vivid.apiserver.domain.individual_video.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoHistory;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoLatest;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoStateBuilder;
import com.vivid.apiserver.domain.individual_video.dto.request.TextMemoCacheSaveRequest;
import com.vivid.apiserver.domain.individual_video.dto.request.TextMemoSaveRequest;
import com.vivid.apiserver.test.ContainerBaseTest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TextMemoCacheDaoTest extends ContainerBaseTest {

    @Autowired
    private TextMemoCacheDao textMemoCacheDao;

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDb;

    @BeforeEach
    void setUp() {

        // test container 기반, dynamoDB table 생성

        CreateTableRequest createTextMemoStateLatestTableRequest = dynamoDBMapper.generateCreateTableRequest(
                        TextMemoLatest.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        CreateTableRequest createTextMemoStateHistoryTableRequest = dynamoDBMapper.generateCreateTableRequest(
                        TextMemoHistory.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        TableUtils.createTableIfNotExists(amazonDynamoDb, createTextMemoStateLatestTableRequest);
        TableUtils.createTableIfNotExists(amazonDynamoDb, createTextMemoStateHistoryTableRequest);
    }

    @Test
    @DisplayName("[TextMemoStateDaoTest] textMemoState_latestState_객체_change")
    public void textMemoState_latestState_객체_change() {

        // given
        String individualVideoId = TextMemoStateBuilder.getRandomIndividualVideoId();
        TextMemoSaveRequest textMemoSaveRequest = TextMemoStateBuilder.dynamoSaveRequestBuilder(
                individualVideoId);

        // when
        TextMemo textMemoLatest = textMemoSaveRequest.toLatestEntity();

        //then
        assertThat(textMemoLatest.getClass()).isEqualTo(TextMemoLatest.class);
        assertThat(textMemoLatest.getStateJson()).isEqualTo(textMemoSaveRequest.getStateJson());
        assertThat(textMemoLatest.getIndividualVideoId()).isEqualTo(
                UUID.fromString(textMemoSaveRequest.getIndividualVideoId()));
    }

    @Test
    @DisplayName("[TextMemoStateDaoTest] textMemoState_레디스_save")
    public void textMemoState_레디스_save() {

        // given
        String individualVideoId = TextMemoStateBuilder.getRandomIndividualVideoId();
        TextMemoCacheSaveRequest redisSaveRequest = TextMemoStateBuilder.redisSaveRequestBuilder(
                individualVideoId);

        // when

        // save 한후,
        TextMemo textMemo = textMemoCacheDao.save(redisSaveRequest.toEntity());

        // individualVideoId를 통한 검색.
        TextMemoLatest savedTextMemoState = textMemoCacheDao.findLatestByIndividualVideoId(
                textMemo.getIndividualVideoId().toString());

        //then
        assertThat(savedTextMemoState.getId()).isEqualTo(textMemo.getId());
        assertThat(savedTextMemoState.getIndividualVideoId()).isEqualTo(textMemo.getIndividualVideoId());
        assertThat(savedTextMemoState.getStateJson()).isEqualTo(textMemo.getStateJson());

    }

    @Test
    @DisplayName("[TextMemoStateDaoTest] textMemoStateHistory_레디스_get")
    public void textMemoStateHistory_레디스_get() {

        // given
        String individualVideoId = TextMemoStateBuilder.getRandomIndividualVideoId();
        TextMemoCacheSaveRequest redisSaveRequest = TextMemoStateBuilder.redisSaveRequestBuilder(
                individualVideoId);

        // when
        // state 두개 저장.
        TextMemo textMemo1 = textMemoCacheDao.save(redisSaveRequest.toEntity());
        TextMemo textMemo2 = textMemoCacheDao.save(redisSaveRequest.toEntity());

        // then
        List<TextMemoHistory> stateHistoryList = textMemoCacheDao.getTextMemoHistories(
                individualVideoId);
        assertThat(stateHistoryList.size()).isEqualTo(2);
        assertThat(stateHistoryList.get(0).getId()).isNotNull();
        assertThat(stateHistoryList.get(1).getId()).isNotNull();
        assertThat(stateHistoryList.get(0).getIndividualVideoId()).isEqualTo(textMemo1.getIndividualVideoId());
        assertThat(stateHistoryList.get(1).getIndividualVideoId()).isEqualTo(textMemo2.getIndividualVideoId());

    }

    @Test
    @DisplayName("[TextMemoStateDaoTest] textMemoStateLatest_다이나모_get")
    public void textMemoStateLatest_다이나모_get() {

        // given
        String individualVideoId = TextMemoStateBuilder.getRandomIndividualVideoId();
        TextMemoSaveRequest textMemoSaveRequest = TextMemoStateBuilder.dynamoSaveRequestBuilder(
                individualVideoId);

        // when
        textMemoCacheDao.saveLatestToDynamo(textMemoSaveRequest.toLatestEntity());
        TextMemoLatest textMemoStateLatest = textMemoCacheDao.getLatestFromDynamo(
                textMemoSaveRequest.getIndividualVideoId());

        // then
        assertThat(textMemoStateLatest.getIndividualVideoId().toString()).isEqualTo(
                textMemoSaveRequest.getIndividualVideoId());
        assertThat(textMemoStateLatest.getStateJson()).isEqualTo(textMemoSaveRequest.getStateJson());
    }

    @Test
    @DisplayName("[TextMemoStateDaoTest] textMemoStateHistoryList_다이나모_save_and_get")
    public void textMemoStateHistoryList_다이나모_save_and_get() {

        // given
        String individualVideoId = TextMemoStateBuilder.getRandomIndividualVideoId();

        // textMemoState 두개 저장
        TextMemoSaveRequest textMemoSaveRequest1 = TextMemoStateBuilder.dynamoSaveRequestBuilder(
                individualVideoId);
        TextMemoSaveRequest textMemoSaveRequest2 = TextMemoStateBuilder.dynamoSaveRequestBuilder(
                individualVideoId);

        // input list 만들기
        TextMemoHistory textMemoStateHistory = textMemoSaveRequest1.toHistoryEntity();
        TextMemoHistory textMemoStateHistory2 = textMemoSaveRequest2.toHistoryEntity();
        List<TextMemoHistory> list = new ArrayList<>();
        list.add(textMemoStateHistory);
        list.add(textMemoStateHistory2);

        //when

        // 리스트 저장 후,
        textMemoCacheDao.saveHistoryListToDynamo(list);
        List<TextMemoHistory> historyListFromDynamo = textMemoCacheDao.getHistoryListFromDynamo(individualVideoId);

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
        TextMemoSaveRequest textMemoSaveRequest = TextMemoStateBuilder.dynamoSaveRequestBuilder(
                individualVideoId);
        Thread.sleep(10);
        TextMemoSaveRequest updatedTextMemoSaveRequest = TextMemoStateBuilder.dynamoSaveRequestBuilder(
                individualVideoId);

        // when

        // 처음 저장에 대한 latest state get
        textMemoCacheDao.saveLatestToDynamo(textMemoSaveRequest.toLatestEntity());
        TextMemoLatest textMemoStateLatest = textMemoCacheDao.getLatestFromDynamo(individualVideoId);

        // updated latest state
        // 똑같은 pk에 save하면 update 된다.
        textMemoCacheDao.saveLatestToDynamo(updatedTextMemoSaveRequest.toLatestEntity());
        TextMemoLatest updatedTextMemoStateLatest = textMemoCacheDao.getLatestFromDynamo(individualVideoId);

        // then
        assertThat(textMemoStateLatest.getCreatedAt()).isNotEqualTo(updatedTextMemoStateLatest.getCreatedAt());
    }
}