package com.vivid.apiserver.domain.video_space.domain;

import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video.domain.Video;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "video_space")
@Getter
@SQLDelete(sql = "UPDATE video_space SET deleted = true WHERE video_space_id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PUBLIC)
public class VideoSpace extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_space_id", updatable = false)
    private Long id;

    @OneToMany(mappedBy = "videoSpace", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VideoSpaceParticipant> videoSpaceParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "videoSpace", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "host_email")
    private String hostEmail;

    @Column(name = "is_individual_video_space", columnDefinition = "TINYINT(1)")
    private boolean isIndividualVideoSpace;

    public static VideoSpace from(User user) {
        return VideoSpace.builder()
                .name("개인 영상")
                .description(user.getName() + "님의 개인 영상 그룹 입니다.")
                .hostEmail(user.getEmail())
                .isIndividualVideoSpace(true)
                .build();
    }
}
