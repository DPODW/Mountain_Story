package com.mountainstory.project.service.mountainInfo;

import com.mountainstory.project.dto.mountain.mountainimg.MountainImgDto;
import com.mountainstory.project.dto.mountain.mountaininfo.MountainInfoDto;
import com.mountainstory.project.dto.mountain.mountaininfo.MountainWeather;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

@Service
public interface MountainInfoService {

    List<MountainInfoDto> getAllMountainInfo(String mountainName) throws UnsupportedEncodingException;

    List<MountainInfoDto> searchMountainInfo(String mountainName) throws UnsupportedEncodingException;

    List<MountainImgDto> searchMountainImg(String mountainNumber);

    MountainWeather searchMountainWeather(Integer nx , Integer ny) throws ParseException;




}
