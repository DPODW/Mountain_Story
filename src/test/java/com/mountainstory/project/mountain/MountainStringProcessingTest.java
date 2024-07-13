package com.mountainstory.project.mountain;
import com.mountainstory.project.dto.mountain.mountaininfo.MountainInfoDto;
import com.mountainstory.project.dto.mountain.mountainregion.MountainLocation;
import com.mountainstory.project.repository.region.LocationRepository;
import com.mountainstory.project.service.mountain.mountaininfo.impl.ConvertMountainName;
import com.mountainstory.project.service.mountain.mountaininfo.impl.MountainCoordinateInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.xml.stream.Location;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class MountainStringProcessingTest {

    MountainCoordinateInfo mountainCoordinateInfo;

    @MockBean
    LocationRepository locationRepository;

    @BeforeEach
    void setup(){
        mountainCoordinateInfo = new MountainCoordinateInfo(locationRepository);
    }

    @Test
    @DisplayName("공백을 기준으로 문자열 자르기")
    void splitMountainLocation() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

          /* given */
          String testLocation = "울산광역시 남구 삼산동 ";

          Method splitMountainLocation = MountainCoordinateInfo.class.getDeclaredMethod("splitMountainLocation",String.class);
          splitMountainLocation.setAccessible(true);
          //Reflection 기능을 통하여 splitMountainLocation 메소드 가져옴


          /* when */
          MountainLocation testMountainLocation = (MountainLocation)splitMountainLocation.invoke(mountainCoordinateInfo, testLocation);

          /* then */
          assertThat(testMountainLocation.getLocationParent()).isEqualTo("울산광역시");
          assertThat(testMountainLocation.getLocationChild()).isEqualTo("남구");
          assertThat(testMountainLocation.getLocationChildDetail()).isEqualTo("삼산동");
    }


    @Test
    @DisplayName("특수 문자 (_) 제거하기")
    void deleteSpecialSymbols() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        /* given*/
        ConvertMountainName convertMountainName = new ConvertMountainName();
        String testMountainName = "설악산_대청봉";

        Method deleteSpecialSymbols = ConvertMountainName.class.getDeclaredMethod("deleteSpecialSymbols", String.class);
        deleteSpecialSymbols.setAccessible(true);

        /* when */
        String deleteSuccess = (String) deleteSpecialSymbols.invoke(convertMountainName, testMountainName);

        /* then */
        assertThat(deleteSuccess).isEqualTo("설악산");
    }


    @Test
    @DisplayName("이름이 부분만 같은 산과 높이가 0 인 산 제거하기")
    void removeWrongFormMountain() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        /* given */
        ConvertMountainName convertMountainName = new ConvertMountainName();
        String testMountainName = "남산";

        MountainInfoDto allWrongDto = new MountainInfoDto();
        allWrongDto.setMountainName("금남산");
        allWrongDto.setMountainHigh("0");

        MountainInfoDto nameWrongDto = new MountainInfoDto();
        nameWrongDto.setMountainName("금남산");
        nameWrongDto.setMountainHigh("100");

        MountainInfoDto height0Dto = new MountainInfoDto();
        height0Dto.setMountainName("남산");
        height0Dto.setMountainHigh("0");

        List<MountainInfoDto> mountainInfoDtoList = new ArrayList<>(Arrays.asList(allWrongDto,nameWrongDto,height0Dto));


        /* when */
        Method removePartSameMountain = ConvertMountainName.class.getDeclaredMethod("removePartSameMountain",String.class, List.class);
        removePartSameMountain.setAccessible(true);

        removePartSameMountain.invoke(convertMountainName, testMountainName, mountainInfoDtoList);


        /* then */
        assertThat(mountainInfoDtoList.size()).isEqualTo(0);

    }




}
