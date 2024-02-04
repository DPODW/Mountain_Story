package com.mountainstory.project.service.mountainInfo.impl;


import com.mountainstory.project.dto.mountain.mountainImg.MountainImgDto;
import com.mountainstory.project.dto.mountain.mountainImg.MountainImgXml;
import com.mountainstory.project.dto.mountain.mountainInfo.MountainInfoDto;
import com.mountainstory.project.dto.mountain.mountainInfo.MountainInfoXml;
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
import java.util.stream.Collectors;

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
                .queryParam("numOfRows","5")
                .queryParam("ServiceKey",openApiServiceKey)
                .build(true) //false: 인코딩 실행 , true: 이미 인코딩 되어있음 (이미 인코딩 되어있는 서비스 키가 properties 에 있음)
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        MountainInfoXml mountainInfoXml = restTemplate.getForObject(uri, MountainInfoXml.class);
        List<MountainInfoDto> mountainInfoDtoList = mountainInfoXml.getBody().getMountainInfoDto();

        //TODO: 검색된 모든 산 -> 번호 가져와서 이미지 URL 추출 (메소드 추출 필요)
        List<MountainImgDto> mountainImgDtoList = mountainInfoDtoList.stream()
                .map(mountainInfoDto -> mountainInfoDto.getMountainNo())
                .flatMap(mountainNo -> searchMountainPhoto(mountainNo).stream())
                .collect(Collectors.toList());


        //TODO: 이미지 URL -> mountainInfoDto 최종 SET (메소드 추출 필요)
        mountainInfoDtoList.forEach(mountainInfoDto -> {
            List<MountainImgDto> imageUrlList = searchMountainPhoto(mountainInfoDto.getMountainNo());

            // 이미지 URL 리스트가 비어있지 않은 경우에만 첫 번째 이미지 URL을 가져와서 testImg에 설정
            if (!imageUrlList.isEmpty()) {
                MountainImgDto firstImageUrl = imageUrlList.get(0);
                mountainInfoDto.setTestImg(firstImageUrl.getMountainImgName());
            } else {
                mountainInfoDto.setTestImg(null);
            }
        });


        log.info("{}",mountainInfoDtoList);
        return mountainInfoDtoList;
    }

    @Override
    public List<MountainImgDto> searchMountainPhoto(String mountainNumber) {
        //TODO: 해당 메소드 의미 없는 호출 발생. 해결 필요
        log.info("몇번 실행되냐");
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
    public String searchMountainWeather(String mountainName) {
        return null;
    }

}
