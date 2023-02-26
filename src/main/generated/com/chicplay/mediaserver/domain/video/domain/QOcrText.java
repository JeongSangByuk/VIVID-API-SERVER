package com.vivid.apiserver.domain.video.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOcrText is a Querydsl query type for OcrText
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOcrText extends EntityPathBase<OcrText> {

    private static final long serialVersionUID = 1650733417L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOcrText ocrText = new QOcrText("ocrText");

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

    public QOcrText(String variable) {
        this(OcrText.class, forVariable(variable), INITS);
    }

    public QOcrText(Path<? extends OcrText> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOcrText(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOcrText(PathMetadata metadata, PathInits inits) {
        this(OcrText.class, metadata, inits);
    }

    public QOcrText(Class<? extends OcrText> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.video = inits.isInitialized("video") ? new QVideo(forProperty("video"), inits.get("video")) : null;
    }

}

