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

    private int nx;

    private int ny;

    public MountainCoordinate(int nx, int ny) {
        this.nx = nx;
        this.ny = ny;
    }
}
