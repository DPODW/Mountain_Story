package com.mountainstory.project.dto.mountain.mountainregion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class MountainLocation {

    private String locationParent;

    private String locationChild;

    private String locationChildDetail;

    public MountainLocation(String locationParent, String locationChild, String locationChildDetail) {
        this.locationParent = locationParent;
        this.locationChild = locationChild;
        this.locationChildDetail = locationChildDetail;
    }
}
