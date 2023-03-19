package com.vivid.apiserver.domain.video_space.domain;

import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.global.common.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "video_space_participant")
@Getter
@DynamicUpdate
@SQLDelete(sql = "UPDATE video_space_participant SET deleted = true WHERE video_space_participant_id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class VideoSpaceParticipant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_space_participant_id", updatable = false)
    private Long id;

    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "video_space_id", nullable = false)
    private VideoSpace videoSpace;

    @OneToMany(mappedBy = "videoSpaceParticipant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IndividualVideo> individualVideos = new ArrayList<>();

    public static VideoSpaceParticipant of(VideoSpace videoSpace, User user) {
        return VideoSpaceParticipant.builder()
                .user(user)
                .videoSpace(videoSpace)
                .email(user.getEmail())
                .build();
    }
}
