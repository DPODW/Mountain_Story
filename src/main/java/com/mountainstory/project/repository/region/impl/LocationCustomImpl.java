package com.mountainstory.project.repository.region.impl;

import com.mountainstory.project.dto.mountain.mountainregion.MountainCoordinate;
import com.mountainstory.project.entity.region.QLocation;
import com.mountainstory.project.repository.region.LocationCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class LocationCustomImpl implements LocationCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public LocationCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public MountainCoordinate findCoordinateToLocation(String locationParent, String locationChild, String locationChildDetail) {
        QLocation qLocation = QLocation.location;

        BooleanExpression condition = qLocation.locationParent.eq(locationParent)
                .and(qLocation.locationChild.eq(locationChild))
                .and(qLocation.locationChildDetail.eq(locationChildDetail));

        MountainCoordinate mountainCoordinate = jpaQueryFactory
                .select(Projections.fields(MountainCoordinate.class,
                        qLocation.nx,
                        qLocation.ny))
                .from(qLocation)
                .where(condition)
                .fetchOne();

        return mountainCoordinate;
    }
}
