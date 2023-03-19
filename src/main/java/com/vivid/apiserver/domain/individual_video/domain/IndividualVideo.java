package com.vivid.apiserver.domain.individual_video.domain;

import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.global.common.BaseEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "individual_video")
@Getter
@DynamicUpdate
@SQLDelete(sql = "UPDATE individual_video SET deleted = true WHERE individual_video_id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class IndividualVideo extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "individual_video_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "video_space_participant_id", nullable = false)
    private VideoSpaceParticipant videoSpaceParticipant;

    @Column(name = "progress_rate")
    private Integer progressRate;

    @Column(name = "last_access_time")
    private LocalDateTime lastAccessTime;

    public static IndividualVideo of(Video video, VideoSpaceParticipant videoSpaceParticipant) {
        return IndividualVideo.builder()
                .video(video)
                .videoSpaceParticipant(videoSpaceParticipant)
                .progressRate(0)
                .lastAccessTime(LocalDateTime.now())
                .build();
    }

    public void changeLastAccessTime() {
        this.lastAccessTime = LocalDateTime.now();
    }

    public void changeProgressRate(Integer progressRate) {
        this.progressRate = progressRate;
    }
}
