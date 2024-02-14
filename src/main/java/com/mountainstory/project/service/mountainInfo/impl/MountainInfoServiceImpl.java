package com.mountainstory.project.service.mountainInfo.impl;
import com.mountainstory.project.dto.mountain.mountainimg.MountainImgDto;
import com.mountainstory.project.dto.mountain.mountainimg.MountainImgXml;
import com.mountainstory.project.dto.mountain.mountaininfo.MountainInfoDto;
import com.mountainstory.project.dto.mountain.mountaininfo.MountainInfoXml;
import com.mountainstory.project.dto.mountain.mountaininfo.MountainWeather;
import com.mountainstory.project.service.mountainInfo.MountainInfoService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class MountainInfoServiceImpl implements MountainInfoService {

    private final MountainInfoServiceHelper mountainInfoServiceHelper;

    @Value("${openapi.serviceKey}")
    private String openApiServiceKey;

    public MountainInfoServiceImpl(MountainInfoServiceHelper mountainInfoServiceHelper) {
        this.mountainInfoServiceHelper = mountainInfoServiceHelper;
    }


    @Override
    public List<MountainInfoDto> getAllMountainInfo(String mountainName) throws UnsupportedEncodingException {
        List<MountainInfoDto> mountainInfoDtoList = searchMountainInfo(mountainName);
        setImgToDtoList(mountainInfoDtoList);
        mountainInfoServiceHelper.getMountainCoordinate(mountainInfoDtoList);
        mountainInfoServiceHelper.setCoordinateToDtoList(mountainInfoDtoList);

        log.info("검색 결과>{}",mountainInfoDtoList);
        return mountainInfoDtoList;
    }

    @Override
    public List<MountainInfoDto> searchMountainInfo(String mountainName) throws UnsupportedEncodingException {
        String encodeMountainName = URLEncoder.encode(mountainName, "UTF-8");
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr/1400000/service/cultureInfoService2/mntInfoOpenAPI2")
                .queryParam("searchWrd",encodeMountainName)
                .queryParam("pageNo","1")
                .queryParam("numOfRows","5")
                .queryParam("ServiceKey",openApiServiceKey)
                .build(true)
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        MountainInfoXml mountainInfoXml = restTemplate.getForObject(uri, MountainInfoXml.class);
        List<MountainInfoDto> mountainInfoDtoList = mountainInfoXml.getBody().getMountainInfoDto();
        return mountainInfoDtoList;
    }


    @Override
    public List<MountainImgDto> searchMountainImg(String mountainNumber) {
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr/1400000/service/cultureInfoService2/mntInfoImgOpenAPI2")
                .queryParam("mntiListNo",mountainNumber)
                .queryParam("pageNo","1")
                .queryParam("numOfRows","1")
                .queryParam("ServiceKey",openApiServiceKey)
                .build(true)
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        MountainImgXml mountainXml = restTemplate.getForObject(uri, MountainImgXml.class);
        List<MountainImgDto> mountainImgDto = mountainXml.getBody().getMountainImgDto();
        return mountainImgDto;
    }




    @Override
    public  MountainWeather searchMountainWeather(Integer nx , Integer ny) throws ParseException {
        //TODO: base_time: 특정 시간 지나면 자동 업데이트 하도록
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst")
                .queryParam("serviceKey",openApiServiceKey)
                .queryParam("numOfRows","12")
                .queryParam("pageNo","1")
                .queryParam("dataType","JSON")
                .queryParam("base_date",baseDate())
                .queryParam("base_time","0500")
                .queryParam("nx",nx.toString())
                .queryParam("ny",ny.toString())
                .build(true)
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        String jsonWeatherData = restTemplate.getForObject(uri, String.class);

        MountainWeather weatherInfoDto = getWeatherInfo(jsonWeatherData);
        log.info(">>>{}",weatherInfoDto);
        return weatherInfoDto;
    }


    private static String baseDate(){
        LocalDateTime nowBaseDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return nowBaseDate.format(formatter);
    }

    private static void bastTime() throws java.text.ParseException {
        LocalDateTime nowTime = LocalDateTime.now();
        SimpleDateFormat formatter =new SimpleDateFormat("HH:mm");
        Date da = formatter.parse(nowTime.toString());

    }


    private static MountainWeather getWeatherInfo(String jsonWeatherData) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        List<HashMap<String, Object>> weatherMapList = new ArrayList<>();

        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonWeatherData);
        JSONObject jsonResponse = (JSONObject) jsonObject.get("response");
        JSONObject jsonBody = (JSONObject) jsonResponse.get("body");
        JSONObject jsonItems = (JSONObject) jsonBody.get("items");
        JSONArray jsonItem = (JSONArray) jsonItems.get("item");
        log.info("{}",jsonResponse);

        jsonItem.forEach(item -> {
            JSONObject weatherInfo = (JSONObject) item;
            String category = (String) weatherInfo.get("category");
            Object fcstValue = weatherInfo.get("fcstValue");

            HashMap<String,Object> weatherMap = new HashMap<>();
            weatherMap.put(category,fcstValue);

            weatherMapList.add(weatherMap);
        });

        return setMountainWeatherToDto(weatherMapList);
    }

    private static MountainWeather setMountainWeatherToDto(List<HashMap<String, Object>> weatherMapList) {
        MountainWeather mountainWeather = new MountainWeather();

        for (int i = 0; i < weatherMapList.size(); i++) {
            Map<String, Object> weatherMap = weatherMapList.get(i);

            switch (i) {
                case 0:
                    mountainWeather.setCurrentTemperature(Double.parseDouble(weatherMap.get("TMP").toString()));
                    break;
                case 1:
                    mountainWeather.setEastAndWestWindSpeed(Double.parseDouble(weatherMap.get("UUU").toString()));
                    break;
                case 2:
                    mountainWeather.setSouthAndNorthWindSpeed(Double.parseDouble(weatherMap.get("VVV").toString()));
                    break;
                case 3:
                    mountainWeather.setWindDirection(Double.parseDouble(weatherMap.get("VEC").toString()));
                    break;
                case 4:
                    mountainWeather.setAverageWindSpeed(Double.parseDouble(weatherMap.get("WSD").toString()));
                    break;
                case 5:
                    mountainWeather.setSkyState(Double.parseDouble(weatherMap.get("SKY").toString()));
                    break;
                case 6:
                    mountainWeather.setRainForm(Double.parseDouble(weatherMap.get("PTY").toString()));
                    break;
                case 7:
                    mountainWeather.setRainPercentage(Double.parseDouble(weatherMap.get("POP").toString()));
                    break;
                case 8:
                    mountainWeather.setWaveHeight(Double.parseDouble(weatherMap.get("WAV").toString()));
                    break;
                case 9:
                    mountainWeather.setRainAmount(weatherMap.get("PCP").toString());
                    break;
                case 10:
                    mountainWeather.setHumidity(Double.parseDouble(weatherMap.get("REH").toString()));
                    break;
                case 11:
                    mountainWeather.setSnowAmount(weatherMap.get("SNO").toString());
                    break;
            }
        }
        return mountainWeather;
    }


    private void setImgToDtoList(List<MountainInfoDto> mountainInfoDtoList) {
        //순환 참조 문제로 인하여 MountainInfoServiceHelper 에서 사용될수 없음.
        mountainInfoDtoList.forEach(mountainInfoDto -> {
            List<MountainImgDto> imageList = searchMountainImg(mountainInfoDto.getMountainNo());
            if (!imageList.isEmpty()) {
                MountainImgDto firstImageUrl = imageList.get(0);
                mountainInfoDto.setMountainImgUrl(firstImageUrl.getMountainImgName());
            } else {
                mountainInfoDto.setMountainImgUrl("이미지 제공 되지 않음");
            }
        });
    }

}
