package com.vivid.apiserver.domain.video.dao;

import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video.domain.VideoBuilder;
import com.vivid.apiserver.test.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class VideoRepositoryTest extends RepositoryTest {

    @Autowired
    private VideoRepository videoRepository;

    private Video video;

    @BeforeEach
    void setUp() {

        //given
        video = VideoBuilder.build();
    }

    @Test
    @DisplayName("[VideoRepository] save 성공 테스트")
    public void save_성공(){

        //when
        Video savedVideo = videoRepository.save(video);

        //then

        assertThat(savedVideo.getId()).isNotNull();

        // 처음 생성시에는 null이 맞다.
        assertThat(savedVideo.getIndividualVideos().size()).isEqualTo(0);
        assertThat(savedVideo.getFilePath()).isEqualTo(video.getFilePath());
        assertThat(savedVideo.getChattingJsonFilePath()).isEqualTo(video.getChattingJsonFilePath());
        assertThat(savedVideo.getUploaderId()).isEqualTo(video.getUploaderId());
        assertThat(savedVideo.getVideoIndexingImageFilePath()).isEqualTo(video.getVideoIndexingImageFilePath());
        assertThat(savedVideo.getCreatedDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(savedVideo.getUpdatedDate()).isBeforeOrEqualTo(LocalDateTime.now());



    }

}