package com.mountainstory.project.dto.mountain.mountaininfo;

import com.mountainstory.project.dto.mountain.mountainregion.MountainCoordinate;
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

    private MountainCoordinate mountainCoordinate;


    public MountainInfoDto(String mountainName, String mountainLocation, String mountainIntroduce, String mountainHigh, String mountainNo ,String mountainImgUrl,
                           MountainCoordinate mountainCoordinate) {
        this.mountainName = mountainName;
        this.mountainLocation = mountainLocation;
        this.mountainIntroduce = mountainIntroduce;
        this.mountainHigh = mountainHigh;
        this.mountainNo = mountainNo;
        this.mountainImgUrl=mountainImgUrl;
        this.mountainCoordinate=mountainCoordinate;
    }
}
