package com.mountainstory.project.dto.mountain.mountainInfo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.NONE)
public class MountainInfoDto {

    @XmlElement(name = "mntiname")
    private String mountainName;

    @XmlElement(name = "mntiadd")
    private String mountainLocation;

    @XmlElement(name = "mntidetails")
    private String mountainIntroduce;

    @XmlElement(name = "mntihigh")
    private String mountainHigh;

    @XmlElement(name = "mntilistno")
    private String mountainNo;

    private String mountainImgUrl;


    public MountainInfoDto(String mountainName, String mountainLocation, String mountainIntroduce, String mountainHigh, String mountainNo ,String mountainImgUrl) {
        this.mountainName = mountainName;
        this.mountainLocation = mountainLocation;
        this.mountainIntroduce = mountainIntroduce;
        this.mountainHigh = mountainHigh;
        this.mountainNo = mountainNo;
        this.mountainImgUrl=mountainImgUrl;
    }
}
