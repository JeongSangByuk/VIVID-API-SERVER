package com.vivid.apiserver.domain.individual_video.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSnapshotImage is a Querydsl query type for SnapshotImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSnapshotImage extends EntityPathBase<SnapshotImage> {

    private static final long serialVersionUID = -1958597551L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSnapshotImage snapshotImage = new QSnapshotImage("snapshotImage");

    public final com.vivid.apiserver.global.common.QBaseEntity _super = new com.vivid.apiserver.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath filePath = createString("filePath");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final QIndividualVideo individualVideo;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final TimePath<java.time.LocalTime> videoTime = createTime("videoTime", java.time.LocalTime.class);

    public QSnapshotImage(String variable) {
        this(SnapshotImage.class, forVariable(variable), INITS);
    }

    public QSnapshotImage(Path<? extends SnapshotImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSnapshotImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSnapshotImage(PathMetadata metadata, PathInits inits) {
        this(SnapshotImage.class, metadata, inits);
    }

    public QSnapshotImage(Class<? extends SnapshotImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.individualVideo = inits.isInitialized("individualVideo") ? new QIndividualVideo(forProperty("individualVideo"), inits.get("individualVideo")) : null;
    }

}

