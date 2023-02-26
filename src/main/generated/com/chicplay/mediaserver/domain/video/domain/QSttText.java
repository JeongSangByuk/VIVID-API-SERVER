package com.vivid.apiserver.domain.video.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSttText is a Querydsl query type for SttText
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSttText extends EntityPathBase<SttText> {

    private static final long serialVersionUID = 1394323454L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSttText sttText = new QSttText("sttText");

    public final com.vivid.apiserver.global.common.QBaseEntity _super = new com.vivid.apiserver.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Integer> duration = createNumber("duration", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final TimePath<java.time.LocalTime> startTime = createTime("startTime", java.time.LocalTime.class);

    public final StringPath text = createString("text");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final QVideo video;

    public QSttText(String variable) {
        this(SttText.class, forVariable(variable), INITS);
    }

    public QSttText(Path<? extends SttText> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSttText(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSttText(PathMetadata metadata, PathInits inits) {
        this(SttText.class, metadata, inits);
    }

    public QSttText(Class<? extends SttText> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.video = inits.isInitialized("video") ? new QVideo(forProperty("video"), inits.get("video")) : null;
    }

}

