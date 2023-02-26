package com.vivid.apiserver.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInstitution is a Querydsl query type for Institution
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QInstitution extends BeanPath<Institution> {

    private static final long serialVersionUID = 88142556L;

    public static final QInstitution institution = new QInstitution("institution");

    public final StringPath webexAccessToken = createString("webexAccessToken");

    public final StringPath zoom_access_token = createString("zoom_access_token");

    public QInstitution(String variable) {
        super(Institution.class, forVariable(variable));
    }

    public QInstitution(Path<? extends Institution> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInstitution(PathMetadata metadata) {
        super(Institution.class, metadata);
    }

}

