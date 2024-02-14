package com.mountainstory.project.dto.mountain.mountainimg;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.NONE)
public class MountainImgDto {

    @XmlElement(name = "imgfilename")
    private String mountainImgName;


    public MountainImgDto(String mountainImgName) {
        this.mountainImgName = mountainImgName;
    }
}
