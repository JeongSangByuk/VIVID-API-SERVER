package com.vivid.apiserver.domain.individual_video.domain;

import com.vivid.apiserver.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "text_memo")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TextMemo extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "text_memo_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "individual_video_id")
    private IndividualVideo individualVideo;

    @Email
    @Column(name = "text")
    private String text;

    @Column(name = "video_time")
    private LocalTime videoTime;

    @Builder
    public TextMemo(IndividualVideo individualVideo, String text, LocalTime videoTime) {
        this.individualVideo = individualVideo;
        this.text = text;
        this.videoTime = videoTime;
    }
}
