package com.mountainstory.project.service.mountain.mountaininfo;

import com.mountainstory.project.dto.mountain.mountainimg.MountainImgDto;
import com.mountainstory.project.dto.mountain.mountaininfo.MountainInfoDto;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public interface MountainInfoService {

    List<MountainInfoDto> getAllMountainInfo(String mountainName) throws UnsupportedEncodingException;

    List<MountainInfoDto> searchMountainInfo(String mountainName) throws UnsupportedEncodingException;

    List<MountainImgDto> searchMountainImg(String mountainNumber);


}
