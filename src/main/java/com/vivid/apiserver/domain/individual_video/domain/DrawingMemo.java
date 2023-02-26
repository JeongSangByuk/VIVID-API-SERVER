package com.vivid.apiserver.domain.individual_video.domain;

import com.vivid.apiserver.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "drawing_memo")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrawingMemo extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "drawing_memo_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "individual_video_id")
    private IndividualVideo individualVideo;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "file_path")
    private String filePath;

    @Builder
    public DrawingMemo(IndividualVideo individualVideo, LocalTime startTime, Integer duration, String filePath) {
        this.individualVideo = individualVideo;
        this.startTime = startTime;
        this.duration = duration;
        this.filePath = filePath;
    }
}
