package com.mountainstory.project.entity.region;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLocation is a Querydsl query type for Location
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocation extends EntityPathBase<Location> {

    private static final long serialVersionUID = 1491073900L;

    public static final QLocation location = new QLocation("location");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath locationChild = createString("locationChild");

    public final StringPath locationChildDetail = createString("locationChildDetail");

    public final StringPath locationParent = createString("locationParent");

    public final NumberPath<Integer> nx = createNumber("nx", Integer.class);

    public final NumberPath<Integer> ny = createNumber("ny", Integer.class);

    public QLocation(String variable) {
        super(Location.class, forVariable(variable));
    }

    public QLocation(Path<? extends Location> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocation(PathMetadata metadata) {
        super(Location.class, metadata);
    }

}

