package com.mountainstory.project.dto.mountain.mountainInfo;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.ToString;

import java.util.List;


@Getter
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.NONE)
public class MountainInfoXml {

  @XmlElement(name = "body")
  private Body body;

    @ToString
    @Getter
    @XmlAccessorType(XmlAccessType.NONE)
    public static class Body {

      @XmlElementWrapper(name = "items")
      @XmlElement(name = "item")
      private List<MountainInfoDto> mountainInfoDto;

  }

}