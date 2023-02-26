package com.vivid.apiserver.domain.video_space.domain;

import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "video_space_participant")
@Getter
@SQLDelete(sql = "UPDATE video_space_participant SET deleted = true WHERE video_space_participant_id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoSpaceParticipant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_space_participant_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "video_space_id", nullable = false)
    private VideoSpace videoSpace;

    @OneToMany(mappedBy = "videoSpaceParticipant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IndividualVideo> individualVideos = new ArrayList<>();

    @Builder
    public VideoSpaceParticipant(User user, VideoSpace videoSpace) {
        changeUser(user);
        changeVideoSpace(videoSpace);
    }

    // 연관 관계 편의 메소드
    public void changeUser(User user) {

        // account 관계가 있다면,
        if(this.user != null){
            this.user.getVideoSpaceParticipants().remove(this);
        }

        this.user = user;
        this.user.getVideoSpaceParticipants().add(this);

    }

    // 연관 관계 편의 메소드
    public void changeVideoSpace(VideoSpace videoSpace) {

        // 기존의 video space 관계가 있다면,
        if(this.videoSpace != null){
            this.videoSpace.getVideoSpaceParticipants().remove(this);
        }

        this.videoSpace = videoSpace;
        this.videoSpace.getVideoSpaceParticipants().add(this);
    }

    // 연관 관계 편의 메소드
    public void delete() {

        // OnetoMany 연관 관계 끊기
        for (IndividualVideo individualVideo : individualVideos) {
            individualVideo.deleteMapping();
        }

        // ManyToOne 연관 관계 끊기, user쪽 영속성 컨텍스트 존재하기 때문에,
        deleteMapping();

        // toMany 연관관계 끊기
        individualVideos.clear();

    }

    // ManyToOne 연관 관계 끊기
    public void deleteMapping() {
        this.user = null;
        this.videoSpace = null;
    }






}
