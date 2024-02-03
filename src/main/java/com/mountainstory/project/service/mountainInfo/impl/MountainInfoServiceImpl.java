package com.mountainstory.project.service.mountainInfo.impl;

import com.mountainstory.project.dto.mountainInfo.MountainInfoDto;
import com.mountainstory.project.dto.mountainInfo.MountainInfoXml;
import com.mountainstory.project.service.mountainInfo.MountainInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Service
public class MountainInfoServiceImpl implements MountainInfoService {

    @Value("${openapi.serviceKey}")
    private String openApiServiceKey;

    @Override
    public List<MountainInfoDto> searchMountainInfo(String mountainName) throws UnsupportedEncodingException {
        String encodeMountainName = URLEncoder.encode(mountainName, "UTF-8");
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr/1400000/service/cultureInfoService2/mntInfoOpenAPI2")
                .queryParam("searchWrd",encodeMountainName)
                .queryParam("pageNo","1")
                .queryParam("numOfRows","2")
                .queryParam("ServiceKey",openApiServiceKey)
                .build(true) //false: 인코딩 실행 , true: 이미 인코딩 되어있음 (이미 인코딩 되어있는 서비스 키가 properties 에 있음)
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        MountainInfoXml mountainInfoXml = restTemplate.getForObject(uri, MountainInfoXml.class);
        List<MountainInfoDto> mountainInfoDto = mountainInfoXml.getBody().getItems().getMountainInfoDto();
        log.info("첫번째로 검색된 산 정보: {}",mountainInfoDto);

        return mountainInfoDto;
    }

    @Override
    public String searchMountainWeather(String mountainName) {
        return null;
    }

    @Override
    public String searchMountainPhoto(String mountainNumber) {
        return null;
    }
}
