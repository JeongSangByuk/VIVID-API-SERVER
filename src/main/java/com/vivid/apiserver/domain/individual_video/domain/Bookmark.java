package com.vivid.apiserver.domain.individual_video.domain;

import com.vivid.apiserver.global.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "bookmark")
@Getter
@NoArgsConstructor
public class Bookmark extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "bookmark_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "individual_video_id")
    private IndividualVideo individualVideo;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "video_time")
    private LocalTime videoTime;

    @Builder
    public Bookmark(IndividualVideo individualVideo, String name, String content, LocalTime videoTime) {
        this.individualVideo = individualVideo;
        this.name = name;
        this.content = content;
        this.videoTime = videoTime;
    }
}
