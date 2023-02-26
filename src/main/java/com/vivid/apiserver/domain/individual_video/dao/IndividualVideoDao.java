package com.vivid.apiserver.domain.individual_video.dao;

import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.individual_video.domain.QIndividualVideo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class IndividualVideoDao {

    private final JdbcTemplate jdbcTemplate;

    private final JPAQueryFactory query;

    /**
     * uuid 방식이기 때문에 bluk insert 직접 구현 불필요
     */
    public void saveAll(List<IndividualVideo> individualVideos) {

        int batchSize = 1000;

        List<IndividualVideo> subItems = new ArrayList<>();

        for (int i = 0; i < individualVideos.size(); i++) {

            subItems.add(individualVideos.get(i));

            // batch size 됐을 때, batch insert
            if ((i + 1) % batchSize == 0) {
                batchInsert(subItems);
            }
        }

        // batch size를 채우지 못하고 반복문 종료, 남은 것들 batch isnert
        if (!subItems.isEmpty()) {
            batchInsert(subItems);
        }
    }

    private void batchInsert(List<IndividualVideo> subItems) {

        // jdbc를 이용한 batch update
        jdbcTemplate.batchUpdate("INSERT INTO individual_video (`individual_video_id`,`video_id`, `video_space_participant_id`) VALUES (?, ?, ?)",

                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {

                        ps.setBytes(1, asBytes(UUID.randomUUID()));
                        ps.setLong(2, subItems.get(i).getVideo().getId());
                        ps.setLong(3, subItems.get(i).getVideoSpaceParticipant().getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return subItems.size();
                    }

                });

        subItems.clear();
    }

    public byte[] asBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }




}
