package com.mountainstory.project.service.mountainInfo.impl;
import com.mountainstory.project.dto.mountain.mountainImg.MountainImgDto;
import com.mountainstory.project.dto.mountain.mountainImg.MountainImgXml;
import com.mountainstory.project.dto.mountain.mountainInfo.MountainInfoDto;
import com.mountainstory.project.dto.mountain.mountainInfo.MountainInfoXml;
import com.mountainstory.project.dto.mountain.mountainregion.MountainCoordinate;
import com.mountainstory.project.dto.mountain.mountainregion.MountainLocation;
import com.mountainstory.project.repository.region.LocationRepository;
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
import java.util.stream.IntStream;

@Slf4j
@Service
public class MountainInfoServiceImpl implements MountainInfoService {

    private final LocationRepository locationRepository;
    @Value("${openapi.serviceKey}")
    private String openApiServiceKey;

    public MountainInfoServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }


    @Override
    public List<MountainInfoDto> getAllMountainInfo(String mountainName) throws UnsupportedEncodingException {
        List<MountainInfoDto> mountainInfoDtoList = searchMountainInfo(mountainName);
        setImgToDtoList(mountainInfoDtoList);
        getMountainCoordinate(mountainInfoDtoList);
        setCoordinateToDtoList(mountainInfoDtoList);


        log.info(">좌표 {}",getMountainCoordinate(mountainInfoDtoList));
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
        //TODO: 반복문 최적화 필요
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

    private void setCoordinateToDtoList(List<MountainInfoDto> mountainInfoDtoList){
        List<MountainCoordinate> mountainCoordinate = getMountainCoordinate(mountainInfoDtoList);

        //TODO: 분석 필요
        IntStream.range(0, mountainInfoDtoList.size())
                .forEach(i -> mountainInfoDtoList.get(i).setMountainCoordinate(mountainCoordinate.get(i)));
    }


    private List<MountainCoordinate> getMountainCoordinate(List<MountainInfoDto> mountainInfoDtoList){

        List<MountainLocation> mountainLocationList = mountainInfoDtoList.stream().map(marketInfoDto -> {
            MountainLocation mountainLocation = splitMountainLocation(marketInfoDto.getMountainLocation());
            return mountainLocation;
        }).collect(Collectors.toList());


        List<MountainCoordinate> mountainCoordinateList = mountainLocationList.stream().map(
                mountainLocation -> {
                    MountainCoordinate locationToCoordinate = locationRepository.findCoordinateToLocation(mountainLocation.getLocationParent(),
                            mountainLocation.getLocationChild(), mountainLocation.getLocationChildDetail());

                    if(locationToCoordinate==null){
                        mountainLocation.setLocationChildDetail("");
                        MountainCoordinate notChildDetailCoordinate = locationRepository.findCoordinateToLocation
                                (mountainLocation.getLocationParent(), mountainLocation.getLocationChild(), mountainLocation.getLocationChildDetail());
                        return notChildDetailCoordinate;
                    }
                    return locationToCoordinate;
                }
        ).collect(Collectors.toList());
        return mountainCoordinateList;
    }


    private MountainLocation splitMountainLocation(String mountainLocationAll){
        MountainLocation mountainLocation = new MountainLocation();
        String[] mountainLocationSplit = mountainLocationAll.split(" ");
        log.info("산 위치> {}",mountainLocationAll);

        mountainLocation.setLocationParent(mountainLocationSplit[0]);
        mountainLocation.setLocationChild(mountainLocationSplit[1]);
        mountainLocation.setLocationChildDetail(mountainLocationSplit[2]);

        return mountainLocation;
    }



    @Override
    public String searchMountainWeather(String mountainName) {
        return null;
    }

}
