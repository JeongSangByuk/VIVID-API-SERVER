package com.vivid.apiserver.domain.video.domain;

import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.global.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "video")
@Getter
@SQLDelete(sql = "UPDATE video SET deleted = true WHERE video_id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "video_space_id", nullable = false)
    private VideoSpace videoSpace;

    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IndividualVideo> individualVideos = new ArrayList<>();

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="uploader_id")
    private String uploaderId;

    @Column(name = "is_uploaded")
    private boolean isUploaded;

    @Column(name = "thumbnail_image")
    private String thumbnailImagePath;

    public void changeIsUploaded(boolean isUploaded) {
        this.isUploaded = isUploaded;
    }

    public void changeThumbnailImagePath(String thumbnailImagePath) {
        this.thumbnailImagePath = thumbnailImagePath;
    }

    @Builder
    public Video(VideoSpace videoSpace, String title, String description,String uploaderId) {
        this.videoSpace = videoSpace;
        this.title = title;
        this.description = description;
        this.uploaderId = uploaderId;
        this.isUploaded = false;

        // default image link
        this.thumbnailImagePath = "https://service-video-storage.s3.ap-northeast-2.amazonaws.com/no_image_available.png";
    }



    // 연관 관계 편의 메소드
    public void changeVideo(VideoSpace videoSpace){

        // 기존의 비디오 관계가 있다면,
        if(this.videoSpace != null){
            this.videoSpace.getVideos().remove(this);
        }

        this.videoSpace = videoSpace;
        this.videoSpace.getVideos().add(this);

    }

    // 전체 연관 관계 삭제 편의 메소드
    public void delete() {

        // ManyToOne 연관관계 삭제
        deleteMapping();

        // OnneToMany 연관관계 삭제
        for (IndividualVideo individualVideo : individualVideos) {
            individualVideo.deleteMapping();
        }

        // ManyToOne 연관관계 삭제
        individualVideos.clear();
    }

    // ManyToOne 연관관계(부모) 삭제
    public void deleteMapping() {
        this.videoSpace = null;
    }
}
