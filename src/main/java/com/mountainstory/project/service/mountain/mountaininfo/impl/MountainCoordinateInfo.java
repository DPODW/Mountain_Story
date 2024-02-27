package com.mountainstory.project.service.mountain.mountaininfo.impl;

import com.mountainstory.project.dto.mountain.mountaininfo.MountainInfoDto;
import com.mountainstory.project.dto.mountain.mountainregion.MountainCoordinate;
import com.mountainstory.project.dto.mountain.mountainregion.MountainLocation;
import com.mountainstory.project.repository.region.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class MountainCoordinateInfo {

    private final LocationRepository locationRepository;

    private static final int PARENT_PART = 0;
    private static final int CHILD_PART = 1;
    private static final int CHILD_DETAIL_PART = 2;


    public MountainCoordinateInfo(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }


    protected List<MountainCoordinate> getMountainCoordinate(List<MountainInfoDto> mountainInfoDtoList){

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


    protected void setCoordinateToDtoList(List<MountainInfoDto> mountainInfoDtoList){
        List<MountainCoordinate> mountainCoordinate = getMountainCoordinate(mountainInfoDtoList);

        IntStream.range(0, mountainInfoDtoList.size())
                .forEach(i -> mountainInfoDtoList.get(i).setMountainCoordinate(mountainCoordinate.get(i)));
    }


    protected MountainLocation splitMountainLocation(String mountainLocationAll){
        MountainLocation mountainLocation = new MountainLocation();
        String[] mountainLocationSplit = mountainLocationAll.split(" ");

        mountainLocation.setLocationParent(mountainLocationSplit[PARENT_PART]);
        mountainLocation.setLocationChild(mountainLocationSplit[CHILD_PART]);
        mountainLocation.setLocationChildDetail(mountainLocationSplit[CHILD_DETAIL_PART]);

        return mountainLocation;
    }
}
