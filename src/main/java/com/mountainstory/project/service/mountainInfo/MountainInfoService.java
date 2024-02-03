package com.mountainstory.project.service.mountainInfo;

import com.mountainstory.project.dto.mountainInfo.MountainInfoDto;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public interface MountainInfoService {

    List<MountainInfoDto> searchMountainInfo(String mountainName) throws UnsupportedEncodingException;

    String searchMountainWeather(String mountainName);

    String searchMountainPhoto(String mountainNumber);


}
