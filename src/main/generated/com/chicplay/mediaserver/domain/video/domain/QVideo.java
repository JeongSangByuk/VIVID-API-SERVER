package com.vivid.apiserver.domain.video.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVideo is a Querydsl query type for Video
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVideo extends EntityPathBase<Video> {

    private static final long serialVersionUID = -1873213959L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVideo video = new QVideo("video");

    public final com.vivid.apiserver.global.common.QBaseEntity _super = new com.vivid.apiserver.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.vivid.apiserver.domain.individual_video.domain.IndividualVideo, com.vivid.apiserver.domain.individual_video.domain.QIndividualVideo> individualVideos = this.<com.vivid.apiserver.domain.individual_video.domain.IndividualVideo, com.vivid.apiserver.domain.individual_video.domain.QIndividualVideo>createList("individualVideos", com.vivid.apiserver.domain.individual_video.domain.IndividualVideo.class, com.vivid.apiserver.domain.individual_video.domain.QIndividualVideo.class, PathInits.DIRECT2);

    public final BooleanPath isUploaded = createBoolean("isUploaded");

    public final StringPath thumbnailImagePath = createString("thumbnailImagePath");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final StringPath uploaderId = createString("uploaderId");

    public final com.vivid.apiserver.domain.video_space.domain.QVideoSpace videoSpace;

    public QVideo(String variable) {
        this(Video.class, forVariable(variable), INITS);
    }

    public QVideo(Path<? extends Video> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVideo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVideo(PathMetadata metadata, PathInits inits) {
        this(Video.class, metadata, inits);
    }

    public QVideo(Class<? extends Video> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.videoSpace = inits.isInitialized("videoSpace") ? new com.vivid.apiserver.domain.video_space.domain.QVideoSpace(forProperty("videoSpace")) : null;
    }

}

