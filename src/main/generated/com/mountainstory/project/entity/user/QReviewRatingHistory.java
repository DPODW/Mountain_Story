package com.mountainstory.project.entity.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewRatingHistory is a Querydsl query type for ReviewRatingHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewRatingHistory extends EntityPathBase<ReviewRatingHistory> {

    private static final long serialVersionUID = -111498305L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewRatingHistory reviewRatingHistory = new QReviewRatingHistory("reviewRatingHistory");

    public final BooleanPath isReviewed = createBoolean("isReviewed");

    public final QMember memberId;

    public final NumberPath<Long> reviewHistoryId = createNumber("reviewHistoryId", Long.class);

    public final NumberPath<Long> reviewNumber = createNumber("reviewNumber", Long.class);

    public QReviewRatingHistory(String variable) {
        this(ReviewRatingHistory.class, forVariable(variable), INITS);
    }

    public QReviewRatingHistory(Path<? extends ReviewRatingHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewRatingHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewRatingHistory(PathMetadata metadata, PathInits inits) {
        this(ReviewRatingHistory.class, metadata, inits);
    }

    public QReviewRatingHistory(Class<? extends ReviewRatingHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberId = inits.isInitialized("memberId") ? new QMember(forProperty("memberId")) : null;
    }

}

