package com.vivid.apiserver.domain.video.domain;

import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.global.common.BaseEntity;
import java.util.ArrayList;
import java.util.List;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "video")
@Getter
@DynamicUpdate
@SQLDelete(sql = "UPDATE video SET deleted = true WHERE video_id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_space_id", nullable = false)
    private VideoSpace videoSpace;

    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY)
    private List<IndividualVideo> individualVideos = new ArrayList<>();

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "uploader_id")
    private String uploaderId;

    @Column(name = "is_uploaded")
    private boolean isUploaded;

    @Column(name = "thumbnail_image")
    private String thumbnailImagePath;

    public void changeWhenUploaded(boolean isUploaded) {
        this.isUploaded = isUploaded;
    }

    public void changeThumbnailImagePath(String thumbnailImagePath) {
        this.thumbnailImagePath = thumbnailImagePath;
    }

    public void deleteIndividualVideos() {
        this.individualVideos = null;
    }

    @Builder
    public Video(VideoSpace videoSpace, String title, String description, String uploaderId) {
        this.videoSpace = videoSpace;
        this.title = title;
        this.description = description;
        this.uploaderId = uploaderId;
        this.isUploaded = false;

        // default image link
        this.thumbnailImagePath = "https://service-video-storage.s3.ap-northeast-2.amazonaws.com/no_image_available.png";
    }
}
