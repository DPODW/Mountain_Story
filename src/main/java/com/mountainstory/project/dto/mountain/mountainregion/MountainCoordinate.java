package com.mountainstory.project.dto.mountain.mountainregion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class MountainCoordinate {

    private Integer nx;

    private Integer ny;

    public MountainCoordinate(Integer nx, Integer ny) {
        this.nx = nx;
        this.ny = ny;
    }
}
