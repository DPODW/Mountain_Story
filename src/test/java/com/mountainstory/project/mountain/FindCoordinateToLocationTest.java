package com.mountainstory.project.mountain;

import com.mountainstory.project.dto.mountain.mountainregion.MountainCoordinate;
import com.mountainstory.project.repository.region.LocationCustom;
import com.mountainstory.project.repository.region.LocationRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FindCoordinateToLocationTest {
    private LocationRepository locationRepository;


    @Autowired
    public FindCoordinateToLocationTest(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
            return new JPAQueryFactory(entityManager);
        }
    }


    @Test
    @DisplayName("QueryDSL 을 활용한 좌표값 검색 테스트")
    void findCoordinateToLocation(){

        /* given */
        String testLocationParent = "서울특별시";
        String testLocationChild = "종로구";
        String testLocationChildDetail = "사직동";

        MountainCoordinate testCoordinate =new MountainCoordinate();
        testCoordinate.setNx(60);
        testCoordinate.setNy(127);
        //위의 테스트 위치에 대한 좌표값

        /* when */
        MountainCoordinate coordinateToLocation = locationRepository.findCoordinateToLocation(testLocationParent, testLocationChild, testLocationChildDetail);

        /* then */
        assertThat(coordinateToLocation.getNx()).isEqualTo(testCoordinate.getNx());
        assertThat(coordinateToLocation.getNy()).isEqualTo(testCoordinate.getNy());

    }
}
