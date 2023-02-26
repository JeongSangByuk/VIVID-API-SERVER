package com.vivid.apiserver.domain.individual_video.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTextMemo is a Querydsl query type for TextMemo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTextMemo extends EntityPathBase<TextMemo> {

    private static final long serialVersionUID = 1281560941L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTextMemo textMemo = new QTextMemo("textMemo");

    public final com.vivid.apiserver.global.common.QBaseEntity _super = new com.vivid.apiserver.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final QIndividualVideo individualVideo;

    public final StringPath text = createString("text");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final TimePath<java.time.LocalTime> videoTime = createTime("videoTime", java.time.LocalTime.class);

    public QTextMemo(String variable) {
        this(TextMemo.class, forVariable(variable), INITS);
    }

    public QTextMemo(Path<? extends TextMemo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTextMemo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTextMemo(PathMetadata metadata, PathInits inits) {
        this(TextMemo.class, metadata, inits);
    }

    public QTextMemo(Class<? extends TextMemo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.individualVideo = inits.isInitialized("individualVideo") ? new QIndividualVideo(forProperty("individualVideo"), inits.get("individualVideo")) : null;
    }

}

