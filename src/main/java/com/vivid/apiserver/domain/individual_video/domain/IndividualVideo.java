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
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "individual_video")
@Getter
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
    private Long progressRate;

    @Column(name = "last_access_time")
    private LocalDateTime lastAccessTime;

    public static IndividualVideo of(Video video, VideoSpaceParticipant videoSpaceParticipant) {

        return IndividualVideo.builder()
                .video(video)
                .videoSpaceParticipant(videoSpaceParticipant)
                .progressRate(0L)
                .lastAccessTime(LocalDateTime.now())
                .build();
    }

    // 최종 접근 시간 변경 메소드
    public void changeLastAccessTime() {
        this.lastAccessTime = LocalDateTime.now();
    }

    // 최종 접근 시간 변경 메소드
    public void changeProgressRate(Long progressRate) {
        this.progressRate = progressRate;
    }


    // 전체 연관 관계 삭제 편의 메소드
    public void delete() {

        // OneToMany 연관관계(자식) 삭제. -> 영속성에 이미 있기 때문에,
        videoSpaceParticipant.getIndividualVideos().remove(this);

        // ManyToOne 연관관계(부모) 삭제
        deleteMapping();
    }

    // ManyToOne 연관관계(부모) 삭제
    public void deleteMapping() {
        video = null;
        videoSpaceParticipant = null;
    }

}
