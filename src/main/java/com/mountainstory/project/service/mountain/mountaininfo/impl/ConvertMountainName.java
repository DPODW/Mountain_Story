package com.mountainstory.project.service.mountain.mountaininfo.impl;

import com.mountainstory.project.dto.mountain.mountaininfo.MountainInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;


@Slf4j
@Service
public class ConvertMountainName {

    protected String deleteSpecialSymbols(String mountainName){
        if (mountainName.contains("_")){
            return mountainName.substring(0, mountainName.lastIndexOf("_"));
        }else
            return mountainName;
    }

    //TODO: 학습 필요
    protected void removePartSameMountain(String mountainName, List<MountainInfoDto> mountainInfoDtoList) {
        mountainInfoDtoList.removeIf(mountainInfoDto -> !mountainName.equals(deleteSpecialSymbols(mountainInfoDto.getMountainName())) ||
                        mountainInfoDto.getMountainHigh().equals("0")
        );
    }
}
