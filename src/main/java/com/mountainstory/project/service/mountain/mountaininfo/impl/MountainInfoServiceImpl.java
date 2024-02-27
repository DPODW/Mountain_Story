package com.mountainstory.project.service.mountain.mountaininfo.impl;
import com.mountainstory.project.dto.mountain.mountainimg.MountainImgDto;
import com.mountainstory.project.dto.mountain.mountainimg.MountainImgXml;
import com.mountainstory.project.dto.mountain.mountaininfo.MountainInfoDto;
import com.mountainstory.project.dto.mountain.mountaininfo.MountainInfoXml;
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

    private final MountainCoordinateInfo mountainCoordinateInfo;

    @Value("${openapi.serviceKey}")
    private String openApiServiceKey;

    public MountainInfoServiceImpl(MountainCoordinateInfo mountainCoordinateInfo) {
        this.mountainCoordinateInfo = mountainCoordinateInfo;
    }


    @Override
    public List<MountainInfoDto> getAllMountainInfo(String mountainName) throws UnsupportedEncodingException {
        List<MountainInfoDto> mountainInfoDtoList = searchMountainInfo(mountainName);
        setImgToDtoList(mountainInfoDtoList);
        mountainCoordinateInfo.getMountainCoordinate(mountainInfoDtoList);
        mountainCoordinateInfo.setCoordinateToDtoList(mountainInfoDtoList);
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
