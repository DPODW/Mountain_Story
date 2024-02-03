package com.mountainstory.project.dto.mountainInfo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
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

      @XmlElement(name = "items")
      private Items items;

      @ToString
      @Getter
      @XmlAccessorType(XmlAccessType.NONE)
      public static class Items {

      @XmlElement(name = "item")
      private List<MountainInfoDto> mountainInfoDto;
     }

  }

}