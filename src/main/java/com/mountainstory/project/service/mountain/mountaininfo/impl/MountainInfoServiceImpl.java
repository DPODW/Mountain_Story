package com.mountainstory.project.service.mountain.mountaininfo.impl;
import com.mountainstory.project.dto.mountain.mountainimg.MountainImgDto;
import com.mountainstory.project.dto.mountain.mountainimg.MountainImgXml;
import com.mountainstory.project.dto.mountain.mountaininfo.MountainInfoDto;
import com.mountainstory.project.dto.mountain.mountaininfo.MountainInfoXml;
import com.mountainstory.project.dto.mountain.mountainregion.MountainCoordinate;
import com.mountainstory.project.service.mountain.mountaininfo.MountainInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
@Service
public class MountainInfoServiceImpl implements MountainInfoService {

    private static final String LIST_DEFAULT_PAGE_NUMBER = "1";
    private static final String LIST_DEFAULT_RESULT_COUNT = "15";

    private final MountainCoordinateInfo mountainCoordinateInfo;
    private final ConvertMountainName convertMountainName;

    @Value("${openapi.serviceKey}")
    private String openApiServiceKey;

    public MountainInfoServiceImpl(MountainCoordinateInfo mountainCoordinateInfo, ConvertMountainName convertMountainName) {
        this.mountainCoordinateInfo = mountainCoordinateInfo;
        this.convertMountainName = convertMountainName;
    }


    @Override
    public List<MountainInfoDto> getAllMountainInfoList(String mountainName) throws UnsupportedEncodingException {
        long beforeTime = System.currentTimeMillis();

        List<MountainInfoDto> mountainInfoDtoList = searchMountainInfo(mountainName,LIST_DEFAULT_PAGE_NUMBER,LIST_DEFAULT_RESULT_COUNT);
        convertMountainName.removePartSameMountain(mountainName,mountainInfoDtoList);
        setImgToDtoList(mountainInfoDtoList);

        long afterTime = System.currentTimeMillis();
        long resultTime = (afterTime - beforeTime);
        log.info("setImgToDtoList 메소드 실행시간=> {}",resultTime);

        return mountainInfoDtoList;
    }


    @Override
    public MountainInfoDto setCoordinateInfo(MountainInfoDto mountainInfoDto) {
        MountainCoordinate mountainCoordinate = mountainCoordinateInfo.getMountainCoordinate(mountainInfoDto.getMountainLocation());
        mountainInfoDto.setMountainCoordinate(mountainCoordinate);
        return mountainInfoDto;
    }

    @Override
    public List<MountainInfoDto> searchMountainInfo(String mountainName,String mountainInfoPage,String mountainInfoResultCount) throws UnsupportedEncodingException {
        String encodeMountainName = URLEncoder.encode(mountainName, "UTF-8");
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr/1400000/service/cultureInfoService2/mntInfoOpenAPI2")
                .queryParam("searchWrd",encodeMountainName)
                .queryParam("pageNo",mountainInfoPage)
                .queryParam("numOfRows",mountainInfoResultCount)
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
        //TODO: 이미지 반복 요청이 리스트 호출 시간 지연의 주범임. . .
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


    private void setImgToDtoList(List<MountainInfoDto> mountainInfoDtoList) {
        mountainInfoDtoList.forEach(mountainInfoDto -> {
            List<MountainImgDto> imageList = searchMountainImg(mountainInfoDto.getMountainNo());
            if (!imageList.isEmpty()) {
                MountainImgDto firstImageUrl = imageList.get(0);
                mountainInfoDto.setMountainImgUrl(firstImageUrl.getMountainImgName());
            } else {
                mountainInfoDto.setMountainImgUrl(null);
            }
        });
    }

}
