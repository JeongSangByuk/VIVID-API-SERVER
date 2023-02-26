package com.vivid.apiserver.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -644587737L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.vivid.apiserver.global.common.QBaseEntity _super = new com.vivid.apiserver.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath email = createString("email");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final QInstitution institution;

    public final ComparablePath<java.util.UUID> lastAccessIndividualVideoId = createComparable("lastAccessIndividualVideoId", java.util.UUID.class);

    public final StringPath name = createString("name");

    public final StringPath picture = createString("picture");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final ListPath<com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant, com.vivid.apiserver.domain.video_space.domain.QVideoSpaceParticipant> videoSpaceParticipants = this.<com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant, com.vivid.apiserver.domain.video_space.domain.QVideoSpaceParticipant>createList("videoSpaceParticipants", com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant.class, com.vivid.apiserver.domain.video_space.domain.QVideoSpaceParticipant.class, PathInits.DIRECT2);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.institution = inits.isInitialized("institution") ? new QInstitution(forProperty("institution")) : null;
    }

}

