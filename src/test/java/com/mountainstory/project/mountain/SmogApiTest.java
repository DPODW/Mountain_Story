package com.mountainstory.project.mountain;

import com.mountainstory.project.service.mountain.mountainweather.impl.ConvertWeatherLocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class SmogApiTest {

    private static final int PARENT_LOCATION_LIST_INDEX = 1;

    private static final int CHILD_LOCATION_LIST_INDEX = 0;


    @Test
    @DisplayName("부모 지역 길이가 5인 위치정보를 미세먼지 API 에 맞게 변환")
    void parentLocationLength5() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, UnsupportedEncodingException {

        /* given */
        ConvertWeatherLocation convertWeatherLocation = new ConvertWeatherLocation();

        String testLocation = "울산광역시  남구 삼산동";
        String encodingParentLocation = URLEncoder.encode("울산", "UTF-8");
        String childLocation = "남구";

        Method convertedShortLocation = getMethod();

        /* when */
        List<String> locationList = (List<String>)convertedShortLocation.invoke(convertWeatherLocation,testLocation);


        /* then */
        assertThat(locationList.get(PARENT_LOCATION_LIST_INDEX)).isEqualTo(encodingParentLocation);
        assertThat(locationList.get(CHILD_LOCATION_LIST_INDEX)).isEqualTo(childLocation);

    }

    @Test
    @DisplayName("부모 지역 길이가 4인 위치정보를 미세먼지 API 에 맞게 변환")
    void parentLocationLength4() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, UnsupportedEncodingException {

        /* given */
        ConvertWeatherLocation convertWeatherLocation = new ConvertWeatherLocation();

        String testLocation = "경상북도  안동시 태리";
        String encodingParentLocation = URLEncoder.encode("경북", "UTF-8");
        String childLocation = "안동시";

        Method convertedShortLocation = getMethod();

        /* when */
        List<String> locationList = (List<String>)convertedShortLocation.invoke(convertWeatherLocation,testLocation);


        /* then */
        assertThat(locationList.get(PARENT_LOCATION_LIST_INDEX)).isEqualTo(encodingParentLocation);
        assertThat(locationList.get(CHILD_LOCATION_LIST_INDEX)).isEqualTo(childLocation);

    }

    @Test
    @DisplayName("부모 지역 길이가 3인 위치정보를 미세먼지 API 에 맞게 변환")
    void parentLocationLength3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, UnsupportedEncodingException {

        /* given */
        ConvertWeatherLocation convertWeatherLocation = new ConvertWeatherLocation();

        String testLocation = "제주도  제주시 서귀포시";
        String encodingParentLocation = URLEncoder.encode("제주", "UTF-8");
        String childLocation = "제주시";

        Method convertedShortLocation = getMethod();

        /* when */
        List<String> locationList = (List<String>)convertedShortLocation.invoke(convertWeatherLocation,testLocation);


        /* then */
        assertThat(locationList.get(PARENT_LOCATION_LIST_INDEX)).isEqualTo(encodingParentLocation);
        assertThat(locationList.get(CHILD_LOCATION_LIST_INDEX)).isEqualTo(childLocation);

    }

    private static Method getMethod() throws NoSuchMethodException {
        Method convertedShortLocation = ConvertWeatherLocation.class.getDeclaredMethod("convertedShortLocation", String.class);
        convertedShortLocation.setAccessible(true);
        return convertedShortLocation;
    }
}
