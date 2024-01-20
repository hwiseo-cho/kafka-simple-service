package com.hwiseo.kafka.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserEventVo is a Querydsl query type for UserEventVo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEventVo extends EntityPathBase<UserEventVo> {

    private static final long serialVersionUID = 846696707L;

    public static final QUserEventVo userEventVo = new QUserEventVo("userEventVo");

    public final StringPath colorName = createString("colorName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath timestamp = createString("timestamp");

    public final StringPath userAgent = createString("userAgent");

    public final StringPath userName = createString("userName");

    public QUserEventVo(String variable) {
        super(UserEventVo.class, forVariable(variable));
    }

    public QUserEventVo(Path<? extends UserEventVo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEventVo(PathMetadata metadata) {
        super(UserEventVo.class, metadata);
    }

}

