package com.vivid.apiserver.domain.video.domain;

import com.vivid.apiserver.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "ocr_text")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OcrText extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ocr_text_id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    @Column(name="text")
    private String text;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "duration")
    private Integer duration;

    @Builder
    public OcrText(Video video, String text, LocalTime startTime, Integer duration) {
        this.video = video;
        this.text = text;
        this.startTime = startTime;
        this.duration = duration;
    }
}
