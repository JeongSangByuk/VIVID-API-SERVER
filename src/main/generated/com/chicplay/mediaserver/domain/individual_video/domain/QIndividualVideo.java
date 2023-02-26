package com.vivid.apiserver.domain.individual_video.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIndividualVideo is a Querydsl query type for IndividualVideo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIndividualVideo extends EntityPathBase<IndividualVideo> {

    private static final long serialVersionUID = -2051909316L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIndividualVideo individualVideo = new QIndividualVideo("individualVideo");

    public final com.vivid.apiserver.global.common.QBaseEntity _super = new com.vivid.apiserver.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final DateTimePath<java.time.LocalDateTime> lastAccessTime = createDateTime("lastAccessTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> progressRate = createNumber("progressRate", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final com.vivid.apiserver.domain.video.domain.QVideo video;

    public final com.vivid.apiserver.domain.video_space.domain.QVideoSpaceParticipant videoSpaceParticipant;

    public QIndividualVideo(String variable) {
        this(IndividualVideo.class, forVariable(variable), INITS);
    }

    public QIndividualVideo(Path<? extends IndividualVideo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIndividualVideo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIndividualVideo(PathMetadata metadata, PathInits inits) {
        this(IndividualVideo.class, metadata, inits);
    }

    public QIndividualVideo(Class<? extends IndividualVideo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.video = inits.isInitialized("video") ? new com.vivid.apiserver.domain.video.domain.QVideo(forProperty("video"), inits.get("video")) : null;
        this.videoSpaceParticipant = inits.isInitialized("videoSpaceParticipant") ? new com.vivid.apiserver.domain.video_space.domain.QVideoSpaceParticipant(forProperty("videoSpaceParticipant"), inits.get("videoSpaceParticipant")) : null;
    }

}

