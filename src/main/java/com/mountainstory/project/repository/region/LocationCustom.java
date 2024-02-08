package com.mountainstory.project.repository.region;

import com.mountainstory.project.dto.mountain.mountainregion.MountainCoordinate;
import com.mountainstory.project.entity.region.Location;

import java.util.List;
import java.util.Map;

public interface LocationCustom {
    MountainCoordinate findCoordinateToLocation(String locationParent, String locationChild, String locationChildDetail);
}
