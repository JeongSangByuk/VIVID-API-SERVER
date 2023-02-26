package com.vivid.apiserver.domain.individual_video.application;

import com.vivid.apiserver.domain.individual_video.dao.TextMemoStateDao;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoStateHistory;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoStateLatest;
import com.vivid.apiserver.domain.individual_video.dto.TextMemoStateRedisSaveRequest;
import com.vivid.apiserver.domain.individual_video.dto.TextMemoStateResponse;
import com.vivid.apiserver.domain.individual_video.exception.TextMemoStateNotExistException;
import com.vivid.apiserver.domain.user.application.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TextMemoStateService {

    private final UserService userService;

    private final IndividualVideoService individualVideoService;

    private final TextMemoStateDao textMemoStateDao;


    // redis에 textMemoState 객체 하나 저장 메소드
    public void saveToRedis(final TextMemoStateRedisSaveRequest textMemoState, String individualVideoId) {

        // save to redis
        textMemoStateDao.saveToRedis(textMemoState.toEntity(individualVideoId));
    }

    // redis로 부터 latest get
    public TextMemoStateResponse getLatestFromRedis(String individualVideoId) {

        // 권한 체크
        individualVideoService.checkValidUserAccessId(individualVideoId);


        // redis return 값이 null 일 경우, dynamoDB에서 get
        // null일 경우 dyanamo에서 get
        TextMemoStateLatest textMemoStateLatest = Optional
                .ofNullable(textMemoStateDao.getLatestFromRedis(individualVideoId))
                .orElse(textMemoStateDao.getLatestFromDynamo(individualVideoId));

        // redis dynamo 둘다 null -> 빈 response 반환.
        if (textMemoStateLatest == null) {
            return new TextMemoStateResponse();
        }

        // entitiy to dto
        TextMemoStateResponse textMemoStateResponse = TextMemoStateResponse.builder().textMemoState(textMemoStateLatest).build();

        return textMemoStateResponse;
    }

    // dynamoDB에서 history list get
    public List<TextMemoStateResponse> getHistoryFromDynamoDb(String individualVideoId) {

        // 권한 체크
        individualVideoService.checkValidUserAccessId(individualVideoId);

        List<TextMemoStateHistory> historyListFromDynamo = textMemoStateDao.getHistoryListFromDynamo(individualVideoId);

        // dto로 객체 변환
        List<TextMemoStateResponse> textMemoStateResponseList = new ArrayList<>();
        historyListFromDynamo.forEach(textMemoStateHistory -> {
            textMemoStateResponseList.add(TextMemoStateResponse.builder().textMemoState(textMemoStateHistory).build());
        });

        return textMemoStateResponseList;
    }

    // redis 캐시에 있던 데이터 전부 save
    public void saveAllToDynamoDb(String individualVideoId) {

        // 권한 체크
        individualVideoService.checkValidUserAccessId(individualVideoId);

        // latest save
        saveLatestToDynamoDb(individualVideoId);

        // history save
        saveHistoryToDynamoDb(individualVideoId);

    }

    // dynamo db에 textMemoStateLatest문 insert
    // save시 redis에서 delete
    public void saveLatestToDynamoDb(String individualVideoId) {

        // 요청된 individualVideoId의 state latest를 redis에서 가져온다. 없을 경우 예외 throw
        TextMemoStateLatest textMemoStateLatest = Optional.ofNullable(textMemoStateDao.getLatestFromRedis(individualVideoId))
                .orElseThrow(TextMemoStateNotExistException::new);

        // 해당 state를 저장한다.
        textMemoStateDao.saveLatestToDynamo(textMemoStateLatest);

        // redis에서 삭제
        textMemoStateDao.deleteLatestFromRedis(individualVideoId);
    }

    // dynamo db에 textMemoStateHistory문 insert
    // save시 redis에서 delete
    public void saveHistoryToDynamoDb(String individualVideoId) {

        // text_memo_state_history를 redis에서 get
        List<TextMemoStateHistory> historyListFromRedis = textMemoStateDao.getTextMemoStateHistoryFromRedis(individualVideoId);

        // 리스트 비었을 경우 exception throw
        if (historyListFromRedis.isEmpty())
            throw new TextMemoStateNotExistException();

        // get된 text_memo_state_history 다이나모db에 save
        textMemoStateDao.saveHistoryListToDynamo(historyListFromRedis);

        // redis에서 삭제
        textMemoStateDao.deleteHistoryFromRedis(individualVideoId);
    }

    /*
    old version
     */

    // redis에 textMemoState 객체 리스트 저장 메소드
//    public void saveTextMemoStatesToRedis(List<TextMemoStateRedisSaveRequest> textMemoStates){
//
//        textMemoStateDao.saveListToRedis(textMemoStates);
//    }


}
