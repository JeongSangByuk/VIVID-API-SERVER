package com.vivid.apiserver.domain.video.dao;

import com.vivid.apiserver.domain.video.domain.*;
import com.vivid.apiserver.test.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class SttTextRepositoryTest extends RepositoryTest {

    @Autowired
    private SttTextRepository sttTextRepository;

    @Autowired
    private VideoRepository videoRepository;

    private Video video;

    private SttText sttText;

    @BeforeEach
    void setUp() {

        //given
        video = videoRepository.save(VideoBuilder.build());
        sttText = SttTextBuilder.build(video);
    }

    @Test
    @DisplayName("[OcrTextRepository] save 성공 테스트")
    public void save_성공(){

        //when
        SttText savedSttText = sttTextRepository.save(sttText);

        //then
        assertThat(savedSttText.getId()).isNotNull();
        assertThat(savedSttText.getVideo().getId()).isEqualTo(video.getId());
        assertThat(savedSttText.getText()).isEqualTo(sttText.getText());
        assertThat(savedSttText.getStartTime()).isEqualTo(sttText.getStartTime());
        assertThat(savedSttText.getDuration()).isEqualTo(sttText.getDuration());
        assertThat(savedSttText.getCreatedDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(savedSttText.getUpdatedDate()).isBeforeOrEqualTo(LocalDateTime.now());

    }
}