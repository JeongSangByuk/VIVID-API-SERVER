package com.vivid.apiserver.domain.individual_video.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDrawingMemo is a Querydsl query type for DrawingMemo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDrawingMemo extends EntityPathBase<DrawingMemo> {

    private static final long serialVersionUID = 331193170L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDrawingMemo drawingMemo = new QDrawingMemo("drawingMemo");

    public final com.vivid.apiserver.global.common.QBaseEntity _super = new com.vivid.apiserver.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Integer> duration = createNumber("duration", Integer.class);

    public final StringPath filePath = createString("filePath");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final QIndividualVideo individualVideo;

    public final TimePath<java.time.LocalTime> startTime = createTime("startTime", java.time.LocalTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QDrawingMemo(String variable) {
        this(DrawingMemo.class, forVariable(variable), INITS);
    }

    public QDrawingMemo(Path<? extends DrawingMemo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDrawingMemo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDrawingMemo(PathMetadata metadata, PathInits inits) {
        this(DrawingMemo.class, metadata, inits);
    }

    public QDrawingMemo(Class<? extends DrawingMemo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.individualVideo = inits.isInitialized("individualVideo") ? new QIndividualVideo(forProperty("individualVideo"), inits.get("individualVideo")) : null;
    }

}

