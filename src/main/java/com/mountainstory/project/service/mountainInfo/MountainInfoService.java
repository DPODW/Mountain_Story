package com.mountainstory.project.service.mountainInfo;

import com.mountainstory.project.dto.mountain.mountainImg.MountainImgDto;
import com.mountainstory.project.dto.mountain.mountainInfo.MountainInfoDto;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public interface MountainInfoService {

    List<MountainInfoDto> searchMountainInfo(String mountainName) throws UnsupportedEncodingException;

    List<MountainImgDto> searchMountainImg(String mountainNumber);

    String searchMountainWeather(String mountainName);




}
