package com.vivid.apiserver.domain.video_space.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVideoSpace is a Querydsl query type for VideoSpace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVideoSpace extends EntityPathBase<VideoSpace> {

    private static final long serialVersionUID = 818846292L;

    public static final QVideoSpace videoSpace = new QVideoSpace("videoSpace");

    public final com.vivid.apiserver.global.common.QBaseEntity _super = new com.vivid.apiserver.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath description = createString("description");

    public final StringPath hostEmail = createString("hostEmail");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isIndividualVideoSpace = createBoolean("isIndividualVideoSpace");

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final ListPath<com.vivid.apiserver.domain.video.domain.Video, com.vivid.apiserver.domain.video.domain.QVideo> videos = this.<com.vivid.apiserver.domain.video.domain.Video, com.vivid.apiserver.domain.video.domain.QVideo>createList("videos", com.vivid.apiserver.domain.video.domain.Video.class, com.vivid.apiserver.domain.video.domain.QVideo.class, PathInits.DIRECT2);

    public final ListPath<VideoSpaceParticipant, QVideoSpaceParticipant> videoSpaceParticipants = this.<VideoSpaceParticipant, QVideoSpaceParticipant>createList("videoSpaceParticipants", VideoSpaceParticipant.class, QVideoSpaceParticipant.class, PathInits.DIRECT2);

    public QVideoSpace(String variable) {
        super(VideoSpace.class, forVariable(variable));
    }

    public QVideoSpace(Path<? extends VideoSpace> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVideoSpace(PathMetadata metadata) {
        super(VideoSpace.class, metadata);
    }

}

