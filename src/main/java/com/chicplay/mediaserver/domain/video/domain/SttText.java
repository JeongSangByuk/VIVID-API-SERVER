package com.chicplay.mediaserver.domain.video.domain;

import com.chicplay.mediaserver.global.common.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "stt_text")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SttText extends BaseTime {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "stt_text_id   ", columnDefinition = "1BINARY(16)")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    @Column(name="text")
    private String text;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "duration")
    private LocalTime duration;

    @Builder
    public SttText(Video video, String text, LocalTime startTime, LocalTime duration) {
        this.video = video;
        this.text = text;
        this.startTime = startTime;
        this.duration = duration;
    }
}
